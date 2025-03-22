package com.kuponburada.KuponBurada.service;

import com.kuponburada.KuponBurada.dto.request.category.CategoryRequest;
import com.kuponburada.KuponBurada.dto.response.category.CategoryBrandDTO;
import com.kuponburada.KuponBurada.dto.response.category.CategoryDTO;
import com.kuponburada.KuponBurada.dto.response.category.PopularCategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO createCategory(CategoryRequest categoryRequest);
    List<CategoryBrandDTO> addBrandToCategory(Long categoryId, Long brandId);
    List<CategoryBrandDTO> getCategoryBrands(Long id);
    List<PopularCategoryDTO> getPopularCategories();
    void deleteCategory(Long id);
}
