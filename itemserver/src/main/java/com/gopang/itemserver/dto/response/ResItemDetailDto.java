package com.gopang.itemserver.dto.response;

import com.gopang.itemserver.entity.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Response
 * 판매자 상품 상세 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ResItemDetailDto {

    private ResItem item;

    @Builder
    public ResItemDetailDto(ResItem item) {
        this.item = item;
    }

    public static ResItemDetailDto fromEntity(Item item) {
        return ResItemDetailDto.builder()
                .build();
    }
}
