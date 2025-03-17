package com.kuponburada.KuponBurada.service;

import com.kuponburada.KuponBurada.dto.request.BrandRequest;
import com.kuponburada.KuponBurada.dto.response.BrandDTO;

import java.util.List;

public interface BrandService {
    BrandDTO getBrandById(Long id);
    List<BrandDTO> getAllBrands();
    BrandDTO createBrand(BrandRequest brandRequest);
    BrandDTO updateBrand(Long id, BrandRequest brandRequest);
    void deleteBrand(Long id);
}
