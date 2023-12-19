package com.example.marketapi.domain.order.service;

import com.example.marketapi.domain.order.repository.OrderInfoRepository;
import com.example.marketapi.domain.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderInfoRepository orderInfoRepository;
    private final OrderItemRepository orderItemRepository;
}
