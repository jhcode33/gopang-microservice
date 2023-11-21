package com.gopang.itemserver.dto.request.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request
 * 판매자 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class SellerInfo {

    Long sellerId;
    Long sellerDeliveryId;
    Long sellerREId;

    @Builder
    public SellerInfo(Long sellerId, Long sellerDeliveryId, Long sellerREId) {
        this.sellerId = sellerId;
        this.sellerDeliveryId = sellerDeliveryId;
        this.sellerREId = sellerREId;
    }
}
