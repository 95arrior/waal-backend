# WAAL Backend

반려견 유치원 플랫폼 Spring Boot API 서버

## 기술 스택

- Java 17 + Spring Boot 3.2.x
- PostgreSQL + Spring Data JPA
- Spring Security + OAuth2 (카카오/네이버) + JWT
- AWS S3 (이미지 업로드)
- springdoc-openapi (Swagger UI)
- Railway 배포

## 로컬 개발 환경 설정

### 1. 사전 준비

```bash
# Java 17 설치
brew install openjdk@17

# 환경변수 설정 (~/.zshrc)
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
export PATH="$JAVA_HOME/bin:$PATH"

# Maven 설치
brew install maven
```

### 2. PostgreSQL 설정

```bash
# PostgreSQL 설치 및 실행
brew install postgresql@16
brew services start postgresql@16

# DB 생성
psql postgres -c "CREATE USER waal WITH PASSWORD 'waal1234';"
psql postgres -c "CREATE DATABASE waal_dev OWNER waal;"
```

### 3. 환경변수 설정

```bash
cp .env.example .env.local
# .env.local 파일에 실제 값 입력
```

### 4. 실행

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## API 문서

서버 실행 후 http://localhost:8080/swagger-ui.html

## API 엔드포인트 요약

| 메서드 | 경로 | 설명 | 권한 |
|--------|------|------|------|
| GET | /api/v1/users/me | 내 정보 조회 | 인증 |
| PATCH | /api/v1/users/me | 내 정보 수정 | 인증 |
| POST | /api/v1/kindergartens | 유치원 등록 | 인증 |
| GET | /api/v1/kindergartens/{id} | 유치원 조회 | 공개 |
| PATCH | /api/v1/kindergartens/{id} | 유치원 수정 | OWNER |
| GET | /api/v1/kindergartens/me | 내 소속 유치원 | 인증 |
| GET | /api/v1/kindergartens/{id}/members | 멤버 목록 | OWNER/TEACHER |
| POST | /api/v1/kindergartens/{id}/invite-tokens | 초대 토큰 생성 | OWNER |
| POST | /api/v1/invite-tokens/{token}/accept | 초대 수락 | 인증 |
| POST | /api/v1/dogs | 반려견 등록 | 인증 |
| GET | /api/v1/dogs | 내 반려견 목록 | 인증 |
| GET | /api/v1/dogs/{id} | 반려견 상세 | 보호자 |
| PATCH | /api/v1/dogs/{id} | 반려견 수정 | 보호자 |
| DELETE | /api/v1/dogs/{id} | 반려견 삭제 | 보호자 |
| POST | /api/v1/kindergartens/{id}/connections | 연결 요청 | GUARDIAN |
| GET | /api/v1/kindergartens/{id}/connections | 연결 목록 | OWNER/TEACHER |
| GET | /api/v1/connections/me | 내 연결 목록 | GUARDIAN |
| PATCH | /api/v1/connections/{id}/approve | 연결 승인 | OWNER |
| PATCH | /api/v1/connections/{id}/reject | 연결 거절 | OWNER |
| POST | /api/v1/reservations | 예약 생성 | 인증 |
| GET | /api/v1/dogs/{id}/reservations | 반려견 예약 목록 | 보호자 |
| GET | /api/v1/kindergartens/{id}/reservations | 유치원 예약 목록 | OWNER/TEACHER |
| GET | /api/v1/kindergartens/{id}/capacity | 날짜별 정원 | 공개 |
| PATCH | /api/v1/reservations/{id}/confirm | 예약 확정 | OWNER/TEACHER |
| PATCH | /api/v1/reservations/{id}/cancel | 예약 취소 | 인증 |
| POST | /api/v1/daily-reports | 보고서 작성 | OWNER/TEACHER |
| GET | /api/v1/daily-reports/{id} | 보고서 상세 | 인증 |
| GET | /api/v1/dogs/{id}/daily-reports | 반려견 보고서 | GUARDIAN |
| GET | /api/v1/kindergartens/{id}/daily-reports | 유치원 보고서 | OWNER/TEACHER |
| PATCH | /api/v1/daily-reports/{id} | 보고서 수정 | OWNER/TEACHER |
| POST | /api/v1/images/upload | 이미지 업로드 | 인증 |

## Railway 배포

Railway 프로젝트에서 다음 환경변수를 설정하세요:

- `DATABASE_URL` (Railway PostgreSQL 자동 설정)
- `JWT_SECRET`
- `KAKAO_CLIENT_ID` / `KAKAO_CLIENT_SECRET`
- `NAVER_CLIENT_ID` / `NAVER_CLIENT_SECRET`
- `S3_BUCKET_NAME` / `S3_REGION`
- `AWS_ACCESS_KEY_ID` / `AWS_SECRET_ACCESS_KEY`
- `CORS_ALLOWED_ORIGINS`
