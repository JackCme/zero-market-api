package com.example.marketapi.domain.cart.api;

import com.example.marketapi.domain.cart.api.model.CartAddProduct;
import com.example.marketapi.domain.cart.api.model.GetCartInfo;
import com.example.marketapi.domain.cart.dto.CartInfoDto;
import com.example.marketapi.domain.cart.service.CartService;
import com.example.marketapi.domain.user.entity.UserAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

// TODO: 테스트 코드 작성
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping("/userCartInfo")
    public GetCartInfo.Response getUserCartInfo(@AuthenticationPrincipal UserAccount userAccount) {
        CartInfoDto userCartInfo = cartService.getUserCartInfo(userAccount.getUserId());

        return GetCartInfo.Response.from(userCartInfo);
    }

    @PostMapping("/addProduct")
    public GetCartInfo.Response addProductToCart(@AuthenticationPrincipal UserAccount userAccount,
                                                    @RequestBody CartAddProduct.Request body) {
        CartInfoDto userCartInfo = cartService.getUserCartInfo(userAccount.getUserId());

        CartInfoDto cartInfoDto = cartService.addProductToCart(userCartInfo.getCartId(), body.getProductId(), body.getCount());
        return GetCartInfo.Response.from(cartInfoDto);
    }
}
