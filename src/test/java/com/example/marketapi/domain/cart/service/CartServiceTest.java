package com.example.marketapi.domain.cart.service;

import com.example.marketapi.domain.cart.entity.CartInfo;
import com.example.marketapi.domain.cart.entity.CartItem;
import com.example.marketapi.domain.cart.repository.CartInfoRepository;
import com.example.marketapi.domain.cart.repository.CartItemRepository;
import com.example.marketapi.domain.product.dto.ProductDto;
import com.example.marketapi.domain.product.entity.Product;
import com.example.marketapi.domain.product.repository.ProductRepository;
import com.example.marketapi.domain.product.service.ProductService;
import com.example.marketapi.domain.user.entity.UserAccount;
import com.example.marketapi.global.exception.GlobalException;
import com.example.marketapi.global.exception.model.ResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @Mock
    private CartInfoRepository cartInfoRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductService productService;
    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("사용자 장바구니 생성 성공")
    void createUserCartSuccess() {
        // Given
        Long userId = 1L;
        UserAccount userAccount = UserAccount.builder()
                .userId(userId)
                .build();
        CartInfo cartInfo = CartInfo.builder()
                .userAccount(userAccount)
                .build();
        given(cartInfoRepository.findCartInfoByUserAccountUserId(anyLong())).willReturn(Optional.empty());
        given(cartInfoRepository.save(any())).willReturn(cartInfo);
        ArgumentCaptor<CartInfo> captor = ArgumentCaptor.forClass(CartInfo.class);
        // When
        assertDoesNotThrow(() -> cartService.createUserCart(userId));
        // Then
        verify(cartInfoRepository, times(1)).save(captor.capture());
        assertEquals(captor.getValue().getUserAccount().getUserId(), cartInfo.getUserAccount().getUserId());
    }

    @Test
    @DisplayName("사용자 장바구니 생성 실패 - 이미 장바구니 정보 존재")
    void createUserCartFailed_CartAlreadyExists() {
        // Given
        Long userId = 1L;
        UserAccount userAccount = UserAccount.builder()
                .userId(userId)
                .build();
        CartInfo cartInfo = CartInfo.builder()
                .userAccount(userAccount)
                .build();
        given(cartInfoRepository.findCartInfoByUserAccountUserId(anyLong())).willReturn(Optional.of(cartInfo));
        // When
        GlobalException cartException = assertThrows(GlobalException.class, () -> cartService.createUserCart(userId));
        // Then
        assertEquals(ResultCode.CART_INFO_ALREADY_EXISTS, cartException.getResultCode());
        assertEquals(ResultCode.CART_INFO_ALREADY_EXISTS.getDescription(), cartException.getResultCode().getDescription());
    }

    @Test
    @DisplayName("장바구니 상품담기 성공")
    void addProductToCart() {
        // Given
        Long cartId = 1L;
        CartInfo cartInfo = CartInfo.builder()
                .cartId(cartId)
                .build();
        Long productId = 1L;
        String productName = "샘플상품";
        Long inStock = 10L;
        Product product = Product.builder()
                .name(productName)
                .inStock(inStock)
                .productId(productId)
                .build();
        Long productCount = 2L;
        CartItem cartItem = CartItem.builder()
                .productCnt(productCount)
                .cartInfo(cartInfo)
                .product(product)
                .build();
        given(productService.getProduct(productId)).willReturn(ProductDto.fromEntity(product));
        given(cartInfoRepository.findById(cartId)).willReturn(Optional.of(cartInfo));
        given(cartInfoRepository.save(any())).willReturn(cartInfo);
        given(cartItemRepository.save(any())).willReturn(cartItem);

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.add(cartItem);
        given(cartItemRepository.findCartItemsByCartInfo(cartInfo)).willReturn(cartItemList);
        ArgumentCaptor<CartInfo> captor = ArgumentCaptor.forClass(CartInfo.class);
        ArgumentCaptor<CartItem> itemArgumentCaptor = ArgumentCaptor.forClass(CartItem.class);

        // When
        cartService.addProductToCart(cartId, productId, productCount);
        // Then
        verify(cartItemRepository, times(1)).save(itemArgumentCaptor.capture());
        assertEquals(itemArgumentCaptor.getValue().getCartInfo().getCartId(), cartId);
        assertEquals(itemArgumentCaptor.getValue().getProduct().getProductId(), productId);


        verify(cartInfoRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getItemCount()).isEqualTo(cartInfo.getItemCount() + 1);
    }

    @Test
    @DisplayName("장바구니 상품담기 실패 - 상품 재고없음")
    void addProductToCartFailed_InsufficientProductStock() {
        // Given
        Long cartId = 1L;
        Long productId = 1L;
        String productName = "샘플상품";
        Long inStock = 1L;
        Product product = Product.builder()
                .name(productName)
                .inStock(inStock)
                .productId(productId)
                .build();
        Long count = 10L;
        CartInfo cartInfo = CartInfo.builder()
                .cartId(cartId)
                .build();
        given(productService.getProduct(productId)).willReturn(ProductDto.fromEntity(product));
        given(cartInfoRepository.findById(cartId)).willReturn(Optional.of(cartInfo));
        // When
        GlobalException productException = assertThrows(GlobalException.class, () -> cartService.addProductToCart(cartId, productId, count));
        // Then
        assertEquals(ResultCode.INSUFFICIENT_STOCK_EXCEPTION, productException.getResultCode());
        assertEquals(ResultCode.INSUFFICIENT_STOCK_EXCEPTION.getDescription(), productException.getResultCode().getDescription());
    }
}
