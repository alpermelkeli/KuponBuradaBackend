package com.kuponburada.KuponBurada.dto.response.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopularCategoryDTO {
    private Long id;
    private String name;
    private String imageUrl;
}
