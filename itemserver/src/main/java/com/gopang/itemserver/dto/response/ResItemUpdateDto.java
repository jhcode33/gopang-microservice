package com.gopang.itemserver.dto.response;

import com.gopang.itemserver.entity.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * -Response
 * 판매자 상품 수정 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ResItemUpdateDto {

    private Long itemId;
    private String titleName;
    private String itemLabel;
    private String state;

    private LocalDate sellStartDate;
    private LocalDate sellEndDate;

    private Long sellerId;
    private Long sellerDeliveryId;
    private Long sellerREId;

    private Long itemDetailId;
    private List<Long> itemOptionIds;
    private Long brandManufacturerIds;

    @Builder
    public ResItemUpdateDto(Long itemId, String titleName, String itemLabel, String state, LocalDate sellStartDate, LocalDate sellEndDate, Long sellerId, Long sellerDeliveryId, Long sellerREId, Long itemDetailId, List<Long> itemOptionIds, Long brandManufacturerIds) {
        this.itemId = itemId;
        this.titleName = titleName;
        this.itemLabel = itemLabel;
        this.state = state;
        this.sellStartDate = sellStartDate;
        this.sellEndDate = sellEndDate;
        this.sellerId = sellerId;
        this.sellerDeliveryId = sellerDeliveryId;
        this.sellerREId = sellerREId;
        this.itemDetailId = itemDetailId;
        this.itemOptionIds = itemOptionIds;
        this.brandManufacturerIds = brandManufacturerIds;
    }

    public static ResItemUpdateDto fromEntity(Item item, Long itemDetailId, List<Long> itemOptionIds, Long brandManufacturerIds) {
        return ResItemUpdateDto.builder()
                .itemId(item.getItemId())
                .titleName(item.getTitleName())
                .itemLabel(item.getItemLabel())
                .state(item.getState().toString())
                .sellStartDate(item.getSellStartDate())
                .sellEndDate(item.getSellEndDate())
                .sellerId(item.getSellerId())
                .sellerDeliveryId(item.getSellerDeliveryId())
                .sellerREId(item.getSellerREId())

                .itemDetailId(itemDetailId)
                .itemOptionIds(itemOptionIds)
                .brandManufacturerIds(brandManufacturerIds)
                .build();
    }
}
