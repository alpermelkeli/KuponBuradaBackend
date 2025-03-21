package com.kuponburada.KuponBurada.repository;

import com.kuponburada.KuponBurada.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.isPopular = true")
    List<Category> findPopularCategories(Pageable pageable);
}