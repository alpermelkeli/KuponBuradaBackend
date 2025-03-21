package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.CouponRequest;
import com.kuponburada.KuponBurada.dto.response.brand.BrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.PopularBrandDTO;
import com.kuponburada.KuponBurada.dto.response.coupon.CouponDTO;
import com.kuponburada.KuponBurada.dto.response.coupon.LastAddedCouponDTO;
import com.kuponburada.KuponBurada.entity.Brand;
import com.kuponburada.KuponBurada.entity.Category;
import com.kuponburada.KuponBurada.entity.Coupon;
import com.kuponburada.KuponBurada.repository.BrandRepository;
import com.kuponburada.KuponBurada.repository.CouponRepository;
import com.kuponburada.KuponBurada.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<CouponDTO> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
                .map(coupon -> {
                    return modelMapper.map(coupon, CouponDTO.class);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CouponDTO getCouponById(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with id: " + id));
        CouponDTO couponDTO = modelMapper.map(coupon, CouponDTO.class);
        System.out.println("CategoryNames: " + coupon.getCategories().stream().map(Category::getName).toList());
        return couponDTO;
    }

    @Override
    public List<LastAddedCouponDTO> getLastAddedCoupons() {
        Pageable pageable = PageRequest.of(0, 4);
        List<Coupon> lastAddedCoupons = couponRepository.findLastAddedCoupons(pageable);

        return lastAddedCoupons.stream()
                .map(coupon -> {
                    return modelMapper.map(coupon, LastAddedCouponDTO.class);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CouponDTO createCoupon(CouponRequest couponRequest) {
        Coupon coupon = new Coupon();
        modelMapper.map(couponRequest, coupon);

        if(couponRequest.getBrandId() != null) {
            Brand brand = brandRepository.findById(couponRequest.getBrandId())
                    .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + couponRequest.getBrandId()));
            coupon.setBrand(brand);

        }
        Coupon savedCoupon = couponRepository.save(coupon);

        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public CouponDTO updateCoupon(Long id, CouponRequest couponRequest) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with id: " + id));
        modelMapper.map(couponRequest, coupon);
        Coupon savedCoupon = couponRepository.save(coupon);
        return modelMapper.map(savedCoupon, CouponDTO.class);
    }

    @Override
    public void deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with id: " + id));
        couponRepository.delete(coupon);
    }
}
