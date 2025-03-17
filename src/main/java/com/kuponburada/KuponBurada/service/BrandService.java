package com.kuponburada.KuponBurada.service;

import com.kuponburada.KuponBurada.dto.request.BrandRequest;
import com.kuponburada.KuponBurada.dto.response.BrandDTO;
import com.kuponburada.KuponBurada.entity.Brand;

import java.util.List;

public interface BrandService {
    BrandDTO getBrandById(Long id);
    List<BrandDTO> getAllBrands();
    List<BrandDTO> getPopularBrands();
    BrandDTO createBrand(BrandRequest brandRequest);
    BrandDTO updateBrand(Long id, BrandRequest brandRequest);
    void deleteBrand(Long id);
}
