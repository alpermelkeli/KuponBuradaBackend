package com.kuponburada.KuponBurada.dto.request.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String username;
    private String title;
    private String message;
    private String notificationType;
}
