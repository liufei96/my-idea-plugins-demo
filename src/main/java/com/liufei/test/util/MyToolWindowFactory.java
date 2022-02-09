package com.liufei.test.util;

import com.intellij.execution.*;
import com.intellij.execution.configurations.*;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.impl.RunConfigurationLevel;
import com.intellij.execution.impl.RunnerAndConfigurationSettingsImpl;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.*;
import com.intellij.javascript.debugger.execution.DebuggableProgramRunner;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.liufei.test.util.dialog.AlertDialog;
import com.liufei.test.util.dialog.PromptDialog;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MyToolWindowFactory implements ToolWindowFactory {

    private JButton run = new JButton("运行");
    private JButton stop = new JButton("停止");
    private JButton restart = new JButton("重启");

    RunnerAndConfigurationSettingsImpl runnerAndConfigurationSettings;

    Executor debugExecutorInstance = DefaultDebugExecutor.getDebugExecutorInstance();

    private JButton runButton = new JButton("run");

    @SneakyThrows
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 此处方法将会在点击ToolWindow的时候触发
        // 获取ContentManager

        JPanel content = new MyWebToolWindowContent().getContent();
        content.add(runButton, BorderLayout.NORTH);

        ContentManager contentManager = toolWindow.getContentManager();
        Content labelContent =
                contentManager.getFactory() // 内容管理器获取工厂类
                        .createContent( // 创建Content（组件类实例、显示名称、是否可以锁定）
                                content,
                                "MyTab",
                                false
                        );
        // 利用ContentManager添加Content
        contentManager.addContent(labelContent);


        runButton.addActionListener(e -> {
            PromptDialog promptDialog = new PromptDialog("执行回访", "message_text");
            boolean isOk = promptDialog.showAndGet();
            String text = promptDialog.getText();
            System.out.println(text);
            System.out.println(isOk);
        });



//        VirtualFile workspaceFile = project.getWorkspaceFile();
//        String projectName = project.getName();
//        VirtualFile[] vFiles = ProjectRootManager.getInstance(project).getContentSourceRoots();
//        String sourceRootsList = Arrays.stream(vFiles).map(VirtualFile::getUrl).collect(Collectors.joining("\n"));
//        Messages.showInfoMessage("Source roots for the " + projectName + " plugin:\n" + sourceRootsList, "Project Properties");
//        System.out.println(workspaceFile.getCanonicalPath());

        RunConfiguration.RestartSingletonResult[] values = RunConfiguration.RestartSingletonResult.values();
        for (RunConfiguration.RestartSingletonResult value : values) {
            if (value.name().equals("RESTART")) {
            }
        }
        System.out.println(values);


        RunManager instance = RunManager.getInstance(project);
        // instance.createConfiguration("神农回滚测试", (ConfigurationFactory) instance.getTempConfigurationsList().get(3));
        List<RunnerAndConfigurationSettings> allSettings = instance.getAllSettings();
        for (RunnerAndConfigurationSettings settings : allSettings) {
            RunConfigurationLevel level = (RunConfigurationLevel) ReflectUtil.getValue(settings, "level");
            if (RunConfigurationLevel.WORKSPACE.name().equals(level.name())) {
                // 执行
                // ProgramRunnerUtil.executeConfiguration(allSetting, DefaultRunExecutor.getRunExecutorInstance());
                // debug的方式执行
                // ProgramRunnerUtil.executeConfiguration(settings, DefaultDebugExecutor.getDebugExecutorInstance());
                //System.out.println(debugExecutorInstance.getStartActionText());
                RunnerAndConfigurationSettingsImpl applicationConfiguration = (RunnerAndConfigurationSettingsImpl) settings;
                runnerAndConfigurationSettings = applicationConfiguration.clone();
                runnerAndConfigurationSettings.setName("神农回归测试");
                RunConfiguration configuration = runnerAndConfigurationSettings.getConfiguration();
                instance.addConfiguration(runnerAndConfigurationSettings);
            }
        }

//        RunnerAndConfigurationSettingsImpl runnerAndConfigurationSettings = applicationConfiguration.clone();
//
//        runnerAndConfigurationSettings.setName("神农回归测试");
//        ModuleBasedConfiguration configuration = (ModuleBasedConfiguration) runnerAndConfigurationSettings.getConfiguration();
//        Object state = configuration.getState();
//        instance.addConfiguration(runnerAndConfigurationSettings);
//        System.out.println(instance);

//        Executor debugExecutorInstance = DefaultDebugExecutor.getDebugExecutorInstance();
//        ProgramRunnerUtil.executeConfiguration(runnerAndConfigurationSettings, DefaultDebugExecutor.getDebugExecutorInstance());
//        System.out.println(debugExecutorInstance.getStartActionText());
//        System.out.println(debugExecutorInstance.getDescription());
//        System.out.println(debugExecutorInstance.isApplicable(project));

        // 重启idea
//        Application application = ApplicationManager.getApplication();
//        application.restart();

        run.addActionListener(e -> {
            instance.setSelectedConfiguration(runnerAndConfigurationSettings);
            ProgramRunnerUtil.executeConfiguration(runnerAndConfigurationSettings, debugExecutorInstance);
        });

        runnerAndConfigurationSettings.getManager().addRunManagerListener(new RunManagerListener() {
            @Override
            public void runConfigurationSelected(@Nullable RunnerAndConfigurationSettings settings) {
                RunManagerListener.super.runConfigurationSelected(settings);
                System.out.println("runConfigurationSelected");
            }

            @Override
            public void runConfigurationSelected() {
                RunManagerListener.super.runConfigurationSelected();
                System.out.println("runConfigurationSelected");
            }

            @Override
            public void beforeRunTasksChanged() {
                RunManagerListener.super.beforeRunTasksChanged();
                System.out.println("beforeRunTasksChanged");
            }

            @Override
            public void runConfigurationAdded(@NotNull RunnerAndConfigurationSettings settings) {
                RunManagerListener.super.runConfigurationAdded(settings);
                System.out.println("runConfigurationAdded");
            }

            @Override
            public void runConfigurationRemoved(@NotNull RunnerAndConfigurationSettings settings) {
                RunManagerListener.super.runConfigurationRemoved(settings);
                System.out.println("runConfigurationRemoved");
            }

            @Override
            public void runConfigurationChanged(@NotNull RunnerAndConfigurationSettings settings, @Nullable String existingId) {
                RunManagerListener.super.runConfigurationChanged(settings, existingId);
                System.out.println("runConfigurationChanged");
            }

            @Override
            public void runConfigurationChanged(@NotNull RunnerAndConfigurationSettings settings) {
                RunManagerListener.super.runConfigurationChanged(settings);
                System.out.println("runConfigurationChanged");
            }

            @Override
            public void beginUpdate() {
                RunManagerListener.super.beginUpdate();
                System.out.println("beginUpdate");
            }

            @Override
            public void endUpdate() {
                RunManagerListener.super.endUpdate();
                System.out.println("endUpdate");
            }

            @Override
            public void stateLoaded(@NotNull RunManager runManager, boolean isFirstLoadState) {
                RunManagerListener.super.stateLoaded(runManager, isFirstLoadState);
                System.out.println("stateLoaded");
            }
        });

        restart.addActionListener(e -> {
            System.out.println("restart begin");
            ExecutionEnvironmentBuilder environment = ExecutionUtil.createEnvironment(debugExecutorInstance, runnerAndConfigurationSettings);
            ExecutionUtil.restart(environment.build());
            System.out.println("restart completed");
        });

        stop.addActionListener(e -> {
            System.out.println("stop begin");
            ExecutionEnvironmentBuilder environment = ExecutionUtil.createEnvironment(debugExecutorInstance, runnerAndConfigurationSettings);
            ExecutionUtil.handleExecutionError(environment.build(), new ExecutionException("中止"));
            System.out.println("stop completed");
        });
    }

    public JPanel jPanel() {
        JPanel jPanel = new JPanel(new FlowLayout());
        jPanel.add(run);
        jPanel.add(restart);
        jPanel.add(stop);
        return jPanel;
    }
}