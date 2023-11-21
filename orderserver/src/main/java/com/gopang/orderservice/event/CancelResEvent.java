package com.gopang.orderservice.event;

import com.gopang.orderservice.message.payment.CancelPayOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelResEvent {
    private String message;

    private CancelPayOrder cancelPayOrder;

    @Override
    public String toString() {
        return "CancelResEvent{" +
                "message='" + message + '\'' +
                ", cancelPayOrder=" + cancelPayOrder +
                '}';
    }
}
