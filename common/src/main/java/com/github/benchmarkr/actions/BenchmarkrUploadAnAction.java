package com.github.benchmarkr.actions;

import com.github.benchmarkr.BenchmarkrIcons;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrUploadAnAction extends AnAction {
    public BenchmarkrUploadAnAction() {
        super(BenchmarkrIcons.UploadIcon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // log the action
        // logger.error("Running upload results");
        NotificationGroupManager.getInstance().getRegisteredNotificationGroups().forEach(n -> System.out.println(n.getDisplayId()));

        NotificationGroupManager.getInstance()
            .getNotificationGroup("Benchmarkr Notification Group")
            .createNotification("Hello world", MessageType.INFO)
            .notify(e.getProject());
    }
}
