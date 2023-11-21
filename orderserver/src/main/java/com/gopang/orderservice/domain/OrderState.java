package com.gopang.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderState {

    COMPLETE("주문완료"),
    CANCEL("주문취소"),
    PAYCOMPLETE("결제완료"),
    PAYCANCEL("결제취소"),
    ITEMREADY("상품준비중"),
    DELIVERYREADY("배송준비중"),
    DELIVERYCOMPLETE("배송완료"),
    SELLERCANCEL("판매자취소");

    private final String state;

}
