package com.example.marketapi.domain.order.api;

import com.example.marketapi.domain.order.api.model.Cancel;
import com.example.marketapi.domain.order.api.model.Checkout;
import com.example.marketapi.domain.order.service.OrderService;
import com.example.marketapi.domain.user.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/checkout")
    public Checkout.Response checkout(@RequestBody @Valid Checkout.Request request, @AuthenticationPrincipal UserAccount userAccount) {
        return new Checkout.Response(orderService.checkoutOrderFromCart(request.getCartId(), userAccount.getUserId()));
    }

    @PostMapping("/cancel")
    public Cancel.Response cancel(@RequestBody @Valid Cancel.Request request, @AuthenticationPrincipal UserAccount userAccount) {
        return new Cancel.Response(orderService.cancelOrderProduct(request.getOrderId(), request.getProductId(), userAccount.getUserId()));
    }
}
