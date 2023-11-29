###### Class com.tpcstld.twozerogame.hSolution (com.tpcstld.twozerogame.hSolution)
.class public Lcom/tpcstld/twozerogame/hSolution;
.super Landroid/app/Application;
.source "hSolution.java"


# instance fields
.field ret:I


# direct methods
.method public constructor <init>()V
    .registers 2

    .line 28
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    const/4 v0, 0x0

    .line 29
    iput v0, p0, Lcom/tpcstld/twozerogame/hSolution;->ret:I

    return-void
.end method

.method private native InitCrypto(Ljava/lang/String;)I
.end method

.method static synthetic access$000(Lcom/tpcstld/twozerogame/hSolution;Ljava/lang/String;)I
    .registers 2

    .line 28
    invoke-direct {p0, p1}, Lcom/tpcstld/twozerogame/hSolution;->InitCrypto(Ljava/lang/String;)I

    move-result p0

    return p0
.end method

.method static synthetic access$100(Lcom/tpcstld/twozerogame/hSolution;Ljava/lang/String;)I
    .registers 2

    .line 28
    invoke-direct {p0, p1}, Lcom/tpcstld/twozerogame/hSolution;->verifyIntegrity(Ljava/lang/String;)I

    move-result p0

    return p0
.end method

.method public static bytesToHex([B)Ljava/lang/String;
    .registers 7

    const/16 v0, 0x10

    new-array v0, v0, [C

    .line 201
    fill-array-data v0, :array_2e

    .line 203
    array-length v1, p0

    mul-int/lit8 v1, v1, 0x2

    new-array v1, v1, [C

    const/4 v2, 0x0

    .line 205
    :goto_d
    array-length v3, p0

    if-ge v2, v3, :cond_27

    .line 206
    aget-byte v3, p0, v2

    and-int/lit16 v3, v3, 0xff

    mul-int/lit8 v4, v2, 0x2

    ushr-int/lit8 v5, v3, 0x4

    .line 207
    aget-char v5, v0, v5

    aput-char v5, v1, v4

    add-int/lit8 v4, v4, 0x1

    and-int/lit8 v3, v3, 0xf

    .line 208
    aget-char v3, v0, v3

    aput-char v3, v1, v4

    add-int/lit8 v2, v2, 0x1

    goto :goto_d

    .line 210
    :cond_27
    new-instance p0, Ljava/lang/String;

    invoke-direct {p0, v1}, Ljava/lang/String;-><init>([C)V

    return-object p0

    nop

    :array_2e
    .array-data 2
        0x30s
        0x31s
        0x32s
        0x33s
        0x34s
        0x35s
        0x36s
        0x37s
        0x38s
        0x39s
        0x41s
        0x42s
        0x43s
        0x44s
        0x45s
        0x46s
    .end array-data
.end method

.method private native errorPrint(II)V
.end method

.method public static getSHA1([B)Ljava/lang/String;
    .registers 2
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/security/NoSuchAlgorithmException;
        }
    .end annotation

    const-string v0, "SHA1"

    .line 194
    invoke-static {v0}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v0

    .line 195
    invoke-virtual {v0, p0}, Ljava/security/MessageDigest;->update([B)V

    .line 196
    invoke-virtual {v0}, Ljava/security/MessageDigest;->digest()[B

    move-result-object p0

    .line 197
    invoke-static {p0}, Lcom/tpcstld/twozerogame/hSolution;->bytesToHex([B)Ljava/lang/String;

    move-result-object p0

    return-object p0
.end method

.method private native verifyIntegrity(Ljava/lang/String;)I
.end method


# virtual methods
.method public exitApp()V
    .registers 3

    const-string v0, "hSolution"

    const-string v1, "exit execute"

    .line 108
    invoke-static {v0, v1}, Landroid/util/Log;->d(Ljava/lang/String;Ljava/lang/String;)I

    .line 109
    invoke-static {}, Landroid/os/Process;->myPid()I

    move-result v0

    invoke-static {v0}, Landroid/os/Process;->killProcess(I)V

    return-void
.end method

.method public getApkSignature(Landroid/content/Context;)Ljava/lang/String;
    .registers 6

    const/4 v0, 0x0

    .line 181
    :try_start_1
    invoke-virtual {p1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    invoke-virtual {p1}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object p1

    const/16 v2, 0x40

    invoke-virtual {v1, p1, v2}, Landroid/content/pm/PackageManager;->getPackageInfo(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;

    move-result-object p1

    .line 182
    iget-object p1, p1, Landroid/content/pm/PackageInfo;->signatures:[Landroid/content/pm/Signature;

    array-length v1, p1

    const/4 v2, 0x0

    :goto_13
    if-ge v2, v1, :cond_28

    aget-object v3, p1, v2

    .line 184
    invoke-virtual {v3}, Landroid/content/pm/Signature;->toByteArray()[B

    move-result-object v3

    invoke-static {v3}, Lcom/tpcstld/twozerogame/hSolution;->getSHA1([B)Ljava/lang/String;

    move-result-object v0
    :try_end_1f
    .catch Landroid/content/pm/PackageManager$NameNotFoundException; {:try_start_1 .. :try_end_1f} :catch_24
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_1 .. :try_end_1f} :catch_22

    add-int/lit8 v2, v2, 0x1

    goto :goto_13

    :catch_22
    move-exception p1

    goto :goto_25

    :catch_24
    move-exception p1

    .line 187
    :goto_25
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    :cond_28
    return-object v0
.end method

.method public nativeLibLoader(Ljava/lang/String;Ljava/lang/String;)V
    .registers 7
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 117
    invoke-virtual {p0}, Lcom/tpcstld/twozerogame/hSolution;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v0

    .line 119
    invoke-virtual {v0, p1}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v0

    .line 120
    new-instance v1, Ljava/io/File;

    invoke-virtual {p0}, Lcom/tpcstld/twozerogame/hSolution;->getFilesDir()Ljava/io/File;

    move-result-object v2

    invoke-direct {v1, v2, p1}, Ljava/io/File;-><init>(Ljava/io/File;Ljava/lang/String;)V

    .line 121
    new-instance p1, Ljava/io/FileOutputStream;

    invoke-direct {p1, v1}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    const/16 v1, 0x400

    new-array v1, v1, [B

    .line 126
    :goto_1a
    invoke-virtual {v0, v1}, Ljava/io/InputStream;->read([B)I

    move-result v2

    const/4 v3, -0x1

    if-eq v2, v3, :cond_26

    const/4 v3, 0x0

    .line 127
    invoke-virtual {p1, v1, v3, v2}, Ljava/io/OutputStream;->write([BII)V

    goto :goto_1a

    .line 129
    :cond_26
    invoke-virtual {p1}, Ljava/io/OutputStream;->flush()V

    .line 134
    :try_start_29
    invoke-virtual {p0}, Lcom/tpcstld/twozerogame/hSolution;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v1

    const-string v2, "inject_assets.jar"

    invoke-virtual {v1, v2}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v1

    .line 135
    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Lcom/tpcstld/twozerogame/hSolution;->getFilesDir()Ljava/io/File;

    move-result-object v3

    invoke-virtual {v3}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    sget-object v3, Ljava/io/File;->pathSeparator:Ljava/lang/String;

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    const-string v3, "unzip"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p0, v1, v2}, Lcom/tpcstld/twozerogame/hSolution;->unzipTestJar(Ljava/io/InputStream;Ljava/lang/String;)V

    .line 138
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v1, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    const-string v1, "/libhSolution.so"

    invoke-virtual {p2, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p2

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p2

    invoke-static {p2}, Ljava/lang/System;->load(Ljava/lang/String;)V
    :try_end_6d
    .catch Ljava/io/IOException; {:try_start_29 .. :try_end_6d} :catch_6e

    goto :goto_72

    :catch_6e
    move-exception p2

    .line 140
    invoke-virtual {p2}, Ljava/io/IOException;->printStackTrace()V

    .line 143
    :goto_72
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V

    .line 144
    invoke-virtual {p1}, Ljava/io/OutputStream;->close()V

    return-void
.end method

.method public onCreate()V
    .registers 3

    .line 36
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    .line 38
    new-instance v0, Ljava/lang/Thread;

    new-instance v1, Lcom/tpcstld/twozerogame/hSolution$1;

    invoke-direct {v1, p0}, Lcom/tpcstld/twozerogame/hSolution$1;-><init>(Lcom/tpcstld/twozerogame/hSolution;)V

    invoke-direct {v0, v1}, Ljava/lang/Thread;-><init>(Ljava/lang/Runnable;)V

    .line 96
    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    .line 99
    :try_start_10
    invoke-virtual {v0}, Ljava/lang/Thread;->join()V
    :try_end_13
    .catch Ljava/lang/InterruptedException; {:try_start_10 .. :try_end_13} :catch_14

    goto :goto_18

    :catch_14
    move-exception v0

    .line 101
    invoke-virtual {v0}, Ljava/lang/InterruptedException;->printStackTrace()V

    :goto_18
    return-void
.end method

.method public unzipTestJar(Ljava/io/InputStream;Ljava/lang/String;)V
    .registers 8
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 148
    new-instance v0, Ljava/io/File;

    invoke-direct {v0, p2}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 149
    invoke-virtual {v0}, Ljava/io/File;->exists()Z

    move-result v1

    if-nez v1, :cond_e

    .line 150
    invoke-virtual {v0}, Ljava/io/File;->mkdirs()Z

    .line 153
    :cond_e
    new-instance v0, Ljava/util/zip/ZipInputStream;

    invoke-direct {v0, p1}, Ljava/util/zip/ZipInputStream;-><init>(Ljava/io/InputStream;)V

    .line 156
    :goto_13
    invoke-virtual {v0}, Ljava/util/zip/ZipInputStream;->getNextEntry()Ljava/util/zip/ZipEntry;

    move-result-object p1

    if-eqz p1, :cond_5e

    .line 157
    invoke-virtual {p1}, Ljava/util/zip/ZipEntry;->getName()Ljava/lang/String;

    move-result-object v1

    .line 158
    new-instance v2, Ljava/io/File;

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v3, p2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    sget-object v4, Ljava/io/File;->separator:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {v2, v1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 160
    invoke-virtual {p1}, Ljava/util/zip/ZipEntry;->isDirectory()Z

    move-result p1

    if-eqz p1, :cond_43

    .line 161
    invoke-virtual {v2}, Ljava/io/File;->mkdirs()Z

    goto :goto_5a

    .line 163
    :cond_43
    new-instance p1, Ljava/io/FileOutputStream;

    invoke-direct {p1, v2}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    const/16 v1, 0x400

    new-array v1, v1, [B

    .line 166
    :goto_4c
    invoke-virtual {v0, v1}, Ljava/util/zip/ZipInputStream;->read([B)I

    move-result v2

    if-lez v2, :cond_57

    const/4 v3, 0x0

    .line 167
    invoke-virtual {p1, v1, v3, v2}, Ljava/io/FileOutputStream;->write([BII)V

    goto :goto_4c

    .line 169
    :cond_57
    invoke-virtual {p1}, Ljava/io/FileOutputStream;->close()V

    .line 171
    :goto_5a
    invoke-virtual {v0}, Ljava/util/zip/ZipInputStream;->closeEntry()V

    goto :goto_13

    .line 173
    :cond_5e
    invoke-virtual {v0}, Ljava/util/zip/ZipInputStream;->close()V

    return-void
.end method
