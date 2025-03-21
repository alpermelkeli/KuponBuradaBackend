package com.kuponburada.KuponBurada.controller;

import com.kuponburada.KuponBurada.dto.request.CategoryRequest;
import com.kuponburada.KuponBurada.dto.response.category.CategoryBrandDTO;
import com.kuponburada.KuponBurada.dto.response.category.CategoryDTO;
import com.kuponburada.KuponBurada.dto.response.category.PopularCategoryDTO;
import com.kuponburada.KuponBurada.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/{id}/brands")
    public ResponseEntity<List<CategoryBrandDTO>> getCategoryBrands(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryBrands(id));
    }
    @GetMapping("/popular")
    public ResponseEntity<List<PopularCategoryDTO>> getPopularCategories() {
        return ResponseEntity.ok(categoryService.getPopularCategories());
    }

    @PutMapping("/add-brand/{categoryId}/{brandId}")
    public ResponseEntity<List<CategoryBrandDTO>> addBrandToCategory(@PathVariable Long categoryId, @PathVariable Long brandId) {
        return ResponseEntity.ok(categoryService.addBrandToCategory(categoryId, brandId));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
