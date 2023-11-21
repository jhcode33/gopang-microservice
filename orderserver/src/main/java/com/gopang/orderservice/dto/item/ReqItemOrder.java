package com.gopang.orderservice.dto.item;

import lombok.Getter;

@Getter
public class ReqItemOrder {
    // 아이템 고유번호
    public Long itemId;

    // 주문 수량
    public Long amount;
}
