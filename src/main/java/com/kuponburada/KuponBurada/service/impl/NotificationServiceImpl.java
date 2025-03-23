package com.kuponburada.KuponBurada.service.impl;

import com.kuponburada.KuponBurada.dto.request.notification.NotificationRequest;
import com.kuponburada.KuponBurada.dto.response.notification.NotificationDTO;
import com.kuponburada.KuponBurada.entity.Coupon;
import com.kuponburada.KuponBurada.entity.Notification;
import com.kuponburada.KuponBurada.entity.User;
import com.kuponburada.KuponBurada.repository.NotificationRepository;
import com.kuponburada.KuponBurada.repository.UserRepository;
import com.kuponburada.KuponBurada.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Notification createNotification(NotificationRequest notificationRequest) {
        if (notificationRequest == null) {
            throw new IllegalArgumentException("Notification request cannot be null");
        }
        User user = userRepository.findByUsername(notificationRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Coupon not found with id: " + notificationRequest.getUsername()));

        Notification notification = modelMapper.map(notificationRequest, Notification.class);

        notification.setUser(user);

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
