package com.liufei.test.util;

import java.lang.reflect.Field;

public class ReflectUtil {

    public static Object getValue(Object obj, String fieldName) {

        try {
            Class<?> aClass = obj.getClass();
            Field field = aClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }
}
