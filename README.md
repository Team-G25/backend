### 📪 Mailer - Backend

> 졸업 프로젝트 **Mailer**의 백엔드 파트입니다.  
> AI 기반 메일 자동 생성, 문체 교정, 실제 메일 발송 기능을 포함한  
> 인프라 구축 및 CI/CD 자동화까지 담당했습니다.

<br>

##### 🧠 AI 메일 자동 생성

- 사용자 입력(상황, 키워드 등)을 기반으로 메일 본문 자동 생성
- 자체 LLM 기반 AI 서버와 REST 통신
- Spring Cloud OpenFeign으로 통신 구조 구성 + 커스텀 Decoder 적용

<br>

##### ✍️ 문체/문법 교정 기능

- OpenAI GPT-3.5 활용 (역할/목적에 맞는 맞춤형 프롬프트 설계)
- FeignClient 및 글로벌 예외 처리로 안정적 API 호출

<br>

##### 📂 파일 업로드

- AWS S3에 프로필/첨부 이미지 업로드
- URL 반환 및 IAM 최소 권한 정책 적용

<br>

##### 💌 메일 전송 시스템

- Gmail API(OAuth2), SendGrid API를 통한 실제 메일 발송
- 인증 도메인 적용 (`mailergo.io.kr`)으로 신뢰도 향상

<br>

##### 🔐 인프라 보안

- Nginx 리버스 프록시 및 SSL(HTTPS) 구성
- Let’s Encrypt로 무료 인증서 자동 갱신

<br>

##### 🚀 CI/CD 자동화

- GitHub Actions + AWS CodeDeploy 기반 자동 배포
- 배포 상태는 Discord Webhook으로 실시간 알림

<br>

##### 📄 API 문서 & 예외 처리

- springdoc-openapi(Swagger)로 REST API 자동 문서화
- GlobalExceptionHandler로 표준화된 예외 응답 제공

<br>

##### 🛠 기술 스택

| 구분 | 사용 기술 |
|------|-----------|
| Language | Java 17 |
| Backend | Spring Boot 3.x, Spring Cloud OpenFeign, JPA |
| Infra | AWS EC2, S3, CodeDeploy, Nginx, SSL |
| API | GPT-3.5, Gmail API, SendGrid |
| Tools | GitHub Actions, Swagger, Discord Webhook |

<br>

##### 🙋‍♂️ 담당 역할

> 백엔드 전반 구현을 총괄했습니다:
> - GPT 및 자체 AI 서버 연동
> - 문체 교정 / 자동 생성 기능
> - 메일 발송 시스템 구현
> - S3 파일 업로드
> - CI/CD 및 배포 자동화 구성
