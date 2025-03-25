package com.kuponburada.KuponBurada.service;

import com.kuponburada.KuponBurada.dto.request.notification.NotificationRequest;
import com.kuponburada.KuponBurada.dto.response.notification.NotificationDTO;
import com.kuponburada.KuponBurada.entity.Notification;

import java.util.List;

public interface NotificationService {
    NotificationDTO createNotification(NotificationRequest notificationRequest);
    List<NotificationDTO> getNotifications(Long userId);
    void readNotification(Long notificationId);
    void readAllNotifications(Long userId);
    void deleteNotification(Long notificationId);
    void deleteAllNotifications(Long userId);
}
