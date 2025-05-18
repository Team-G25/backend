# 📦 Mailer - Backend
> 졸업 프로젝트 Mailer의 백엔드 파트입니다.  
> AI 기반 이메일 자동 생성과 전송을 지원하며, 클라우드 인프라와 CI/CD 자동화까지 구현된 백엔드 시스템입니다.

---

### 🧠 **AI 기반 이메일 자동 생성 기능**

- **메일러 전용 AI 자동 생성 서버와의 연동**
    - 이메일 키워드, 상황, 수신자 유형 등을 기반으로 메일 본문을 자동 생성.
    - AI 서버와 REST 통신을 통해 생성 요청 처리.
- **Spring Cloud OpenFeign 활용**
    - FeignClient로 통신 구조를 구현하고, 응답 포맷에 맞춰 커스텀 Decoder 적용.

---

### ✍️ **AI 기반 어투 및 문법 교정 기능**

- **OpenAI GPT-3.5 API 직접 활용**
    - 사용자가 작성한 메일 초안의 문체 및 문법을 자연스럽고 공손하게 보정.
    - 역할, 목적, 문맥에 따라 맞춤형 프롬프트를 설계해 높은 교정 품질 확보.
- **Spring Cloud OpenFeign + 예외 처리 설계**
    - GPT 호출에도 FeignClient를 적용하여 통신을 일관성 있게 유지.
    - 전용 Decoder 및 글로벌 예외 핸들링 구조를 통해 안정적인 응답과 오류 대응 가능.

---

### 📂 **파일 업로드 및 스토리지**

- **Amazon S3**
    - 프로필 이미지 및 이메일 이미지 파일 저장소로 사용.
    - `AmazonS3Client`를 활용한 업로드 및 URL 반환 기능 구현.
    - IAM 최소 권한 정책을 적용하여 보안성 확보.

---

### 💌 **이메일 발송 시스템**

- **Gmail API**
    - OAuth2 인증을 통해 사용자의 Gmail 계정으로 실제 메일 발송 기능 구현.
- **SendGrid**
    - 인증 도메인(domain authentication)을 적용하여 `mailergo.io.kr`을 발신자 주소로 사용해 신뢰도 높은 메일 발송 환경 구축.

---

### 🔐 **보안 및 인프라 연결**

- **Nginx**
    - Spring Boot 서버 앞단에 리버스 프록시 구성.
    - 정적 리소스 제공 및 HTTPS(SSL) 적용으로 보안성과 접근성 강화.
- **Let’s Encrypt**
    - 무료 SSL 인증서를 자동 갱신하도록 구성하여 HTTPS 통신 활성화.

---

### 🚀 **CI/CD 자동화**

- **GitHub Actions + AWS CodeDeploy**
    - `main` 브랜치 push 시 자동으로 빌드 및 배포 수행.
    - JAR 아티팩트는 S3에 업로드 후, EC2에서 배포 스크립트 실행.
    - `-spring.profiles.active` 플래그로 멀티 프로필 설정 분기.
- **Discord Webhook**
    - 배포 성공/실패 결과를 실시간으로 Discord에 알림 전송.

---

### 📄 **API 명세 및 예외 처리**

- **Swagger (springdoc-openapi)**
    - REST API 스펙을 자동으로 문서화하고, 프론트엔드 개발자가 쉽게 연동할 수 있도록 지원.
    - 각 API 엔드포인트에 요청/응답 형식, 상태 코드, 예시 등을 명확히 표기.
- **GlobalExceptionHandler**
    - 서비스 전반의 예외를 일관된 구조로 처리하도록 공통 예외 처리 클래스 구성.
    - 클라이언트가 오류 상황을 명확히 인지하고 대응할 수 있도록 표준화된 에러 응답 포맷 제공.

---

## 🛠 주요 기술 스택

| 분야 | 기술 |
|------|------|
| 언어 | Java 17 |
| 프레임워크 | Spring Boot 3.x, Spring Cloud OpenFeign, Spring Data JPA |
| 인프라 | AWS EC2, S3, CodeDeploy, Nginx, SSL |
| 외부 API | OpenAI GPT-3.5, Gmail API, SendGrid |
| 도구 | GitHub Actions, Swagger, Discord Webhook |

---

## 🙋‍♂️ 담당 역할 요약

> 백엔드 개발을 총괄하며 다음 기능을 직접 구현하였습니다:
- 커스텀 LLM 모델 서버와 연동
- GPT 연동을 통한 AI 메일 자동 생성 기능
- OpenAI 기반 문체 교정 기능
- AWS S3 파일 업로드
- 이메일 발송 시스템 구성
- 전체 CI/CD 파이프라인 구축 (GitHub Actions + CodeDeploy)
- 서버 배포 환경 구성 (Nginx + SSL)
