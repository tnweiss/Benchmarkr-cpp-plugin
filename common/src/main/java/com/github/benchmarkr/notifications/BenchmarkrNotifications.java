package com.github.benchmarkr.notifications;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;

public class BenchmarkrNotifications {
  public static final String NOTIFICATIONS_GROUP = "Benchmarkr Notification Group";

  public static void error(Project project, String message) {
    NotificationGroupManager.getInstance()
        .getNotificationGroup(NOTIFICATIONS_GROUP)
        .createNotification(message, MessageType.ERROR)
        .notify(project);
  }

  public static void error(Project project, Exception ex) {
    error(project, ex.getMessage());
  }

  public static void info(Project project, String message) {
    NotificationGroupManager.getInstance()
        .getNotificationGroup(NOTIFICATIONS_GROUP)
        .createNotification(message, MessageType.INFO)
        .notify(project);
  }
}
