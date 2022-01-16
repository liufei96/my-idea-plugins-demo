package com.liufei.test;


import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.liufei.test.service.PersistentDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MyDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;

    private Project project;

    private PropertiesComponent propertiesComponent;

    public MyDialog(Project project) {
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
//        propertiesComponent = PropertiesComponent.getInstance(project);
//        String username = propertiesComponent.getValue("my_idea_username");
//        if (username != null && username.trim().length() > 0) {
//            textField1.setText(username);
//        }

        PersistentDemo persistDemo = ServiceManager.getService(project, PersistentDemo.class);
        if (persistDemo.getState() != null) {
            textField1.setText(persistDemo.getState().username);
            textField2.setText(persistDemo.getState().password);
            textArea1.setText(persistDemo.getState().remark);
        }

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - getHeight();
        setLocation(x, y);

        this.setBounds(this.getX(), getY(), 300, 200);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here
        // dispose();
//        propertiesComponent.setValue("my_idea_username", textField1.getText());

        PersistentDemo persistDemo = ServiceManager.getService(project, PersistentDemo.class);
        persistDemo.getState().username = textField1.getText();
        persistDemo.getState().password = textField2.getText();
        persistDemo.getState().remark = textArea1.getText();

        try {
            String gitUrl = getGitUrl(readGitConfig());
            persistDemo.getState().gitUrl = gitUrl;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String readGitConfig() throws IOException {
        PropertiesComponent instance = PropertiesComponent.getInstance(project);
        String projectPath = instance.getValue("last_opened_file_path");
        String gitUrl = "";
        File file = new File(projectPath + "\\.git\\config");
        if (!file.exists()) {
            return "";
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(projectPath + "\\.git\\config"), "UTF-8"));) {
            // 读取输出
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String content = line.trim();
                if (content.startsWith("url")) {
                    gitUrl = content;
                    break;
                }
            }
        }
        return gitUrl;
    }

    /**
     *  url = git@github.com:liufei96/my_java_algorithm.git  => git@github.com:liufei96/my_java_algorithm.git
     * 处理一下
     * @return
     */
    private String getGitUrl(String gitUrl) {
        if (gitUrl == null || gitUrl.length() == 0) {
            return gitUrl;
        }
        String[] split = gitUrl.split("=");
        if (split.length > 1) {
            return split[1].trim();
        }
        return "";
    }


    private void onCancel() {
        // add your code here if necessary
        // dispose();
        this.setVisible(false);
    }
}
