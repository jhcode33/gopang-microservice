package com.gopang.itemserver.dto.response;

import com.gopang.itemserver.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * -Response
 * 카테고리 등록 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ResCategoryDto {

    private Long categoryId;
    private String name;
    private Long parentId;
    private Long depth;

    @Builder
    public ResCategoryDto(Long categoryId, String name, Long parentId, Long depth) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
    }

    public static ResCategoryDto fromEntity(Category category, Long parentId) {
        return ResCategoryDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .parentId(parentId)
                .depth(category.getDepth())
                .build();
    }
}
