package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.BrandRequest;
import com.kuponburada.KuponBurada.dto.response.brand.BrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.FollowedBrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.PopularBrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.RelatedBrandDTO;
import com.kuponburada.KuponBurada.entity.Brand;
import com.kuponburada.KuponBurada.entity.Category;
import com.kuponburada.KuponBurada.entity.User;
import com.kuponburada.KuponBurada.entity.UserBrandFollow;
import com.kuponburada.KuponBurada.repository.BrandRepository;
import com.kuponburada.KuponBurada.repository.UserBrandFollowRepository;
import com.kuponburada.KuponBurada.repository.UserRepository;
import com.kuponburada.KuponBurada.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBrandFollowRepository userBrandFollowRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
        return modelMapper.map(brand, BrandDTO.class);
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brand ->{
                            BrandDTO dto = modelMapper.map(brand, BrandDTO.class);
                            dto.setFollowerCount(brand.getFollowers().size());
                            return dto;
                        }
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<PopularBrandDTO> getPopularBrands() {
        Pageable pageable = PageRequest.of(0, 4);
        List<Brand> popularBrands = brandRepository.findPopularBrands(pageable);
        return popularBrands.stream()
                .map(brand -> {
                    PopularBrandDTO dto = modelMapper.map(brand, PopularBrandDTO.class);
                    dto.setCouponCount(brand.getCoupons().size());
                    return dto;
                }
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<RelatedBrandDTO> getRelatedBrands(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));

        Set<Category> categories = brand.getCategories();

        Pageable pageable = PageRequest.of(0, 10);

        List<Brand> relatedBrands = brandRepository.findRelatedBrands(categories, id, pageable);

        return relatedBrands.stream()
                .map(brand1 -> modelMapper.map(brand1, RelatedBrandDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<FollowedBrandDTO> getFollowedBrands(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with userId: " + userId));

        return user.getFollowedBrands().stream()
                .map(userBrandFollow -> {
                    FollowedBrandDTO dto = modelMapper.map(userBrandFollow.getBrand(), FollowedBrandDTO.class);
                    dto.setFollowedAt(userBrandFollow.getFollowedAt().toString());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void followBrand(Long userId, Long id) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with userId: " + userId));

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));


        boolean alreadyFollowing = user.getFollowedBrands().stream()
                .anyMatch(ubf -> ubf.getBrand().equals(brand));

        if (!alreadyFollowing) {
            UserBrandFollow follow = UserBrandFollow.builder()
                    .user(user)
                    .brand(brand)
                    .build();

            user.getFollowedBrands().add(follow);
            userBrandFollowRepository.save(follow);
        }
    }

    @Override
    public void unfollowBrand(Long userId, Long id) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with userId: " + userId));

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));

        UserBrandFollow follow = user.getFollowedBrands().stream()
                .filter(ubf -> ubf.getBrand().equals(brand))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User is not following the brand with id: " + id));

        user.getFollowedBrands().remove(follow);
        userBrandFollowRepository.delete(follow);
    }


    @Override
    public BrandDTO createBrand(BrandRequest brandRequest) {
        Brand brand = modelMapper.map(brandRequest, Brand.class);
        Brand savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand, BrandDTO.class);
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandRequest brandRequest) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));

        // Update the existing brand with new values
        modelMapper.map(brandRequest, existingBrand);

        Brand updatedBrand = brandRepository.save(existingBrand);
        return modelMapper.map(updatedBrand, BrandDTO.class);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new EntityNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }
}
