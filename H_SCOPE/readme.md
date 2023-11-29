<script src="https://cdn.jsdelivr.net/npm/mermaid/dist/mermaid.min.js"></script>
<script>mermaid.initialize({startOnLoad:true});</script>

## H_SCOPE
    목표 : 앱의 위,변조를 탐지하는 무결성 검증 과정을 앱에 추가 및 검증

## 개발 환경 및 사용 툴
    OS : Linux (WSL v1.2.5.0)
    tool : apktool (v2.4.1, with smali v2.3.4 and baksmali v2.3.4)
           jadx-gui (v1.4.6, Java VM : OpenJDK 64-bits, v19.0.2)
           vscode (v1.78.2)
    target : 2048.apk (v 1.3.3, tpcstld.twozerogame, 안드로이드 플레이스토어에서 추출)
    lang : C, JAVA
    SDK : min 14 ~ max 28

---

### 23.05.17

- 데모 앱 : genOTP.apk
- 진행 상황 : 메인에서 애플리케이션 이름을 입력하면 H register origin app.c 에서 해당 앱을 열고 해시값을 구한다.

- Application class smali 주입 시 고려사항  
    1. 이미 Application class를 상속받는 클래스, 메소드가 존재할 수 있다  
        - 순서를 바꿔주는 과정이 필요 
    2. AndroidManifest.xml 파일 또한 수정이 필요.
        - 자동화를 진행한다면 AndroidManifest.xml의 패턴마다 파싱 과정이 달라져야 한다  
    3. SDK 버전 체크  
        - 리컴파일(리패키지) 과정에서 SDK 버전마다 컴파일러가 달라 고려해야한다  

### 23.05.18

- 진행 상황
    - 리눅스 환경에서 apktool 2.5.0 를 설치하고 디컴파일, 리컴파일, 리사이닝을 테스트 했다. 앱 수정 없이 디컴파일, 리컴파일, 리사이닝 과정을 거쳐도 원본과 다른 해시값을 가진다. <br><br> 위 다이어그램 순서로 실행 시 리컴파일, 리사이닝 과정에서 키도 바뀌고 앱 해시값도 달라진다. 리컴파일, 리사이닝 이후 과정의 해시값을 구할 순 있지만, 이후 과정의 해시값은 원본에 대한 해시값이 아니기 때문에 원본 무결성의 성격이 아니게 된다. <br><br> 새롭게 고안해본 방법은 최초 클라리언트가 의뢰를 할 때, 원본 앱과 원본 앱의 해시값을 같이 주는 것 이다. 해시 알고리즘은 상호 약속이 되어있다는 조건이다. 원본 앱과 원본 앱의 해시값을 같이 준다면 서버는 받은 앱의 해시값을 새로 구하고 받은 해시값과 비교한다. <br><br> 위 과정이 추가된다면, 최초 과정에서 클라이언트가 전송하는 앱이 원본임을 증명할 수 있다. 이후 서버에서 받은 앱 또한 원본임을 증명할 수 있다. 이후 리컴파일, 리사이닝 과정 후 해시값을 저장한 뒤, 실행 시 비교한다면 무결성을 검증 할 수 있을 것이다.


---

### 23.05.19

- 앱 재가공 과정

    <div class="mermaid">
    flowchart LR
        A[원본 앱] --> B[Decompile]
        B --> C[Smali Code Injection]
        C --> D[Recompile and Resigning]
        D --> E[재가공된 앱]
    </div>

- 원본 앱과 재가공 앱 실행 순서 비교  
    
    1. 기존 앱  
        <div class="mermaid">
        flowchart LR
            실행 --> 게임시작
        </div>  

    2. 재가공 앱
        <div class="mermaid">
        flowchart LR
            A[실행] --> B[서버 접속 <br/> 구간 암호화]
            B --> C[해당 앱 서명, 해시 비교]
            C --> |PASS| D[게임시작]
            C --> |FAIL| E[앱 종료]
        </div>

