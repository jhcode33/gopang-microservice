package com.gopang.itemserver.dto.request.item.save;

import com.gopang.itemserver.entity.ItemOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request
 * 판매자 상품 옵션 등록 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemOptionSaveDto {

    private String itemModelName;
    private String sellerItemNumber;
    private String optionName;
    private Long sellAmount;
    private Long normalCost;
    private Long discountCost;
    private Long sellCost;
    private Long inventoryAmount;

    @Builder
    public ItemOptionSaveDto(String itemModelName, String sellerItemNumber, String optionName, Long sellAmount, Long normalCost, Long discountCost, Long sellCost, Long inventoryAmount) {
        this.itemModelName = itemModelName;
        this.sellerItemNumber = sellerItemNumber;
        this.optionName = optionName;
        this.sellAmount = sellAmount;
        this.normalCost = normalCost;
        this.discountCost = discountCost;
        this.sellCost = sellCost;
        this.inventoryAmount = inventoryAmount;
    }

    public static ItemOption ofEntity(ItemOptionSaveDto dto) {
        return ItemOption.builder()
                .itemModelName(dto.itemModelName)
                .sellerItemNumber(dto.sellerItemNumber)
                .optionName(dto.optionName)
                .sellAmount(dto.sellAmount)
                .normalCost(dto.normalCost)
                .sellCost(dto.sellCost)
                .inventoryAmount(dto.inventoryAmount)
                .build();
    }
}
