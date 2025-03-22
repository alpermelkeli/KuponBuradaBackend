package com.kuponburada.KuponBurada.dto.request.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    private String name;
    private String description;
    private String logoUrl;
    private String websiteUrl;
    private boolean active;
}