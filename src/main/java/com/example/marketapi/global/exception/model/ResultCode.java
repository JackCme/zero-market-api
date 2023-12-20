package com.example.marketapi.global.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResultCode {
    OK(HttpStatus.OK, 2000, "OK")
    , INVALID_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, 4000, "올바른 요청이 아닙니다")
    , UNAUTHENTICATED(HttpStatus.UNAUTHORIZED, 4010, "인증된 요청이 아닙니다")
    , ACCESS_DENIED(HttpStatus.FORBIDDEN, 4030, "접근 권한이 없습니다")
    , INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 에러")
    , USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,4000, "이미 사용자가 존재합니다")
    , USER_NOT_FOUND(HttpStatus.NOT_FOUND, 4040, "사용자를 찾을 수 없습니다")
    , EMAIL_NOT_VALID(HttpStatus.BAD_REQUEST,4000, "올바른 이메일 형식이 아닙니다")
    , PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST,4000, "비밀번호의 형식이 올바르지 않습니다")
    , CART_INFO_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,4000, "이미 장바구니가 존재합니다")
    , CART_INFO_NOT_EXISTS(HttpStatus.NOT_FOUND,4040, "장바구니 정보가 없습니다")
    , CART_ITEM_EMPTY(HttpStatus.BAD_REQUEST,4000, "장바구니에 상품이 없습니다")
    , PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND,4040, "상품이 존재하지 않습니다")
    , INSUFFICIENT_STOCK_EXCEPTION(HttpStatus.BAD_REQUEST,4000, "상품의 재고가 부족합니다")
    , ORDER_INFO_NOT_EXISTS(HttpStatus.NOT_FOUND,4040, "존재하지 않는 주문입니다")
    , CANCEL_ORDER_ITEM_ALREADY_CANCELED(HttpStatus.BAD_REQUEST,4000, "이미 취소된 주문상품 입니다")
    , CANCEL_ORDER_ITEM_NOT_EXISTS(HttpStatus.NOT_FOUND,4040, "취소할 상품이 주문정보에 존재하지 않습니다")
    , CANCEL_COUNT_EXCEED_ORDER_COUNT(HttpStatus.BAD_REQUEST,4000, "주문한 상품보다 많이 취소할 수 없습니다")
    ;
    private final HttpStatus httpStatus;
    private final int statusCode;
    private final String description;
}
