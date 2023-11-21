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
 * 판매자 상품 등록 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ResItemSaveDto {

    private Long itemId;
    private String titleName;
    private String sellerName;
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
    public ResItemSaveDto(Long itemId, String titleName, String sellerName, String state, LocalDate sellStartDate, LocalDate sellEndDate, Long sellerId, Long sellerDeliveryId, Long sellerREId, Long itemDetailId, List<Long> itemOptionIds, Long brandManufacturerIds) {
        this.itemId = itemId;
        this.titleName = titleName;
        this.sellerName = sellerName;
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

    public static ResItemSaveDto fromEntity(Item item, Long itemDetailId, List<Long> itemOptionIds, Long brandManufacturerIds) {
        return ResItemSaveDto.builder()
                .itemId(item.getItemId())
                .titleName(item.getTitleName())
                .sellerName(item.getItemLabel())
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
