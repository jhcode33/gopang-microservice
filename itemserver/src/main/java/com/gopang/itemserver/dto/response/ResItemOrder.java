package com.gopang.itemserver.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * -Response
 * 주문에서 Item 정보 요청
 * @author 김민규
 * @response itemId, itemName, itemPrice
 */
public class ResItemOrder {

    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("itemPrice")
    private Long itemPrice;

    @Builder
    public ResItemOrder(Long itemId, String itemName, Long itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
}
