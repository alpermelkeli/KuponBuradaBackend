package com.kuponburada.KuponBurada.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.ZonedDateTime;

@Entity
@Table(name = "user_followed_brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBrandFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @CreationTimestamp
    @Column(name = "followed_at", updatable = false)
    private ZonedDateTime followedAt;
}
