package com.example.marketapi.domain.order.api;

import com.example.marketapi.domain.order.api.model.Cancel;
import com.example.marketapi.domain.order.api.model.Checkout;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    @PostMapping("/checkout")
    public Checkout.Response checkout(@RequestBody @Valid Checkout.Request request) {
        return null;
    }

    @PostMapping("/cancel")
    public Cancel.Response cancel(@RequestBody @Valid Cancel.Request request) {
        return null;
    }
}
