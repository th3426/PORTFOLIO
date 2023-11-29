package com.tpcstld.twozerogame;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class hSolution extends Application {
    int ret = 0;

    private native void errorPrint(int ret, int socket_num);
    private native int InitCrypto(String sighPath);
    private native int verifyIntegrity(String signData);

    public void onCreate(){
        super.onCreate();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                String abi = Build.CPU_ABI;
                String jarFilePath = "inject_assets.jar";
                String localDir = getFilesDir().getAbsolutePath() + File.pathSeparator + "unzip" + "/inject_lib/assets/" + abi;

                ////////////////////////////////////////////////////////////////////////////////////
                //                              1. Load Native Lib                                //
                ////////////////////////////////////////////////////////////////////////////////////
                try {
                    nativeLibLoader(jarFilePath, localDir);
                    Log.d("hSolution", "loaded native lib");
                } catch (IOException e) {
                    e.printStackTrace();
                    exitApp();
                }

                ////////////////////////////////////////////////////////////////////////////////////
                //                              2. Initialize Crypto                              //
                ////////////////////////////////////////////////////////////////////////////////////
                ret = InitCrypto((localDir + "/libEdgeCrypto.sign").toString());

                if(ret != 0){
                    Log.d("hSolution","func:initCrypto() failed, ret : " + ret);
                    exitApp();
                }
                else{
                    Log.d("hSolution","func:initCrypto() success ");
                }

                ////////////////////////////////////////////////////////////////////////////////////
                //                              3. Verify from Server                             //
                ////////////////////////////////////////////////////////////////////////////////////
                ret = verifyIntegrity(getApkSignature(getApplicationContext()));

                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(ret != 0){
                            Toast.makeText(getApplicationContext(), "verify failed",Toast.LENGTH_LONG).show();
                            Log.d("hSolution", "verify failed, ret : " + ret);
                            // 검증에 실패한 경우 앱 종료
                            exitApp();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "verify success",Toast.LENGTH_LONG).show();
                            Log.d("hSolution", "verify success");
                        }
                    }
                },0);
            }
        });thread.start();
    }

    public void exitApp() {
        Log.d("hSolution", "exit execute");
        android.os.Process.killProcess(android.os.Process.myPid());

        //System.runFinalization();
        //System.exit(1);
    }

    public void nativeLibLoader(String jarFile, String localDir) throws IOException{
        // inject_assets.jar 파일 로컬 내부로 복사
        AssetManager assetManager = getAssets();

        InputStream signFileStream = assetManager.open(jarFile);
        File outputFile = new File(getFilesDir(), jarFile);
        OutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[1024];
        int read;

        while ((read = signFileStream.read(buffer))!= -1) {
            outputStream.write(buffer, 0, read);
        }
        outputStream.flush();

        try{
            // 복사한 jar 파일 압축 풀기
            //Log.d("hSolution", "unzip jar files");
            InputStream jarStream = getAssets().open("inject_assets.jar");
            unzipTestJar(jarStream, getFilesDir().getAbsolutePath() + File.pathSeparator + "unzip");

            // 로컬에 jar 압축 해제 후 디렉토리에서 절대경로로 로드
            System.load(localDir + "/libhSolution.so");
        }catch (IOException e){
            e.printStackTrace();
        }

        signFileStream.close();
        outputStream.close();
    }

    public void unzipTestJar(InputStream inputStream, String outputDir) throws IOException {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry entry;

        while ((entry = zipInputStream.getNextEntry()) != null) {
            String entryName = entry.getName();
            File outputFile = new File(outputDir + File.separator + entryName);

            if (entry.isDirectory()) {
                outputFile.mkdirs();
            } else {
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = zipInputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.close();
            }
            zipInputStream.closeEntry();
        }
        zipInputStream.close();
    }

    // 오픈 소스 참조
    // https://gist.github.com/scottyab/b849701972d57cf9562e?permalink_comment_id=3245507
    public String getApkSignature(Context context) {
        String sha1Signature = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : packageInfo.signatures) {
                // SHA1 the signature
                sha1Signature = getSHA1(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return sha1Signature;
    }

    //서명값의 SHA1 데이터
    public static String getSHA1(byte[] sig) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        digest.update(sig);
        byte[] hashtext = digest.digest();
        return bytesToHex(hashtext);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] hexChars = new char[bytes.length * 2];
        int tmp;
        for (int j = 0; j < bytes.length; j++) {
            tmp = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[tmp >>> 4];
            hexChars[j * 2 + 1] = hexArray[tmp & 0x0F];
        }
        return new String(hexChars);
    }
}
