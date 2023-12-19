package com.example.marketapi.domain.order.service;

import com.example.marketapi.domain.order.dto.OrderInfoDto;
import com.example.marketapi.domain.order.repository.OrderInfoRepository;
import com.example.marketapi.domain.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderInfoRepository orderInfoRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderInfoDto checkoutOrderFromCart(Long cartId, Long userId) {
        return null;
    }

    public OrderInfoDto cancelOrderProduct(Long orderId, Long productId, Long userId) {
        return null;
    }
}
