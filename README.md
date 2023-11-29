# 여행 계획 짜기 JYTRIP (feat. 로그인에 대한 고찰)

#  Overview


# JYTRIP 서비스 화면

### 로그인


### 


### 


### 게시판


### 


### 



#  주요 기능

### 서비스 소개


### 프로젝트 기능


### 개발환경
- OS
    - Local : Windows 10
    - AWS : Ubuntu 20.04.4 LTS
- IDE
    - IntelliJ IDE 2022.1.3
    - Visual Studio Code 1.70.0
- UI / UX
    - Figma
- DataBase
    - MySQL workbench 8.0.20 (Windows 10)
    - MySQL 8.0.30 (Ubuntu 20.04.2 LTS)
- CI/CD
    - Docker 20.10.17
    - nginx

### 상세 스택
- Backend
    - JAVA - openjdk 1.8.0_342
    - Gradle 6.7
    - SpringBoot 2.4.5, Quarydsl 4.4.0, Lombok 1.18.20, Swagger2 3.0.0
    - Stomp 2.3.3-1
- FrontEnd
    - HTML5, CSS3, JavaScript(ES6)
    - React 18.2.0, face-api.js 0.22.2, Material-UI
    - Node.js 16.16.0, npm 8.15.0
    - stompjs 2.3.3
- Web RTC
    - openvidu-browser 2.22.0

### 협업 툴
- 형상 관리 : Gitlab, Git
- 커뮤니케이션 : Notion, MatterMost, Webex

### 서비스 아키텍처

### 마일스톤
<img src="https://user-images.githubusercontent.com/56749776/185520326-3b83b272-9c20-4f2e-824a-997040bc662d.png" width="600" height="650">


### 화면 설계서 
<img src="https://user-images.githubusercontent.com/56749776/185520252-dbc7307b-268a-4edc-a337-c0b588279785.png" width="600" height="500">


### Git 컨벤션
- Feat : 새로운 기능 추가 (넓은 개념)
- Fix : 버그 수정
- Design : css 등 UI 디자인 변경
- Add : 코드, 테스트, 예제, 문서 등 추가 생성 (좁은 개념)
- Delete : 코드 삭제
- Rename : 이름 변경, 위치 옮기는 작업
- Refactor : 코드 전면 수정
- Test : 테스트 코드 추가, 테스트 코드 리팩토링

### 커밋타입
- 명령어로 작성한다.
- 백앤드 [BE]와 프론트앤드 [FE] 구분
```
[Category] Type : subject

ex) 회원가입 기능

- 프론트 - [FE] Feat : Join in
- 백 - [BE] Feat : Join in
```

### EC2 포트 정리
| --- | --- |
| 80 | nginx HTTP 기본 포트 |
| 443 | nginx HTTPS |
| 3306 | mysql |
| 5173 | Vue app |
| 8080 | Spring boot |


### 팀원 역할
