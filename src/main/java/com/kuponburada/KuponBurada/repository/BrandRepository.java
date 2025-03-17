package com.kuponburada.KuponBurada.repository;
import com.kuponburada.KuponBurada.entity.Category;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.kuponburada.KuponBurada.dto.response.BrandDTO;
import com.kuponburada.KuponBurada.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query("SELECT b FROM Brand b WHERE b.isPopular = true")
    List<Brand> findPopularBrands(Pageable pageable);

    @Query("SELECT b FROM Brand b JOIN b.categories c WHERE c IN :categories AND b.id != :brandId " +
            "GROUP BY b.id ORDER BY COUNT(c) DESC")
    List<Brand> findRelatedBrands(@Param("categories") Set<Category> categories,
                                  @Param("brandId") Long brandId,
                                  Pageable pageable);
}
