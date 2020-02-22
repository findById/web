package com.cn.web.core.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by chenning on 2016/12/29.
 */
public class PluginClassLoader extends URLClassLoader {

    public PluginClassLoader(String path) {
        super(new URL[0], Thread.currentThread().getContextClassLoader());
        try {
            addURL(new File(path).toURI().toURL());
        } catch (IllegalArgumentException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param jarFile
     * @return
     */
    public List<Plugin> scanPlugin(JarFile jarFile) {
        List<Plugin> list = new ArrayList<>();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry jarEntry = entries.nextElement();
            String entryName = jarEntry.getName();
            if (!jarEntry.isDirectory() && entryName.endsWith(".class")) {
                String className = entryName.replace("/", ".").substring(0, entryName.length() - 6);
                try {
                    Class clazz = loadClass(className);
                    if (Plugin.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                        list.add((Plugin) clazz.newInstance());
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
