package com.money.moneytrack_be.service;

import com.money.moneytrack_be.dto.response.CategoryResponse;
import com.money.moneytrack_be.entity.Category;
import com.money.moneytrack_be.enums.CategoryType;
import com.money.moneytrack_be.enums.DeleteFlag;
import com.money.moneytrack_be.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategories(CategoryType type) {
        List<Category> flat = (type != null)
                ? categoryRepository.findByTypeAndDeleteFlag(type, DeleteFlag.ACTIVE)
                : categoryRepository.findByDeleteFlag(DeleteFlag.ACTIVE);

        // Separate parents (no parent reference) from children
        List<Category> parents = flat.stream()
                .filter(c -> c.getParent() == null)
                .collect(Collectors.toList());

        return parents.stream()
                .map(parent -> toResponse(parent, flat))
                .collect(Collectors.toList());
    }

    private CategoryResponse toResponse(Category parent, List<Category> all) {
        List<CategoryResponse> children = all.stream()
                .filter(c -> c.getParent() != null && c.getParent().getId().equals(parent.getId()))
                .map(child -> CategoryResponse.builder()
                        .id(child.getId())
                        .name(child.getName())
                        .type(child.getType())
                        .children(List.of())
                        .build())
                .collect(Collectors.toList());

        return CategoryResponse.builder()
                .id(parent.getId())
                .name(parent.getName())
                .type(parent.getType())
                .children(children)
                .build();
    }
}
