package com.gopang.itemserver.dto.request.item.save;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * -Request
 * 판매자 상품 등록 정보들
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemSaveRequest {
    private Long categoryId;
    private ItemSaveDto itemSaveDto;
    private ItemDetailSaveDto itemDetailSaveDto;
    private List<ItemOptionSaveDto> itemOptionSaveDtoList;
    private BrandManufacturerSaveDto brandManufacturerSaveDto;
}
