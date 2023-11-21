package com.gopang.orderservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PAYCOMPLETE("결제성공"),
    PAYFAIL("결제실패");

    private final String state;
}
