package com.kuponburada.KuponBurada.service;

import com.kuponburada.KuponBurada.dto.request.brand.BrandRequest;
import com.kuponburada.KuponBurada.dto.response.brand.BrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.FollowedBrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.PopularBrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.RelatedBrandDTO;

import java.util.List;

public interface BrandService {
    BrandDTO getBrandById(Long id);
    List<BrandDTO> getAllBrands();
    List<PopularBrandDTO> getPopularBrands();
    List<RelatedBrandDTO> getRelatedBrands(Long id);
    List<FollowedBrandDTO> getFollowedBrands(Long userId);
    void followBrand(Long userId, Long id);
    void unfollowBrand(Long userId, Long id);
    BrandDTO createBrand(BrandRequest brandRequest);
    BrandDTO updateBrand(Long id, BrandRequest brandRequest);
    void deleteBrand(Long id);
}
