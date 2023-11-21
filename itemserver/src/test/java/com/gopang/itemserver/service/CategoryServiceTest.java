package com.gopang.itemserver.service;

import com.gopang.itemserver.dto.request.category.CategorySaveDto;
import com.gopang.itemserver.dto.response.ResCategoryDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    CategorySaveDto rootCategory;
    CategorySaveDto childCategory1;
    CategorySaveDto childCategory2;
    CategorySaveDto grandchildCategory1;
    CategorySaveDto grandchildCategory2;
    CategorySaveDto grandchildCategory3;

    @BeforeEach
    public void setUp() {
        categoryDataSet();
    }

    @Test
    public void saveCategory() {
        // when

        // given
        ResCategoryDto rootCategoryDto = categoryService.save(rootCategory);
        ResCategoryDto childCategoryDto1 = categoryService.save(childCategory1);
        ResCategoryDto childCategoryDto2 = categoryService.save(childCategory2);
        ResCategoryDto grandchildCategoryDto1 = categoryService.save(grandchildCategory1);
        ResCategoryDto grandchildCategoryDto2 = categoryService.save(grandchildCategory2);
        ResCategoryDto grandchildCategoryDto3 = categoryService.save(grandchildCategory3);

        // then
        assertEquals(rootCategoryDto.getName(), rootCategory.getName());
        assertEquals(childCategoryDto1.getName(), childCategory1.getName());
        assertEquals(childCategoryDto2.getName(), childCategory2.getName());
        assertEquals(grandchildCategoryDto1.getName(), grandchildCategory1.getName());
        assertEquals(grandchildCategoryDto2.getName(), grandchildCategory2.getName());
        assertEquals(grandchildCategoryDto3.getName(), grandchildCategory3.getName());
    }

    private void categoryDataSet() {
        rootCategory = CategorySaveDto.builder()
                        .name("Root Category")
                        .build();

        childCategory1 = CategorySaveDto.builder()
                        .name("Child Category 1")
                        .parentId(1L)
                        .build();

        childCategory2 = CategorySaveDto.builder()
                .name("Child Category 2")
                .parentId(1L)
                .build();

        grandchildCategory1 = CategorySaveDto.builder()
                .name("Grandchild Category 1")
                .parentId(2L)
                .build();

        grandchildCategory2 = CategorySaveDto.builder()
                .name("Grandchild Category 2")
                .parentId(2L)
                .build();

        grandchildCategory3 = CategorySaveDto.builder()
                .name("Grandchild Category 3")
                .parentId(2L)
                .build();
    }

}
