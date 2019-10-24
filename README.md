# APP_PasooGunM_Team

## 1.컴퓨터 구성 / 필수 조건 안내 (Prerequisites)
  ### 작업환경
    프로그래밍 버젼은 다음 버젼을 사용했습니다.

    **자바**  jdk-8u221-windows-x64

    **안드로이드 스튜디오** android-studio-ide-191.5900203-windows

    **서버** apache-tomcat-9.0.26-windows-x64

    **문서 편집** eclipse-jee-2019-09-R-win32-x86_64

    **DB** sqldeveloper-19.2.1.247.2212-x64

    **안드로이드 API 레벨** 최소 15  최대 28




## 2.설치 안내 (Installation Process)
  ### APP
    APP은 안드로이드 스튜디오에서 AVD로 구동 또는 APK 배포로 실행합니다.
    
  ### Server
    서버는 eclipse로 디렉토리 내 Realserver 폴더를 프로젝트 폴더로 설정합니다.
    
    이후 해당 apache tomcat 버젼을 이용해 실행합니다.



## 3.사용법 (Getting Started)
  ### 사용자
    
    사용자는 Registration에 관리자의 ID를 입력합니다.
    
    이후 사용자가 카메라에 접근하면 카메라 구동시간과 구동여부가 서버로 전송됩니다.
    
  ### 관리자
    
    관리자는 로그인 시 관리하고 있는 사용자의 카메라 구동시간을 서버로 부터 받을 수 있습니다.
    

## 4.파일 정보 및 목록 (File Manifest)
  ### App Directory
```bash
/app/
├── manifests
│   │   AndroidManifest.xml     # 기본 정보
│   
├── java
|   ├   com.example.pasoom
│   │   │   AdminLogin.java     # 관리자 로그인 정보
│   │   │   AdminMain.java      # 관리자 메인화면 정보
│   │   │   AdminRegister.java  # 관리자 회원가입 정보
│   │   │   alert.java          # TODO  관리자 알람 서비스
│   │   │   CamOver.java        # 카메라 감시 서비스
│   │   │   MainActivity.java   # 초기화면 정보
│   │   │   UserLogin.java      # 사용자 로그인 정보
│   │   │   UserMain.java       # 사용자 메인화면 정보
│   │   │   UserRegister.java   # 사용자 회원가입 정보
│   │
│   ...
│
├── res
│   ├── layout
│   │    │  aaa.xml             # TODO  미사용
│   │    │  admin_login.xml     # 관리자 로그인 UI
│   │    │  admin_main.xml      # 관리자 메인화면 UI
│   │    │  admin_register.xml  # 관리자 회원가입 UI
│   │    │  default.screen.xml  # 초기화면 UI
│   │    │  logo.xml            # TODO  미사용
│   │    │  row.xml             # 리스트뷰 요소 UI 정보
│   │    │  user_login.xml      # 사용자 로그인 UI
│   │    │  user_main.xml       # 사용자 메인화면 UI
│   │    │  user_register.xml   # 사용자 회원가입 UI
│   │
│   ...
│
...
```
  ### Server Directory
```bash
/RealServer/
├── build
│   └── classes   
│       │  test.class           # TODO
│       │  work.class           # TODO
│       │  work11.class         # TODO
│   
├── src
│   │  test.java                # TODO
│   │  work.java                # TODO
│   │  work11.java              # TODO
│
├── WebContent
│   ├── META-INF
│   ├── WEB-INF
│   │
│   │  admindelete.jsp          # 관리자 ID 삭제
│   │  adminlogin.jsp           # 관리자 로그인
│   │  adminon.jsp              # 관리자 메인화면
│   │  adminregister.jsp        # 관리자 회원가입
│   │  camend.jsp               # 카메라 가동 종료
│   │  camon.jsp                # 카메라 가동 시작
│   │  updatebreak.jsp          # TODO 
│   │  userdelete.jsp           # 사용자 ID 삭제
│   │  userlogin.jsp            # 사용자 로그인
│   │  userregister.jsp         # 사용자 회원가입
│
│   enlisted.db                 # 사용자 정보 DB
...
```

## 5.저작권 및 사용권 정보 (Copyright / End User License)
  Copyright@ 2019 all right reserved

## 6.배포자 및 개발자의 연락처 정보 (Contact Information)
  이승현 : seunghyun1216@naver.com
  김동욱 : ddh0500298@gmail.com

## 7.알려진 버그 (Known Issues)
  A. 한 단말에서 사용자 및 관리자에 여러번 접속시 서버가 다운되는 현상
  B. 삭제버튼이 아닌 리스트 뷰의 요소를 클릭했을때 사용자가 삭제되는 현상

## 8.문제 발생에 대한 해결책 (Troubleshooting)
  A. 서버에 부하를 덜 줄시 문제 상황이 발생하지 않았음
  B. 추후 확인

## 9.크레딧 (Credit)
  공동 개발  이승현 & 김동욱

## 10.업데이트 정보 (Change Log)

v 1.00 기본 통신모듈 및 DB구축 (update 2019.10.24)
