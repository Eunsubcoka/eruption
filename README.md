# eruption

한정 수량 굿즈를 예약하고 모의 결제까지 할 수 있는 단일 운영자 굿즈 몰 백엔드 프로젝트입니다.

> 🚧 개발 진행 중 (현재: 초기 환경 세팅 완료)

## 기술 스택

| 영역 | 기술 |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.1.x |
| ORM | Spring Data JPA |
| Database | MySQL 8 (Docker Compose) |
| Build | Gradle |

예정: Spring Security + JWT, Swagger(springdoc), JUnit 5

## 실행 방법

**사전 준비**: JDK 21, Docker Desktop

```bash
# 1. DB 실행
docker compose up -d

# 2. 애플리케이션 실행
./gradlew bootRun        # Windows: gradlew.bat bootRun
```

- DB: `localhost:3306` / 데이터베이스명 `eruption`
- 로컬 개발용 설정이므로 실제 서비스에서는 접속 정보를 환경 변수로 분리해야 합니다.

## 프로젝트 범위

**구현 범위**
- 회원 가입 / 로그인
- 굿즈 조회 (목록, 상세)
- 옵션 선택 후 예약
- 모의 결제
- 예약 조회 / 취소
- 재고 관리 (DB 직접 입력)

**구현하지 않는 것**
- 리뷰, 추천, 정산
- 카테고리, 장바구니
- 실제 PG 연동
- 배송 관리
- 프론트엔드 (백엔드 MVP 완료 후 재검토)

## 데이터 모델

| 테이블 | 설명 | 주요 컬럼 |
|---|---|---|
| User | 회원 | id, email, password, name, role |
| Goods | 굿즈 | id, title, description, reservation_start_at, reservation_end_at |
| GoodsOption | 옵션 + 재고 | id, goods_id, option_name, price, stock_qty, max_qty |
| Orders | 예약/주문 | id, user_id, order_no, total_amt, status, expired_at |
| OrderItems | 예약 상세 | id, order_id, goods_option_id, qty, price |
| Payments | 결제 | id, order_id, payment_key, amt, status, approved_at |

관계: User 1:N Orders, Orders 1:N OrderItems, Orders 1:1 Payments, Goods 1:N GoodsOption, GoodsOption 1:N OrderItems

> ERD 이미지는 추후 `docs/` 폴더에 추가 예정

## 개발 진행 상황

- [x] 기획 (범위, ERD, 기술 스택)
- [x] 프로젝트 생성 및 DB 연결
- [ ] 엔티티 및 연관관계 매핑
- [ ] 회원 가입 / 로그인 (JWT)
- [ ] 굿즈 조회 API
- [ ] 예약 생성 / 조회 / 취소 API
- [ ] 모의 결제 API
- [ ] API 문서화 (Swagger)
- [ ] 테스트 코드
