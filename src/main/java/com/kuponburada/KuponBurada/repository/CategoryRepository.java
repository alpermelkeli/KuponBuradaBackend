package com.kuponburada.KuponBurada.repository;

import com.kuponburada.KuponBurada.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
