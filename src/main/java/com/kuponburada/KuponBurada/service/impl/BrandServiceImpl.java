package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.BrandRequest;
import com.kuponburada.KuponBurada.dto.response.BrandDTO;
import com.kuponburada.KuponBurada.entity.Brand;
import com.kuponburada.KuponBurada.repository.BrandRepository;
import com.kuponburada.KuponBurada.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
        return modelMapper.map(brand, BrandDTO.class);
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> modelMapper.map(brand, BrandDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO createBrand(BrandRequest brandRequest) {
        System.out.println("Request: " + brandRequest.getName()); // Debug line

        Brand brand = modelMapper.map(brandRequest, Brand.class);
        System.out.println("Mapped Entity: " + brand.getName()); // Debug line

        Brand savedBrand = brandRepository.save(brand);
        return modelMapper.map(savedBrand, BrandDTO.class);
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandRequest brandRequest) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));

        // Update the existing brand with new values
        modelMapper.map(brandRequest, existingBrand);

        Brand updatedBrand = brandRepository.save(existingBrand);
        return modelMapper.map(updatedBrand, BrandDTO.class);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new EntityNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }
}
