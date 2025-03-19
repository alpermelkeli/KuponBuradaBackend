package com.kuponburada.KuponBurada.service;

import com.kuponburada.KuponBurada.dto.request.CategoryRequest;
import com.kuponburada.KuponBurada.dto.response.category.CategoryDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
    CategoryDTO createCategory(CategoryRequest categoryRequest);
    void deleteCategory(Long id);

}
