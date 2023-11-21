package com.gopang.itemserver.dto.request.item.update;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * -Request
 * 판매자 상품 수정 정보들
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemUpdateRequest {
    private Long categoryId;
    private ItemUpdateDto itemUpdateDto;
    private ItemDetailUpdateDto itemDetailUpdateDto;
    private List<ItemOptionUpdateDto> itemOptionUpdateDtoList;
    private BrandManufacturerUpdateDto brandManufacturerUpdateDto;
}
