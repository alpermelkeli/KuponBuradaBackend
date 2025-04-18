package com.kuponburada.KuponBurada.dto.response.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    private Long id;
    private String title;
    private String description;
    private String code;
    private String discountType;
    private String startDate;
    private String endDate;
    private String termsConditions;
}
