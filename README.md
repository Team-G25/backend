### 📪 Mailer - Backend
- 졸업 프로젝트 **Mailer**의 백엔드 파트입니다.  
- AI 기반 메일 자동 생성, 문체 교정, 실제 메일 발송 기능을 포함한  
- 인프라 구축 및 CI/CD 자동화까지 담당했습니다.

<br> 

**🧠 AI 메일 자동 생성**  
- 사용자 입력(상황, 키워드 등)을 기반으로 메일 본문 자동 생성  
- 자체 LLM 기반 AI 서버와 REST 통신  
- Spring Cloud OpenFeign + 커스텀 Decoder 구성

**✍️ 문체/문법 교정 기능**  
- OpenAI GPT-3.5 활용 (맞춤형 프롬프트 설계)  
- FeignClient + 글로벌 예외 처리로 안정적 호출

**📂 파일 업로드 (AWS S3)**  
- 프로필 및 첨부 이미지 업로드  
- URL 반환 + IAM 권한 최소화

**💌 메일 전송 시스템**  
- Gmail API (OAuth2), SendGrid API 사용  
- 인증 도메인 적용(`mailergo.io.kr`)

**🔐 보안/배포 인프라**  
- Nginx 리버스 프록시 + SSL 적용  
- Let’s Encrypt로 HTTPS 자동 갱신

**🚀 CI/CD 자동화**  
- GitHub Actions + AWS CodeDeploy 구성  
- Discord Webhook을 통한 알림 연동

**📄 API 문서 & 예외 처리**  
- Swagger(openapi) 자동 문서화  
- GlobalExceptionHandler 통한 표준화된 오류 응답

<br> 

**🙋‍♂️ 담당 역할**  
- 백엔드 전반 개발 및 배포 총괄  
- AI 연동, S3, 메일 전송, CI/CD, 보안 인프라 구현
