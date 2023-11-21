package com.gopang.itemserver.dto.response;

import com.gopang.itemserver.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * -Response
 * 카테고리 목록 정보
 */
@Getter
@Setter
@NoArgsConstructor
public class ResCategoryListDto {

    private Long categoryId;
    private String name;
    private Long depth;
    private List<ResCategoryListDto> children;

    @Builder
    public ResCategoryListDto(Long categoryId, String name, Long depth, List<ResCategoryListDto> children) {
        this.categoryId = categoryId;
        this.name = name;
        this.depth = depth;
        this.children = children;
    }

    public static ResCategoryListDto fromEntity(Category category) {
        return ResCategoryListDto.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .depth(category.getDepth())
                .children(category.getChild().stream()
                        .map(ResCategoryListDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
