package com.kuponburada.KuponBurada.service;

import com.kuponburada.KuponBurada.dto.request.CouponRequest;
import com.kuponburada.KuponBurada.dto.response.coupon.CouponDTO;

import java.util.List;

public interface CouponService {
    List<CouponDTO> getAllCoupons();
    CouponDTO getCouponById(Long id);
    List<CouponDTO> getLastAddedCoupons();
    CouponDTO createCoupon(CouponRequest couponRequest);
    CouponDTO updateCoupon(Long id, CouponRequest couponRequest);
    void deleteCoupon(Long id);
}
