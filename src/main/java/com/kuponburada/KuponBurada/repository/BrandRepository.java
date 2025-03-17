package com.kuponburada.KuponBurada.repository;

import com.kuponburada.KuponBurada.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    // You can add custom query methods here if needed
}
