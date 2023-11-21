package com.gopang.itemserver.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResItem {

    private Long itemId;
    private String itemTitleName;
    private String itemOptionName;
    private String imageURL;
    private Long sellCost;
    private Long sellAmount;

    @Builder
    public ResItem(Long itemId, String itemTitleName, String itemOptionName, String imageURL, Long sellCost, Long sellAmount) {
        this.itemId = itemId;
        this.itemTitleName = itemTitleName;
        this.itemOptionName = itemOptionName;
        this.imageURL = imageURL;
        this.sellCost = sellCost;
        this.sellAmount = sellAmount;
    }
}
