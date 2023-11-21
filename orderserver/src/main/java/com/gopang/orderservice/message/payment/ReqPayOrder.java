package com.gopang.orderservice.message.payment;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReqPayOrder {
    // 주문 고유 번호
    String order_id;
    
    // 총 결제 금액
    int amount;
}
