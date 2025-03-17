package com.kuponburada.KuponBurada.repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.kuponburada.KuponBurada.dto.response.BrandDTO;
import com.kuponburada.KuponBurada.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT b FROM Brand b WHERE b.isPopular = true")
    List<Brand> findPopularBrands(Pageable pageable);
}
