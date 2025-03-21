package com.kuponburada.KuponBurada.dto.response.brand;

import com.kuponburada.KuponBurada.dto.response.coupon.CouponDTO;
import com.kuponburada.KuponBurada.entity.Coupon;
import com.kuponburada.KuponBurada.entity.Faq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    private Long id;
    private String name;
    private String description;
    private List<String> categoryNames;
    private boolean isPopular;
    private String logoUrl;
    private String websiteUrl;
    private List<CouponDTO> coupons;
    private Integer followerCount;
    private List<Faq> faqs;
}

