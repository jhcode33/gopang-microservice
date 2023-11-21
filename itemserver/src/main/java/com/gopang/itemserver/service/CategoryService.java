package com.gopang.itemserver.service;

import com.gopang.itemserver.dto.request.category.CategorySaveDto;
import com.gopang.itemserver.dto.request.category.CategoryUpdateDto;
import com.gopang.itemserver.dto.response.ResCategoryDto;
import com.gopang.itemserver.dto.response.ResCategoryListDto;
import com.gopang.itemserver.entity.Category;
import com.gopang.itemserver.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<ResCategoryListDto> findAll() {
        List<ResCategoryListDto> list = categoryRepository.findListAll().stream()
                .map(ResCategoryListDto::fromEntity)
                .collect(Collectors.toList());
        return list;
    }

    public ResCategoryDto save(CategorySaveDto saveDto) {
        Category parent = getParentCategory(saveDto.getParentId());
        Long depth = (parent != null) ? parent.getDepth() + 1 : 0L;

        saveDto.setDepth(depth);
        Category category = CategorySaveDto.ofEntity(saveDto);
        Long parentId = (parent != null) ? category.setParent(parent) : 0;

        Category saveCategory = categoryRepository.save(category);
        return ResCategoryDto.fromEntity(saveCategory, parentId);
    }

    public ResCategoryDto update(CategoryUpdateDto updateDto) {
        Category parent = getParentCategory(updateDto.getParentId());
        Category updateCategory = categoryRepository.findById(updateDto.getCategoryId()).orElseThrow(
                () -> new IllegalArgumentException("Category not found.")
        );
        updateCategory.update(updateDto.getName(), updateDto.getDepth());
        Long parentId = (parent != null) ? updateCategory.setParent(parent) : 0;
        return ResCategoryDto.fromEntity(updateCategory, parentId);
    }

    public void delete(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    // 부모 카테고리 찾기
    private Category getParentCategory(Long parentId) {
        if (parentId != null && parentId != 0) {
            return categoryRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found."));
        }
        return null;
    }
}
