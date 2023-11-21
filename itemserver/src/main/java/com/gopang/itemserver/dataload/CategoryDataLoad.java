package com.gopang.itemserver.dataload;

import com.gopang.itemserver.dto.request.category.CategorySaveDto;
import com.gopang.itemserver.repository.CategoryRepository;
import com.gopang.itemserver.service.CategoryService;
import com.gopang.itemserver.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
@RequiredArgsConstructor
public class CategoryDataLoad implements CommandLineRunner {

    private final CategoryService categoryService;

    private CategorySaveDto rootCategory;
    private CategorySaveDto childCategory1;
    private CategorySaveDto childCategory2;
    private CategorySaveDto grandchildCategory1;
    private CategorySaveDto grandchildCategory2;
    private CategorySaveDto grandchildCategory3;

    @Override
    public void run(String... args) throws Exception {
        categoryDataSet();
        saveCategory();
    }

    private void saveCategory() {
        categoryService.save(rootCategory);
        categoryService.save(childCategory1);
        categoryService.save(childCategory2);
        categoryService.save(grandchildCategory1);
        categoryService.save(grandchildCategory2);
        categoryService.save(grandchildCategory3);
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
