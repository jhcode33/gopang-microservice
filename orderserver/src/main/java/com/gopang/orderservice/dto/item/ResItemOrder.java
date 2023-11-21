package com.gopang.orderservice.dto.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResItemOrder {

    private Long itemId;
    private String itemName;
    private Long itemPrice;
    // 주문 수량
    private Long amount;

    @Builder
    public ResItemOrder(Long itemId, String itemName, Long itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "ResItemOrder{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
