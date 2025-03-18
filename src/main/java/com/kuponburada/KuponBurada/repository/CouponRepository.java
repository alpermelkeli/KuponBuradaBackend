package com.kuponburada.KuponBurada.repository;

import com.kuponburada.KuponBurada.entity.Coupon;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("SELECT c FROM Coupon c ORDER BY c.createdAt DESC")
    List<Coupon> findLastAddedCoupons(Pageable pageable);

}
