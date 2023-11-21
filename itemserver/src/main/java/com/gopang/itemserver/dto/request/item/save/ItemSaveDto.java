package com.gopang.itemserver.dto.request.item.save;

import com.gopang.itemserver.entity.Item;
import com.gopang.itemserver.entity.Item.SellState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * -Request
 * 판매자 상품 등록 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemSaveDto {

    // 판매자 관련 정보
    private Long sellerId;
    private Long sellerDeliveryId;
    private Long sellerREId;

    private Long itemId;
    private String titleName;
    private String itemLabel;
    private String sellState;
    private LocalDate sellStartDate;
    private LocalDate sellEndDate;

    @Builder
    public ItemSaveDto(Long sellerId, Long sellerDeliveryId, Long sellerREId, String titleName, String itemLabel, String sellState, LocalDate sellStartDate, LocalDate sellEndDate) {
        this.sellerId = sellerId;
        this.sellerDeliveryId = sellerDeliveryId;
        this.sellerREId = sellerREId;
        this.titleName = titleName;
        this.itemLabel = itemLabel;
        this.sellState = sellState;
        this.sellStartDate = sellStartDate;
        this.sellEndDate = sellEndDate;
    }

    public static Item ofEntity(ItemSaveDto dto) {
        return Item.builder()
                .sellerId(dto.sellerId)
                .sellerDeliveryId(dto.sellerDeliveryId)
                .sellerREId(dto.sellerREId)
                .titleName(dto.titleName)
                .itemLabel(dto.itemLabel)
                .state(SellState.valueOf(dto.sellState))
                .sellStartDate(dto.sellStartDate)
                .sellEndDate(dto.sellEndDate)
                .build();
    }

    public void setSellerInfo(Long sellerId, Long sellerDeliveryId, Long sellerREId) {
        this.sellerId = sellerId;
        this.sellerDeliveryId = sellerDeliveryId;
        this.sellerREId = sellerREId;
    }
}
