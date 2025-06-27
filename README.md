Laundry365는 사용자가 손쉽게 세탁 서비스를 예약하고 관리할 수 있도록 설계된 웹 기반 세탁 서비스 플랫폼입니다.  
이 프로젝트는 Spring Boot와 JPA 기반으로 구축되었으며, JWT 인증과 AWS EC2 배포를 통해 실제 운영 환경을 시뮬레이션하였습니다.

## 🔍 프로젝트 개요

- **프로젝트명**: Laundry365
- **개발 기간**: (예: 2025.04 ~ 2025.06)
- **주요 기능**:
  - 사용자 회원가입/로그인 (JWT 기반 인증)
  - 세탁 서비스 예약 및 주문 관리
  - 관리자 페이지를 통한 주문 관리 및 상태 변경
  - 마이페이지를 통한 주문 내역 확인
  - 결제 연동 (Iamport 테스트 모드 사용)

## 🛠️ 기술 스택

| 구분       | 기술                                                      |
|------------|-----------------------------------------------------------|
| Backend    | Java 17, Spring Boot, Spring Data JPA, QueryDSL           |
| Frontend   | Thymeleaf, HTML/CSS, JavaScript                           |
| DB         | MySQL (AWS RDS), JPA                                      |
| 인증       | JWT (JSON Web Token)                                      |
| 배포       | AWS EC2, Docker Compose                            |
| 기타       | Git, GitHub, Lombok, Gradle, Iamport(아임포트)             |

## 🌐 서비스 아키텍처

[Client] → [Spring Boot API] → [MySQL RDS]
↓
[JWT 인증 처리]
↓
[AWS EC2 배포]

markdown
복사
편집

## 📷 주요 화면

> (여기에 화면 캡처 또는 설명 이미지 넣기)

- 사용자 메인 페이지
- 주문 등록 및 조회 화면
- 관리자 주문 관리 대시보드
- 결제 화면 (Iamport 연동)

## 🚀 배포 URL

> **URL**: http://your-domain.com (포트폴리오 목적의 배포 주소)

## 🙋‍♂️ 제작자

- **이름**: 김정연
- **GitHub**: [your-github-id](https://github.com/your-github-id)
- **Email**: your-email@example.com

---

> 이 프로젝트는 포트폴리오 용도로 제작되었으며, 실서비스가 아닌 테스트 목적으로 운영됩니다.
