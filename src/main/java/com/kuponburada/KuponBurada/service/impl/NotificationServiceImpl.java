package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.notification.NotificationRequest;
import com.kuponburada.KuponBurada.dto.response.notification.NotificationDTO;
import com.kuponburada.KuponBurada.entity.Notification;
import com.kuponburada.KuponBurada.repository.NotificationRepository;
import com.kuponburada.KuponBurada.service.NotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Notification createNotification(NotificationRequest notificationRequest) {
        if (notificationRequest == null) {
            throw new IllegalArgumentException("Notification request cannot be null");
        }
        Notification notification = modelMapper.map(notificationRequest, Notification.class);

        notification = notificationRepository.save(notification);

        return notification;
    }

    @Override
    public List<NotificationDTO> getNotifications(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationDTO.class))
                .toList();
    }

    @Override
    public void readNotification(Long notificationId) {
        if (notificationId == null) {
            throw new IllegalArgumentException("Notification id cannot be null");
        }
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void readAllNotifications(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        if (notificationId == null) {
            throw new IllegalArgumentException("Notification id cannot be null");
        }
        notificationRepository.deleteById(notificationId);
    }

    @Override
    public void deleteAllNotifications(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        List<Notification> notifications = notificationRepository.findByUserId(userId);
        notificationRepository.deleteAll(notifications);
    }
}
