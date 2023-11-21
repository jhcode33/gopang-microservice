package com.gopang.paymentserver.dto.Paymentdto;

import lombok.Getter;

@Getter
//@RequiredArgsConstructor
public enum PaymentStatus {
    PAYCOMPLETE("결제성공"),
    PAYFAIL("결제실패");

    private final String state;

    PaymentStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