- 진행 상황
    - 리눅스에서 사용하는 apktool과 윈도우에서 사용하는 apkeasytool, 두 환경에서 디컴파일 및 리컴파일을 진행해보았다. 리눅스에서 사용하는 apktool을 통해 디컴파일을 한 경우 AndroidManifest.xml 파일이 윈도우에서 열리지 않았다. 운영체제가 달라 열리지 않는 듯 했다. <br><br> 프로젝트 주제에 걸맞게 전제조건과 내가 수행할 부분의 선을 명확히 했고 총 과정의 절차가 수행되는지 우선적으로 테스트 해보는 것을 목적으로 삼았다. <br><br> 타켓 APP에 토스트 메세지를 띄우는 스말리 코드를 주입해보았다. 추후 소켓으로 연결 할 라이브러리와 JNI를 만들어 주입하거나, 자바에서 소켓을 열어 무결성 검증을 할 서버에 접속을 테스트 해볼것이다.

---
    
### 23.05.22
- 데모 앱 변경 : 2048.apk

- 진행상황
    - 리눅스 apktool 문제 해결, 버전 문제였음
    - 앱 재가공 과정 이후 재가공 앱 해시, 재가공 앱 키 해시 데이터를 저장
    - 재가공 시 데모 기준으로 작성된 신규 smali 코드와 수정된 Androidmanifest.xml 파일 주입
        - Application을 상속받는 H_solution 클래스 스말리 코드를 주입 후 Androidmanifest.xml에서 진입점을 변경
    - 주입후 재가공 마친 앱 정상 작동 확인 ( "this is test" 문구 토스트 )

<br>

- 재가공 과정
    1. 실행 전 대상 앱이 올바른 경로에 위치해야함
    2. DATABASE 텍스트 파일 생성 및 초기화
    3. H_solution 진행
        1. 대상 앱 디컴파일
        2. 디컴파일 경로에 준비된 코드 주입
        3. 대상 앱 리컴파일
        4. 대상 앱 리사이닝
    4. 생성된 재가공 이후 앱의 해시값, 재가공 앱의 키 해시값을 DATABASE 텍스트 파일에 저장

<br>

- 문제점
    - 리사이닝에 사용하는 서버의 키스토어가 셀프사인이기 때문에 앱 설치시 경고 문구가 뜸
        - CA 등록 혹은 구글 플레이스토어에 등록된 서명이면 해결 가능
    - 재가공 이후 앱의 키 값을 어떻게 가져와야 할지 고민중

---

### 23.05.25

- ### 서비스 (재가공)
    <div class="mermaid">
        flowchart LR
            A[1. APK 원본] --> B[2. 디컴파일]
            B --> C[3. 코드 및 라이브러리 주입]
            C --> D[4. 리컴파일]
            D --> E[5. 배포될 앱의 apk, 서명 해시값 저장]
            E --> F[6. 앱 배포]
    </div>

    1. APK 원본
        - 해당 원본 APK 획득  
    2. 디컴파일
        - 원본 APK를 디컴파일
    3. 코드 및 라이브러리 주입
        - 서버로 접속을 유도하는 스말리 코드와 구간 암호화를 위한 라이브러리를 주입
    4. 리컴파일
        - 서비스를 제공하는 측의 키를 통해 리컴파일 및 리사이닝
    5. 배포될 앱의 apk, 서명 해시값 저장
        - 리컴파일이 완료된 앱에 대해 apk 해시값, apk 사인 해시값을 저장(hex enc)한다 (SHA256)
    6. 앱 배포
        - 앱 배포

- ### 운용
    <div class="mermaid">
        sequenceDiagram
            Note left of Client: 1. 배포된 앱 실행
            Note left of Client: 2. 주입 된 코드 및 라이브러리를 통해 서버 접속
            Client ->> Server: 3. 앱 해시값, 서명 해시값 전송 (SHA256)
            Note right of Server: 4. DB에 저장된 해시값과 Client의 해시값 비교
            Server ->> Client: 5. 일치하면 앱 구동, 아니면 앱 종료
    </div>

