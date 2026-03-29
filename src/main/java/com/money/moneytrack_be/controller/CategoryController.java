package com.money.moneytrack_be.controller;

import com.money.moneytrack_be.dto.response.CategoryResponse;
import com.money.moneytrack_be.enums.CategoryType;
import com.money.moneytrack_be.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
            @RequestParam(required = false) CategoryType type) {
        return ResponseEntity.ok(categoryService.getCategories(type));
    }
}
