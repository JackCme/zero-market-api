package com.example.marketapi.domain.cart.service;

import com.example.marketapi.domain.cart.repository.CartInfoRepository;
import com.example.marketapi.domain.cart.repository.CartItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartInfoRepository cartInfoRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("사용자 장바구니 생성 성공")
    void createUserCartSuccess() {

    }

    @Test
    @DisplayName("사용자 장바구니 생성 실패 - 사용자가 존재하지 않음")
    void createUserCartFailed_UserDoesNotExists() {

    }
    @Test
    @DisplayName("사용자 장바구니 생성 실패 - 이미 장바구니 정보 존재")
    void createUserCartFailed_CartAlreadyExists() {

    }

    @Test
    @DisplayName("사용자 장바구니 상품목록 조회 성공")
    void getUserCartItemsSuccess() {
    }

    @Test
    @DisplayName("장바구니 상품담기 성공")
    void addProductToCart() {
    }

    @Test
    @DisplayName("장바구니 상품담기 실패 - 상품 재고없음")
    void addProductToCartFailed_InsufficientProductStock() {

    }
}