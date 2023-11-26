# zero-market-api

# 구현 요구사항
## 테이블

- [x] 회원정보
- [x] 상품정보
- [x] 장바구니 정보
- [x] 장바구니 상품정보
- [x] 주문정보
- [x] 주문 상품정보

## 필요 API

- [ ] 회원가입, 로그인 API
- [ ] 상품 관련 API
- [ ] 장바구니 관련 API
- [ ] 주문 관련 API

## 기본 정책
- 회원가입 시에 회원고유의 장바구니도 같이생성
- 상품 장바구니에 모두 넣고 주문성공하면 상품에서 재고관리
- 재고없으면 장바구니담기 불가능, 장바구니에 담겨져 있어도 주문도 불가능
- 상품 status값 주문하면 ORDERED 주문취소하면 CANCELED로 업데이트
- 파라미터값이 잘못된경우는  `IllegalArgumentException`  
- 재고가 없는경우는  `InsufficientStockException`
