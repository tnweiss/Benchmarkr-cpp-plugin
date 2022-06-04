package com.github.benchmarkr.actions;

import com.github.benchmarkr.BenchmarkrIcons;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.ui.MessageType;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrInitElkAction extends AnAction {
  public static final String DESCRIPTION = "Initialize ELK indices and dashboards";
  public static final String TEXT = "Initialize ELK";

  public BenchmarkrInitElkAction() {
    super(BenchmarkrIcons.InitializeIcon);
  }

  @Override
  public void update(@NotNull AnActionEvent event) {
    Presentation presentation = event.getPresentation();
    presentation.setDescription(DESCRIPTION);
    presentation.setText(TEXT);
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    NotificationGroupManager.getInstance()
        .getNotificationGroup("Benchmarkr Notification Group")
        .createNotification("Hello world", MessageType.INFO)
        .notify(e.getProject());
  }
}
