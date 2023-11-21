package com.gopang.orderservice.dto;

import com.gopang.orderservice.domain.OrderState;
import com.gopang.orderservice.domain.Orders;
import lombok.Builder;

public class Details {

    @Builder
    public static class HistoryResponse {
        public Long id;

        // 주문자 고유번호
        public Long user_id;

        // 주문 상품 고유번호
        public Long item_id;

        // 총 주문금액
        public Long order_amount;

        public Orders orders;

        // 주문상태
        public OrderState order_state;

        @Override
        public String toString() {
            return "HistoryResponse{" +
                    "id=" + id +
                    ", user_id=" + user_id +
                    ", item_id=" + item_id +
                    ", order_amount=" + order_amount +
                    ", orders=" + orders +
                    ", order_state=" + order_state +
                    '}';
        }
    }

}
