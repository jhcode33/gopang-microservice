package com.gopang.itemserver.dto.request.item.update;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Request
 * 브랜드 제조사 수정 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class BrandManufacturerUpdateDto {

    private Long brandManufacturerId;

    @NotBlank(message = "브랜드 이름은 Null 값을 가질 수 없습니다.")
    private String brandName;

    @NotBlank(message = "제조사 이름은 Null 값을 가질 수 없습니다.")
    private String manufacturerName;
}
