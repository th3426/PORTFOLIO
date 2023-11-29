## info

- h_server.keystore
    - key algorithm : RSA
    - key size : 2048
    - keystore pw : 1234qwer
    - alias name : alias_name

--- 

## 명령어
- apk decompile

        apktool d [디컴파일 할 apk 파일이 있는 경로] -r -f

- apk recompile

        [!] 리컴파일 할 파일이 있는 경로 : apktool.yml 이 존재하는 폴더
        apktool b [리컴파일 할 파일이 있는 경로] -o [새로 지정할 apk 이름].apk

- 서명 만들기

        keytool -genkey -v -keystore [name].keystore -alias [alias_name] -keyalg [algorithm] -keysize [keysize]

        ex ) name : h_server, alias_name : alias_name, algorithm : RSA , keysize : 2048

- 서명 사이닝

        jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore [h_server.keystore] [리컴파일 apk] alias_name