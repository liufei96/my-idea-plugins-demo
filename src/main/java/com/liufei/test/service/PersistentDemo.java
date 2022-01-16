package com.liufei.test.service;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "PersistentDemo", storages = {@Storage(value = "droplet.xml")})
public class PersistentDemo implements PersistentStateComponent<PersistentDemo> {

    public String username;

    public String password;

    public String remark;

    public String gitUrl;

    @Nullable
    @Override
    public PersistentDemo getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PersistentDemo persistentDemo) {
        XmlSerializerUtil.copyBean(persistentDemo, this);
    }


    /**
     * name： 定义 xml 文件根标签的名称
     * <p>
     * storages： 一个或多个 @Storage，定义存储的位置
     * <p>
     * 若是 application 级别的组件 运行调试时 xml 文件的位置： ~/IdeaICxxxx/system/plugins-sandbox/config/options 正式环境时 xml 文件的位置： ~/IdeaICxxxx/config/options
     * 若是 project 级别的组件，默认为项目的 .idea/misc.xml，若指定为 StoragePathMacros.WORKSPACE_FILE，则会被保存在 .idea/worksapce.xml
     */
}
