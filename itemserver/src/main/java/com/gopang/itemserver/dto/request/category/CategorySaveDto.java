package com.gopang.itemserver.dto.request.category;

import com.gopang.itemserver.entity.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategorySaveDto {

    @NotBlank(message = "카테고리 이름은 Null 값을 가질 수 없습니다.")
    private String name;

    private Long parentId;
    private Long depth;

    @Builder
    public CategorySaveDto(String name, Long parentId, Long depth) {
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
    }

    public static Category ofEntity(CategorySaveDto dto) {
        return Category.builder()
                .name(dto.name)
                .depth(dto.depth)
                .build();
    }
}
