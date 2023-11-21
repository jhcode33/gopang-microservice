package com.gopang.itemserver.dto.request.item.update;

import com.gopang.itemserver.entity.ItemOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request
 * 판매자 상품 옵션 수정 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemOptionUpdateDto {

    private Long itemOptionId;
    private String itemModelName;
    private String sellerItemNumber;
    private String optionName;
    private Long sellAmount;
    private Long normalCost;
    private Long discountCost;
    private Long sellCost;
    private Long inventoryAmount;
}
