package com.liufei.test;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;

public class MyNotification {

    public MyNotification(String message, Project project) {
        init(project, message);
    }

    public void init(Project project, String message) {
        NotificationGroup notificationGroup = new NotificationGroup("com.liufei.test.my_idea_plugins", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(message, MessageType.INFO);
        notification.notify(project);
    }
}
