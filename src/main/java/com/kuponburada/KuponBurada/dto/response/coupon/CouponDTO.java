package com.kuponburada.KuponBurada.dto.response.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private Long id;
    private String title;
    private String description;
    private String code;
    private BigDecimal discountValue;
    private String discountType;
    private BigDecimal minimumPurchase;
    private String startDate;
    private String endDate;
    private String termsConditions;
}
