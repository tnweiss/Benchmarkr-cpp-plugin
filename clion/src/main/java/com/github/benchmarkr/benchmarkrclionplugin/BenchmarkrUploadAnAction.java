package com.github.benchmarkr.benchmarkrclionplugin;

import com.intellij.icons.AllIcons;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.impl.NotificationGroupEP;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.ui.GotItMessage;

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
