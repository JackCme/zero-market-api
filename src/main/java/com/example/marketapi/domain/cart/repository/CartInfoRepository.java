package com.example.marketapi.domain.cart.repository;

import com.example.marketapi.domain.cart.entity.CartInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartInfoRepository extends JpaRepository<CartInfo, Long> {
    CartInfo findCartInfoByUserAccountUserId(Long userId);
}
