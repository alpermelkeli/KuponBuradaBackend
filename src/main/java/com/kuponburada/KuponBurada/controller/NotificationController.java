package com.kuponburada.KuponBurada.controller;

import com.kuponburada.KuponBurada.dto.request.notification.NotificationRequest;
import com.kuponburada.KuponBurada.dto.response.notification.NotificationDTO;
import com.kuponburada.KuponBurada.entity.Notification;
import com.kuponburada.KuponBurada.service.NotificationService;
import com.kuponburada.KuponBurada.util.SecurityContextHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SecurityContextHandler securityContextHandler;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications() {
        Long userId = securityContextHandler.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(notificationService.getNotifications(userId));
    }

    @PutMapping("/mark-as-all-read")
    public ResponseEntity<Object> markAsAllRead(){
        Long userId = securityContextHandler.getCurrentUserId();
        if (userId == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        notificationService.readAllNotifications(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/mark-as-read/{id}")
    public ResponseEntity<Object> markAsRead(@PathVariable Long id){
        notificationService.readNotification(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationRequest notificationRequest){
        if (notificationRequest == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(notificationService.createNotification(notificationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNotification(@PathVariable Long id){
        notificationService.deleteNotification(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Object> deleteAllNotifications(){
        if(!securityContextHandler.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long userId = securityContextHandler.getCurrentUserId();
        notificationService.deleteAllNotifications(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
