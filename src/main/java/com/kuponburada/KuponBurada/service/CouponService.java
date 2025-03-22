package com.kuponburada.KuponBurada.service;

import com.kuponburada.KuponBurada.dto.request.coupon.CouponRequest;
import com.kuponburada.KuponBurada.dto.response.coupon.CouponDTO;
import com.kuponburada.KuponBurada.dto.response.coupon.LastAddedCouponDTO;

import java.util.List;

public interface CouponService {
    List<CouponDTO> getAllCoupons();
    CouponDTO getCouponById(Long id);
    List<LastAddedCouponDTO> getLastAddedCoupons();
    CouponDTO createCoupon(CouponRequest couponRequest);
    CouponDTO updateCoupon(Long id, CouponRequest couponRequest);
    void deleteCoupon(Long id);
}
