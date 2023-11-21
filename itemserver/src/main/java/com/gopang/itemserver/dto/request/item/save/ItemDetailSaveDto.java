package com.gopang.itemserver.dto.request.item.save;

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
 * 판매자 상품 상세 등록 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemDetailSaveDto {

    private String maxOrder;
    private String parallelType;
    private String minorType;
    private String taxType;
    private String maker;
    private String manufacturerItemName;
    private String warning;
    private String qualityAssurance;
    private String asInfo;

    @Builder
    public ItemDetailSaveDto(String maxOrder, String parallelType, String minorType, String taxType, String maker, String manufacturerItemName, String warning, String qualityAssurance, String asInfo) {
        this.maxOrder = maxOrder;
        this.parallelType = parallelType;
        this.minorType = minorType;
        this.taxType = taxType;
        this.maker = maker;
        this.manufacturerItemName = manufacturerItemName;
        this.warning = warning;
        this.qualityAssurance = qualityAssurance;
        this.asInfo = asInfo;
    }

    public static ItemDetail ofEntity(ItemDetailSaveDto dto) {
        return ItemDetail.builder()
                .maxOrder(dto.maxOrder)
                .parallelType(ParallelType.valueOf(dto.parallelType))
                .minorType(MinorType.valueOf(dto.minorType))
                .taxType(TaxType.valueOf(dto.taxType))
                .maker(dto.maker)
                .manufacturerItemName(dto.manufacturerItemName)
                .warning(dto.warning)
                .qualityAssurance(dto.qualityAssurance)
                .asInfo(dto.asInfo)
                .build();
    }
}
