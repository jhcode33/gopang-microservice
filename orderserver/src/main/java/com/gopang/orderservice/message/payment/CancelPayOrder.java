package com.gopang.orderservice.message.payment;

import lombok.*;

@Getter
@Builder
public class CancelPayOrder {
    // 주문 고유 번호
    String merchant_Uid;
    // 취소 완료 금액
    int cancelAmount;
    // 총 결제 금액
    int amount;
    // 취소 후 남은 금액
    int remainingBalance;
    // 취소 상태
    String status;

    @Override
    public String toString() {
        return "CancelPayOrder{" +
                "merchant_Uid='" + merchant_Uid + '\'' +
                ", cancelAmount=" + cancelAmount +
                ", amount=" + amount +
                ", remainingBalance=" + remainingBalance +
                ", status='" + status + '\'' +
                '}';
    }


}
