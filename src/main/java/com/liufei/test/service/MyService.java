package com.liufei.test.service;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

public class MyService implements PersistentStateComponent<MyService> {

    private String username;

    private String password;

    private String remark;

    @Override
    public MyService getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MyService state) {
        XmlSerializerUtil.copyBean(state, this);
    }


    /**
     *
     * state 类中可能有多个字段，但不是所有字段都可以被持久化，可以被持久化的字段：
     *
     * public 字段
     * bean 属性：提供 getter 和 setter 方法
     * 被注解（https://upsource.jetbrains.com/idea-ce/file/idea-ce-d00d8b4ae3ed33097972b8a4286b336bf4ffcfab/platform/util/src/com/intellij/util/xmlb/annotations）的私有字段：使用 @Tag, @Attribute, @Property, @MapAnnotation, @AbstractCollection 等注解来自定义存储格式，一般在实现向后兼容时才考虑使用这些注解
     * 这些字段也有类型要求：
     *
     * 数字（包括基础类型，如int，和封装类型，如Integer）
     * 布尔值
     * 字符串
     * 集合
     * map
     * 枚举
     * 如果不希望某个字段被持久化，可以使用 @com.intellij.util.xmlb.annotations.Transient 注解。
     *
     *
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
