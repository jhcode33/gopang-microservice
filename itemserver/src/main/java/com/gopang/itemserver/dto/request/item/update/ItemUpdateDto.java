package com.gopang.itemserver.dto.request.item.update;

import com.gopang.itemserver.entity.Item;
import com.gopang.itemserver.entity.Item.SellState;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * -Request
 * 판매자 상품 수정 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemUpdateDto {

    // 판매자 관련 정보
    private Long sellerId;
    private Long sellerDeliveryId;
    private Long sellerREId;

    // 상품 관련 정보
    private Long itemId;
    private String titleName;
    private String itemLabel;
    private String sellState;
    private LocalDate sellStartDate;
    private LocalDate sellEndDate;

    public void setSellerInfo(Long sellerId, Long sellerDeliveryId, Long sellerREId) {
        this.sellerId = sellerId;
        this.sellerDeliveryId = sellerDeliveryId;
        this.sellerREId = sellerREId;
    }
}
