package com.gopang.itemserver.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryUpdateDto {

    private Long categoryId;

    @NotBlank(message = "카테고리 이름은 Null 값을 가질 수 없습니다.")
    private String name;

    private Long parentId;
    private Long depth;
}
