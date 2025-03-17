package com.kuponburada.KuponBurada.dto.response;

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
    private boolean isPopular;
    private String logoUrl;
    private String websiteUrl;
    private List<Coupon> coupons;
    private List<Faq> faqs;
}

