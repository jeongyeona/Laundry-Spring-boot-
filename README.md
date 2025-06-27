# 🧺 Laundry365

Laundry365는 사용자가 손쉽게 세탁 서비스를 예약하고 관리할 수 있도록 설계된 **웹 기반 세탁 서비스 플랫폼**입니다.  
Spring Boot와 JPA 기반으로 구축되었으며, JWT 인증 및 AWS EC2 배포를 통해 실제 운영 환경을 시뮬레이션하였습니다.

---

## 🔍 프로젝트 개요

- **프로젝트명**: Laundry365
- **개발 기간**: 2025.04 ~ 2025.06
- **주요 기능**:
  - 🔐 사용자 회원가입/로그인 (JWT 기반 인증)
  - 🧾 세탁 서비스 예약 및 주문 관리
  - 🛠️ 관리자 페이지를 통한 주문 관리 및 상태 변경 (개발 중)
  - 📦 마이페이지를 통한 주문 내역 확인
  - 💳 결제 연동 (Iamport 테스트 모드 사용)

---

## 🛠️ 기술 스택

| 구분       | 기술                                                      |
|------------|-----------------------------------------------------------|
| Backend    | Java 17, Spring Boot, Spring Data JPA, QueryDSL           |
| Frontend   | Thymeleaf, HTML/CSS, JavaScript                           |
| DB         | MySQL (Docker Volume), JPA                                      |
| 인증       | JWT (JSON Web Token)                                      |
| 배포       | AWS EC2, Docker Compose                                   |
| 기타       | Git, GitHub, Lombok, Gradle, Iamport(아임포트)             |

---

## 🌐 서비스 아키텍처

[Client]

↓

[Spring Boot API]

↓

[JWT 인증 처리]

↓

[MySQL (Docker Volume)]

↓

[AWS EC2 (Docker 배포)]

---

## 📷 주요 화면

- **메인 페이지**: 세탁 서비스 소개 및 빠른 예약
- **주문 등록/조회**: 서비스 종류 선택, 주소 입력, 요청사항 작성
- **결제 화면**: Iamport 연동을 통한 카드 결제 시뮬레이션
- **관리자 대시보드**: 전체 주문 현황 확인 및 상태 변경 기능 (개발 중)

> 🔐 **테스트용 관리자 계정**  
> - ID: `kim9502071`  
> - PW: `1q2w3e4r12!`  
> ※ 테스트 목적의 계정입니다. 보안상 주의해주세요.

---

## 🚀 배포 URL

👉 [http://laundry365.store:8080/](http://laundry365.store:8080/)

---

## 🙋‍♂️ 제작자

- **이름**: 김정연
- **Email**: kim9502071@naver.com

---

> ⚠️ 이 프로젝트는 포트폴리오 용도로 제작되었으며, 실제 서비스가 아닌 테스트 환경에서 운영됩니다.
