package com.liufei.test.util;

import com.intellij.openapi.project.Project;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.*;
import java.nio.file.Paths;

public class GitUtil {

    private static String readGitConfig(Project project) throws IOException {
        String projectPath = project.getBasePath();
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
    public static String getGitUrl(Project project) throws IOException {
        String gitUrl = readGitConfig(project);
        if (gitUrl == null || gitUrl.length() == 0) {
            return gitUrl;
        }
        String[] split = gitUrl.split("=");
        if (split.length > 1) {
            return split[1].trim();
        }
        return "";
    }


    public static String getGitUrl2(Project project) throws IOException {
        String localPath = project.getBasePath();
        Repository build = new FileRepositoryBuilder()
                .setGitDir(Paths.get(localPath, ".git").toFile())
                .build();
        return ((FileRepository)build).getConfig().getString("remote" , "origin", "url");
    }
}
