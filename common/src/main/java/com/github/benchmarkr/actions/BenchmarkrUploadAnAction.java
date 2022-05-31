package com.github.benchmarkr.actions;

import com.github.benchmarkr.BenchmarkrIcons;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.ui.MessageType;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrUploadAnAction extends AnAction {
    public static final String DESCRIPTION = "Upload test results";
    public static final String TEXT = "Upload Results";

    public BenchmarkrUploadAnAction() {
        super(BenchmarkrIcons.UploadIcon);
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
