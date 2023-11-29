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
    - STS 3.6.14
    - Visual Studio Code 1.70.0
- DataBase
    - MySQL workbench 8.0.20 (Windows 10)
    - MySQL 8.0.30 (Ubuntu 20.04.2 LTS)
- CI/CD
    - nginx

### 상세 스택
- Backend
    - JAVA - openjdk 1.8.0_342
    - Maven 3.5.3
    - SpringBoot 2.4.5, Lombok 1.18.20, Swagger2 3.0.0
- FrontEnd
    - HTML5, CSS3, JavaScript(ES6)
    - Vue 3, BootStrap
    - Node.js 20.16.0, npm 8.15.0

### 협업 툴
- 형상 관리 : Gitlab, Git
- 커뮤니케이션 : Notion, MatterMost, Webex

### 서비스 아키텍처


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
