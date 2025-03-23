package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.category.CategoryRequest;
import com.kuponburada.KuponBurada.dto.response.category.CategoryBrandDTO;
import com.kuponburada.KuponBurada.dto.response.category.CategoryDTO;
import com.kuponburada.KuponBurada.dto.response.category.PopularCategoryDTO;
import com.kuponburada.KuponBurada.dto.response.coupon.CategoryCouponsDTO;
import com.kuponburada.KuponBurada.dto.response.coupon.CouponDTO;
import com.kuponburada.KuponBurada.entity.Brand;
import com.kuponburada.KuponBurada.entity.Category;
import com.kuponburada.KuponBurada.repository.BrandRepository;
import com.kuponburada.KuponBurada.repository.CategoryRepository;
import com.kuponburada.KuponBurada.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> {
                    CategoryDTO dto = modelMapper.map(category, CategoryDTO.class);
                    dto.setCouponCount(category.getCoupons().size());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        else{
            CategoryDTO dto = modelMapper.map(category.get(), CategoryDTO.class);
            dto.setCouponCount(category.get().getCoupons().size());
            return dto;
        }
    }

    @Override
    public CategoryDTO createCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryBrandDTO> addBrandToCategory(Long categoryId, Long brandId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found with id: " + brandId));

        if (category.getBrands().contains(brand)) {
            return mapToCategoryBrandDTOList(category.getBrands());
        }

        category.getBrands().add(brand);
        brand.getCategories().add(category);

        categoryRepository.save(category);

        return mapToCategoryBrandDTOList(category.getBrands());
    }

    @Override
    public List<CategoryBrandDTO> getCategoryBrands(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return category.getBrands().stream().map(
                brand -> modelMapper.map(brand, CategoryBrandDTO.class)
        ).collect(Collectors.toList()
        );
    }

    @Override
    public List<CategoryCouponsDTO> getCategoryCoupons(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return category.getCoupons().stream().map(
                coupon -> {
                    CategoryCouponsDTO dto = modelMapper.map(coupon, CategoryCouponsDTO.class);
                    dto.setBrandName(coupon.getBrand().getName());
                    dto.setBrandId(coupon.getBrand().getId());
                    dto.setLogoUrl(coupon.getBrand().getLogoUrl());
                    return dto;
                }
        ).collect(Collectors.toList()
        );
    }

    @Override
    public List<PopularCategoryDTO> getPopularCategories() {
        Pageable pageable = PageRequest.of(0, 4);

        List<Category> categories = categoryRepository.findPopularCategories(pageable);
        return categories.stream()
                .map(category -> modelMapper.map(category, PopularCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private List<CategoryBrandDTO> mapToCategoryBrandDTOList(Set<Brand> brands) {
        return brands.stream()
                .map(brand -> modelMapper.map(brand, CategoryBrandDTO.class))
                .collect(Collectors.toList());
    }
}
