package com.liufei.test;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class HelloWorldAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

//        JBPopup jbPopup = JBPopupFactory.getInstance().createMessage("hello world");
//        jbPopup.showInBestPositionFor(e.getDataContext());


        Project project = e.getProject();

        MyDialog dialog = new MyDialog(project);
        dialog.setVisible(true);
        dialog.pack();
//        System.exit(0);
    }
}
