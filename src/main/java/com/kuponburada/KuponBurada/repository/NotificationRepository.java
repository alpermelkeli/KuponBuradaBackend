package com.kuponburada.KuponBurada.repository;

import com.kuponburada.KuponBurada.dto.request.notification.NotificationRequest;
import com.kuponburada.KuponBurada.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.userId = :userId")
    List<Notification> findByUserId(Long userId);
}
