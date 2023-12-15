package com.example.marketapi.domain.cart.api;

import com.example.marketapi.domain.cart.api.model.GetCartInfo;
import com.example.marketapi.domain.cart.service.CartService;
import com.example.marketapi.domain.user.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping("/userCartInfo/{userId}")
    public GetCartInfo.Response getUserCartInfo(@PathVariable("userId") String userId, @AuthenticationPrincipal UserAccount userAccount) {
        // TODO: IMPLEMENT
        return null;
    }
}
