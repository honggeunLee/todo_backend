# Calendar API

## 1. 개요

Calendar API는 사용자가 할 일을 관리할 수 있는 일정 관리 시스템입니다. 사용자는 일정(Todo)을 생성, 조회, 수정, 삭제할 수 있으며, 태그를 추가하여 일정들을 분류할 수 있습니다.
또한, 완료 여부 및 아카이브 기능을 지원하여 보다 체계적인 일정 관리가 가능합니다.

## 2. 소스 빌드 및 실행 방법

### 2.1 필수 환경

- Java 17 이상
- MySQL Database
- Gradle 7+

### 2.2 데이터베이스 설정 및 프로젝트 빌드/실행

1. 프로젝트 클론:
   ```sh
   git clone https://github.com/honggeunLee/todo_backend
   ```
2. MySQL에 데이터베이스 생성:
   ```sql
   CREATE DATABASE todo_db;
   ```
3. 기존 데이터 복원 (`todo_db_backup.sql` 파일 사용, 프로젝트 루트경로에 존재합니다.):
   ```sh
   mysql -u root -p todo_db < todo_db_backup.sql
   ```
4. `application.yml` 설정 (아래와 같이 환경변수로 설정 되어있기 때문에 환경변수 설정을 해주어야 합니다.):
   ```yml
   datasource:
       url: ${DB_URL}
       username: ${DB_USERNAME}
       password: ${DB_PASSWORD}
   ```
   아래의 코드로 설정하거나 application.yml 파일에서 datasource에 직접 수정
   ```sh
   # PowerShell
   $env:DB_URL="jdbc:mysql://localhost:3306/todo_db?useSSL=false&serverTimezone=UTC"
   $env:DB_USERNAME="your_db_username"
   $env:DB_PASSWORD="your_db_password"
   
   # CMD (명령 프롬프트)
   set DB_URL "jdbc:mysql://localhost:3306/todo_db?useSSL=false&serverTimezone=UTC"
   set DB_USERNAME "your_db_username"
   set DB_PASSWORD "your_db_password"
   
   # macOS / Linux (Bash / Zsh)
   export DB_URL="jdbc:mysql://localhost:3306/todo_db?useSSL=false&serverTimezone=UTC"
   export DB_USERNAME="your_db_username"
   export DB_PASSWORD="your_db_password"
   
   ######### your_db_username, your_db_password는 실제 mysql 계정을 사용해야합니다. ###########
   ```
4. 빌드 및 실행 (DB 환경변수 관련 에러 발생시 환경변수 설정 혹은 application.yml 파일의 datasource 수정 필수)
   ```sh
   cd [프로젝트 경로] # 실제 경로
   ./gradlew build
   ./gradlew bootRun
   ```


## 3. 주력 라이브러리 및 사용 이유

- **Spring Boot**: 빠른 개발과 설정 간소화를 위해 사용했습니다.
- **Spring Boot Web**: RESTful API 개발을 위해 사용했습니다.
- **Spring Data JPA**: 데이터베이스 접근을 간편하게 하기 위해 사용했습니다.
- **Lombok**: 보일러플레이트 코드를 줄이기 위해 사용했습니다. (@Getter, @Setter, @Builder 등)
- **Springdoc OpenAPI**: Swagger UI를 통해 API 문서를 자동으로 생성하기 위해 사용했습니다.

## 4. API 명세서

프로젝트 실행 후 http://localhost:8080/swagger-ui/index.html 로 접속하여 명세서 확인 가능합니다.

## 5. 테스트 케이스

아래는 API 서버에서 직접 테스트할 수 있는 케이스입니다. 각 요청을 API 명세서(http://localhost:8080/swagger-ui/index.html) 에서 직접 테스트 할 수 있습니다.

### 5.1 Todo 테스트 케이스

1. Todo 등록
2. Todo 조회 (목록)
3. Todo 조회 (상세)
4. Todo 수정
5. Todo 삭제
6. Todo 완료 처리
7. Todo 아카이브 처리
8. 아카이브된 Todo 조회
9. Todo에 태그 추가
10. Todo에 태그 수정
11. Todo에 태그 삭제

### 5.2 Tag 테스트 케이스

1. 새로운 Tag 등록
2. 전체 Tag 조회 (목록)
3. Tag 이름을 기반으로 조회

