package com.example.marketapi.domain.cart.entity;

import com.example.marketapi.domain.user.entity.UserAccount;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long cartId;
    @Builder.Default
    @Setter
    private Long itemCount = 0L;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "cartInfo")
    @Builder.Default
    private List<CartItem> cartItemList = new ArrayList<>();
}
