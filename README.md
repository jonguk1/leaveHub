# 연차 관리 시스템

> **Java,Jsp와 Spring을 활용한 사내 연차 신청 및 승인 관리 플랫폼**

---

## 1. 핵심 기능 (Key Features)

### 직원
* **연차 관리**: 연차 신청, 수정 및 취소 기능
* **조회 서비스**: 본인의 연차 신청 내역 및 승인 상태 실시간 확인
* **파일 관리**: 연차 사유 증빙을 위한 파일 업로드 기능

### 관리자
* **결재 관리**: 전체 직원의 신청 목록 조회 및 승인/반려 처리
* **회원 관리**: 신규 가입 대기자 승인 및 사용자 권한(Role) 관리
* **데이터 관리**: 업로드된 증빙 서류 다운로드 및 검토

### 공통
* **인증/인가**: Spring Security 기반의 비밀번호 관리 및 Interceptor를 활용한 권한 분리 기능
* **계정 관리**: 회원가입, 정보 수정, 회원 탈퇴 로직 구현
* **UX 최적화**: 대량 데이터 조회를 위한 서버 사이드 페이징(Paging) 처리

---

## 🛠 2. 기술 스택 (Tech Stack)

### **Frontend**
- **Languages**: HTML5, CSS3, JavaScript
- **View Engine**: JSP

### **Backend**
- **Framework**: Spring Boot 3.4.x
- **Language**: Java 17
- **Security**: Spring Security
- **ORM/Mapper**: MyBatis

### **Database**
- **RDBMS**: Oracle Database 21c
- **Infrastructure**: Oracle Cloud Infrastructure (OCI)

### **DevOps & Tools**
- **Build Tool**: Maven
- **IDE**: Visual Studio Code
- **VCS**: GitHub

---

## 🏗 3. 시스템 아키텍처 (Architecture)



---

## 🔧 4. 주요 트러블슈팅 (Troubleshooting)

### ✅ SSL 인증서 적용 및 Keystore 구성
- **이슈**: Let's Encrypt에서 발급받은 PEM 인증서를 Spring Boot에서 직접 로드할 수 없는 문제.
- **해결**: `OpenSSL`을 이용하여 PEM 파일을 `PKCS12` 형식(`.p12`)으로 변환 후, `application.properties` 설정을 통해 HTTPS 포트(443)를 성공적으로 활성화함.

### ✅ Oracle Wallet 경로 설정 (Linux 환경)
- **이슈**: 로컬 개발 환경과 리눅스 서버 환경의 경로 차이로 인한 DB 연결 실패(`500 Error`).
- **해결**: `TNS_ADMIN` 환경 변수와 DB 접속 URL을 리눅스 절대 경로(`/home/ubuntu/wallet`)로 수정하고, `ojdbc.properties` 내의 경로 오타를 해결하여 클라우드 DB 연결 성공.

---
