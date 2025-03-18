package com.kuponburada.KuponBurada.dto.response.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelatedBrandDTO {
    private Long id;
    private String name;
    private String logoUrl;
}
