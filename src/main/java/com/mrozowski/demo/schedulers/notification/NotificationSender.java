package com.mrozowski.demo.schedulers.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class NotificationSender implements Sender {
  @Override
  public void sendNotification(NotificationCommand command) {
    log.info("Notification sent {}", command);
  }
}
