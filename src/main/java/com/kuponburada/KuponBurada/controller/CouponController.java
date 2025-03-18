package com.kuponburada.KuponBurada.controller;

import com.kuponburada.KuponBurada.dto.request.CouponRequest;
import com.kuponburada.KuponBurada.dto.response.coupon.CouponDTO;
import com.kuponburada.KuponBurada.entity.Coupon;
import com.kuponburada.KuponBurada.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping
    public ResponseEntity<List<CouponDTO>> getAllCoupons() {
        return ResponseEntity.ok(couponService.getAllCoupons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponDTO> getCouponById(@PathVariable Long id) {
        return ResponseEntity.ok(couponService.getCouponById(id));
    }

    @GetMapping("/last-added")
    public ResponseEntity<List<CouponDTO>> getLastAddedCoupons() {
        return ResponseEntity.ok(couponService.getLastAddedCoupons());
    }

    @PostMapping
    public ResponseEntity<CouponDTO> createCoupon(@RequestBody CouponRequest couponRequest) {
        return ResponseEntity.ok(couponService.createCoupon(couponRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponDTO> updateCoupon(@PathVariable Long id, @RequestBody CouponRequest couponRequest) {
        return ResponseEntity.ok(couponService.updateCoupon(id, couponRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok().build();
    }


}
