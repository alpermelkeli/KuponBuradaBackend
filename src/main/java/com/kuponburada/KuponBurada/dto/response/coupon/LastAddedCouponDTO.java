package com.kuponburada.KuponBurada.dto.response.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastAddedCouponDTO {
    private Long id;
    private String title;
    private String code;
    private String category;
    private String brandLogoUrl;
    private String endDate;
}
