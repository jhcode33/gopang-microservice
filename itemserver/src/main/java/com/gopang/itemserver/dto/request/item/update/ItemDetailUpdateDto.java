package com.gopang.itemserver.dto.request.item.update;

import com.gopang.itemserver.entity.ItemDetail;
import com.gopang.itemserver.entity.ItemDetail.MinorType;
import com.gopang.itemserver.entity.ItemDetail.ParallelType;
import com.gopang.itemserver.entity.ItemDetail.TaxType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request
 * 판매자 상품 상세 수정 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemDetailUpdateDto {

    private Long itemDetailId;
    private String maxOrder;
    private String parallelType;
    private String minorType;
    private String taxType;
    private String maker;
    private String manufacturerItemName;
    private String warning;
    private String qualityAssurance;
    private String asInfo;
}
