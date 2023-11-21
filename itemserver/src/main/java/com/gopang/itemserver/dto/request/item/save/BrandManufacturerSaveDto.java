package com.gopang.itemserver.dto.request.item.save;


import com.gopang.itemserver.entity.BrandManufacturer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request
 * 브랜드 제조사 등록 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class BrandManufacturerSaveDto {

    private String brandName;
    private String manufacturerName;

    @Builder
    public BrandManufacturerSaveDto(String brandName, String manufacturerName) {
        this.brandName = brandName;
        this.manufacturerName = manufacturerName;
    }

    public static BrandManufacturer ofEntity(BrandManufacturerSaveDto dto) {
        return BrandManufacturer.builder()
                .brandName(dto.brandName)
                .manufacturerName(dto.manufacturerName)
                .build();
    }
}
