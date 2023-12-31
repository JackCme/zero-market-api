# zero-market-api

# How to run
```bash
$ cd docker/mysql
$ docker compose up -d
```

# 구현 요구사항
## 테이블

- [x] 회원정보
- [x] 상품정보
- [x] 장바구니 정보
- [x] 장바구니 상품정보
- [x] 주문정보
- [x] 주문 상품정보

## 필요 API

- [x] 회원가입 API
  - [x] 비밀번호 암호화 처리
  - [x] 회원 장바구니 생성
- [x] 로그인 API
  - [x] 스프링 시큐리티 JWT 적용
  - [x] JWT 토큰 유효성 검사
  - [x] Route guard 적용
- [x] 장바구니 관련 API
  - [x] 본인 장바구니 외에 사용 못하도록 Auth 처리
  - [x] 상품 장바구니에 담기
    - [x] 상품재고 확인하기
  - [x] 장바구니 목록 조회하기
- [x] 주문 관련 API
  - [x] 주문 생성시 장바구니 번호로 주문생성
    - [x] 주문성공 시 상품 재고관리 호출
  - [x] 주문 취소시 주문번호로 주문취소
    - [x] 주문번호 생성체계 생각해보기
- [x] 상품 관련 API
  - [x] 상품조회 서비스
  - [x] 상품재고차감 로직

## 기본 정책
- [x] 회원가입 시에 회원고유의 장바구니도 같이생성
- [x] 상품 장바구니에 모두 넣고 주문성공하면 상품에서 재고관리
- [x] 재고없으면 장바구니담기 불가능,
- [x] 장바구니에 담겨져 있어도 주문도 불가능
- [x] 상품 status값 주문하면 ORDERED 주문취소하면 CANCELED로 업데이트
- [x] 파라미터값이 잘못된경우는  `IllegalArgumentException`  
- [x] 재고가 없는경우는  `InsufficientStockException`
