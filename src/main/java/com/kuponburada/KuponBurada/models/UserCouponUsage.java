package com.kuponburada.KuponBurada.models;

import java.time.ZonedDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "user_coupon_usages")
@Data
public class UserCouponUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "used_at")
    @CreationTimestamp
    private ZonedDateTime usedAt;

    private Boolean success;

    private String feedback;
}