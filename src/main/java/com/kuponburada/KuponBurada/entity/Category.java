package com.kuponburada.KuponBurada.entity;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    private boolean active = true;

    @Column(name = "is_popular")
    private boolean isPopular = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @ManyToMany(mappedBy = "categories")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Brand> brands = new HashSet<>();


    public Set<Coupon> getCoupons() {
        Set<Coupon> coupons = new HashSet<>();
        for (Brand brand : brands) {
            coupons.addAll(brand.getCoupons());
        }
        return coupons;
    }
}
