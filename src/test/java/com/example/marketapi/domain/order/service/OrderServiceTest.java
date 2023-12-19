package com.example.marketapi.domain.order.service;

import com.example.marketapi.domain.order.repository.OrderInfoRepository;
import com.example.marketapi.domain.order.repository.OrderItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderInfoRepository orderInfoRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @InjectMocks
    private OrderService orderService;


    @Test
    @DisplayName("주문 생성 성공")
    void orderCheckoutSuccess() {
        // Given

        // When

        // Then
    }

    @Test
    @DisplayName("주문 생성 실패 - 상품 재고 없음")
    void orderCheckoutFailed_InsufficientStock() {

    }

    @Test
    @DisplayName("주문 생성 실패 - 장바구니 상품 없음")
    void orderCheckoutFailed_NoProductInCart() {

    }

    @Test
    @DisplayName("주문 취소 성공")
    void orderCancelSuccess() {

    }

    @Test
    @DisplayName("주문 취소 싪패 - 주문이 존재하지 않음")
    void orderCancelFailed_OrderDoesNotExists() {

    }

    @Test
    @DisplayName("주문 취소 실패 - 이미 취소된 주문")
    void orderCancelFailed_OrderAlreadyCanceled() {

    }

    @Test
    @DisplayName("주문 취소 실패 - 취소할 주문 상품이 존재하지 않음")
    void orderCancelFailed_OrderItemDoesNotExists() {

    }

    @Test
    @DisplayName("주문 취소 실패 - 취소할 주문 상품의 갯수가 주문 상품 갯수를 초과함")
    void orderCancelFailed_CancelCountExceedOrderCount() {

    }
}
