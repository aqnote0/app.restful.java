package com.madding.shared.restful.util;

/**
 * 类ClassLoaderUtil.java的实现描述：classLoader加载类
 * 
 * @author madding.lip Sep 18, 2013 11:27:20 AM
 */
public class ClassLoaderUtil {

    public static ClassLoader getSystemClassLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return (classLoader == null) ? getSystemClassLoader() : classLoader;
    }

    public static ClassLoader getClassLoader(ClassLoader classLoader) {
        return (classLoader == null) ? getClassLoader() : classLoader;
    }

}
