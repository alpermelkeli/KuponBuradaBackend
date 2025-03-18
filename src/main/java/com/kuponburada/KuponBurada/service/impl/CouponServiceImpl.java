package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.CouponRequest;
import com.kuponburada.KuponBurada.dto.response.brand.BrandDTO;
import com.kuponburada.KuponBurada.dto.response.coupon.CouponDTO;
import com.kuponburada.KuponBurada.entity.Brand;
import com.kuponburada.KuponBurada.entity.Coupon;
import com.kuponburada.KuponBurada.repository.BrandRepository;
import com.kuponburada.KuponBurada.repository.CouponRepository;
import com.kuponburada.KuponBurada.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return List.of();
    }

    @Override
    public CouponDTO getCouponById(Long id) {
        return null;
    }

    @Override
    public List<CouponDTO> getLastAddedCoupons() {
        return List.of();
    }

    @Override
    public CouponDTO createCoupon(CouponRequest couponRequest) {
        try {
            Coupon coupon = new Coupon();
            modelMapper.map(couponRequest, coupon);

            if(couponRequest.getBrandId() != null) {
                Brand brand = brandRepository.findById(couponRequest.getBrandId())
                        .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + couponRequest.getBrandId()));
                coupon.setBrand(brand);
            }
            Coupon savedCoupon = couponRepository.save(coupon);

            return modelMapper.map(savedCoupon, CouponDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create coupon", e);
        }
    }

    @Override
    public CouponDTO updateCoupon(Long id, CouponRequest couponRequest) {
        return null;
    }

    @Override
    public void deleteCoupon(Long id) {

    }
}
