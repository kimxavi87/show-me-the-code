package com.kimxavi.example.container;

import com.kimxavi.example.annotation.KimxaviInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ServiceContainer {

    public static <T> T getObject(Class<T> tClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        T object = createInstanceNew(tClass);
        for (Field declaredField : object.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            Annotation annotation = declaredField.getAnnotation(KimxaviInject.class);

            if (annotation != null) {
                declaredField.set(object, createInstanceNew(declaredField.getType()));
            }
        }

        return object;
    }

    @Deprecated
    private static <T> T createInstance(Class<T> tClass) throws IllegalAccessException, InstantiationException {
        return tClass.newInstance();
    }

    private static <T> T createInstanceNew(Class<T> tClass) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // https://docs.oracle.com/javase/9/docs/api/java/lang/Class.html#newInstance--
        return tClass.getDeclaredConstructor().newInstance();
    }
}
