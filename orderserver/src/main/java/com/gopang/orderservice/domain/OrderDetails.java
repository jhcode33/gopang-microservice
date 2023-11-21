package com.gopang.orderservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {

    @Id @GeneratedValue
    @Column(name = "detail_id")
    public Long id;

    // 주문자 고유번호
    public Long user_id;

    // 주문 상품 고유번호
    public Long item_id;
    
    // 주문 상품 이름
    public String item_name;

    // 주문 상품 가격
    public Long price;
    
    // 주문 상품 수량
    public Long amount;

    // 해당 주문 상품 총 가격
    // 개별 가격 * 수량
    public Long totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Orders orders;

    // 주문상태
    // 없애기
//    @Column
//    @Enumerated(EnumType.STRING)
//    public OrderState order_state;

}