- 해결해야할 점
    1. 클라이언트 쪽에서 구동중인 APK 파일에 대한 해시값을 추출해야함
        - 추출하기전에 라이브러리 안에 값을 박아두면 안될까
            - 안에 값을 박아두려면 클라이언트별로 라이브러리를 각각 만들어줘야함
    
    2. 클라이언트 쪽에서 구동중인 APK 파일의 서명값을 추출해야함
    
    3. 클라이언트 - 서버 간의 구간암호화 통신을 위한 크립토에서 Init 과정에 필요한 서명 데이터를 추출해야함
        - 클라이언트 측에서 접속할 서버의 주소, 포트를 알려야하는가 숨겨야 하는가
            - 숨기려면 라이브러리 안에 주소, 포트 박아두면 가능

- 오늘 해본 것
    - 서비스 (재가공) 단계는 완료했음
    - ABI 별로 바꿔가면서 sign 파일 넣어줘봤음
    - edge crypto errcode 10203 -> 10204 
    - change mode 로 비검증 모드로 바꾸어 sign 없이 해보려했지만 errcode 10116
        - change mode를 하려면 init을 해야함 근데 init이 안되는 상황
        - init 실패 -> change mode 으로 하면 가능한가
            - errcode : 10114 (EDGE_ERR_NOT_AVAILABLE_STATUS)
    - errcode 10204 : 서명 무결성이 깨짐
        - assets 폴더의 서명을 로컬 내부로 복사하여 사용하려 했음
        - Androidmanifest.xml 파일에서 옵션으로 android:extractNativeLibs="true" 주고 진행
            - errcode 동일 (EDGE_ERR_INTEGRITY_VERIFY_SIGN)
        - 혹시 몰라서 android:extractNativeLibs="false" 주고 진행 -> 실패
            ~~~
            // ERROR
            PS C:\Users\user\helloworld_3\workspace\sjhong\H_SCOPE\target_apk> adb install -r .\re_2048.apk
            Performing Streamed Install
            adb: failed to install .\re_2048.apk: Failure [INSTALL_FAILED_INVALID_APK: Failed to extract native libraries, res=-2]
            ~~~
    - √ 주입 전 sign 해시랑 주입 후 sign 해시 비교 해봐야함 (제대로 복사 됐는지)


---

### 23.06.07

    ! 성공

- #### 앱 사이닝
    - keystore 부터 새로운 서명을 하는게 아닌 기존 인증서를 기반으로 APK에 서명을 하는 것
    - 따라서 keystore의 서명값 = APK 의 서명값
    - KEYSTORE의 서명값과 APK의 서명값(FingerPrint)를 비교하면 된다.

<br>

- #### Application을 상속받은 클래스로 MainAct 종료 방법
    - 스레드를 사용하지 않은 경우
        - Application 클래스 -> MainAct
    - 스레드를 사용 한 경우
        - Application 클래스 & MainAct

    <br>

    - 따라서 Application 클래스로 MainAct를 종료하려면 Application 클래스를 스레드로 처리하여 MainAct와 동시에 실행되게 만든 뒤, Application 클래스에서 엑티비티를 종료하는 함수를 호출하면 MainAct도 종료할 수 있다.
    - 만약 스레드로 처리하지 않고 Application 클래스에서 엑티비티를 종료하는 경우,
        - Application 클래스만 종료된 뒤, MainAct가 실행되고 Application 클래스에서 Intent나 Handler 등과 같이 Main Act에 접근이 불가하며 MainAct 종료가 불가하다.



- 관련 링크
	[JNI Env Structure Table](https://docs.google.com/spreadsheets/d/14GgLwizg6qZzCBVxYc3HfegBGBh5Zff5XpyO3cZP64o/edit?pli=1#gid=0)
