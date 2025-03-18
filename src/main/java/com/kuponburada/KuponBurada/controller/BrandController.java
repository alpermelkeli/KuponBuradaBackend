package com.kuponburada.KuponBurada.controller;

import com.kuponburada.KuponBurada.dto.response.brand.BrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.PopularBrandDTO;
import com.kuponburada.KuponBurada.dto.response.brand.RelatedBrandDTO;
import com.kuponburada.KuponBurada.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuponburada.KuponBurada.dto.request.BrandRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/popular")
        public ResponseEntity<List<PopularBrandDTO>> getPopularBrands() {
        return ResponseEntity.ok(brandService.getPopularBrands());
    }

    @GetMapping("/related-brands/{id}")
    public ResponseEntity<List<RelatedBrandDTO>> getRelatedBrands(@PathVariable Long id){
        return ResponseEntity.ok(brandService.getRelatedBrands(id));
    }

    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandRequest brandRequest) {
        return new ResponseEntity<>(brandService.createBrand(brandRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id, @RequestBody BrandRequest brandRequest) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}

