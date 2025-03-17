package com.kuponburada.KuponBurada.repository;

import com.kuponburada.KuponBurada.dto.response.BrandDTO;
import com.kuponburada.KuponBurada.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    // You can add custom query methods here if needed
    List<Brand> findByIsPopularTrue();
}
