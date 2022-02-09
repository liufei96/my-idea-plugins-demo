package com.liufei.test.util;

import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefClient;
import com.liufei.test.handler.JsDialogHandler;

import javax.swing.*;
import java.awt.*;

public class MyWebToolWindowContent {

    private JPanel content;

    public MyWebToolWindowContent() {
        this.content = new JPanel(new BorderLayout());
        // 判断所处的IDEA环境是否支持JCEF
        if (!JBCefApp.isSupported()) {
            this.content.add(new JLabel("当前环境不支持JCEF", SwingConstants.CENTER));
        }
        // 创建 JBCefBrowser
        JBCefBrowser jbCefBrowser = new JBCefBrowser();
        // 注册我们的Handler
//        jbCefBrowser.getJBCefClient()
//                .addJSDialogHandler(
//                        new JsDialogHandler(),
//                        jbCefBrowser.getCefBrowser());
        // 将 JBCefBrowser 的UI控件设置到Panel中
        this.content.add(jbCefBrowser.getComponent(), BorderLayout.CENTER);
        // 加载URL
        // jbCefBrowser.loadURL("https://www.cnblogs.com/liufei2");
    }

    /**
     * 返回创建的JPanel
     * @return JPanel
     */
    public JPanel getContent() {
        return content;
    }
}
