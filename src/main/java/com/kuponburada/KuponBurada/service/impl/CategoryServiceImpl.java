package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.CategoryRequest;
import com.kuponburada.KuponBurada.dto.response.category.CategoryDTO;
import com.kuponburada.KuponBurada.entity.Category;
import com.kuponburada.KuponBurada.repository.CategoryRepository;
import com.kuponburada.KuponBurada.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> {
                    return modelMapper.map(category, CategoryDTO.class);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO createCategory(CategoryRequest categoryRequest) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
