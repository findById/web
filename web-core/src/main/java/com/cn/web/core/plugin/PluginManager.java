package com.cn.web.core.plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Created by chenning on 2016/12/29.
 */
public class PluginManager {

    private Map<String, PluginInfo> map = new HashMap<>();

    public String install(String jar) {
        PluginInfo info = loadPlugin(new File(jar));
        if (info != null && info.plugins != null && !info.plugins.isEmpty()) {
            for (Plugin plugin : info.plugins) {
                if (plugin != null) {
                    plugin.onCreate();
                }
            }
            map.put(info.id, info);
            return info.id;
        }
        return null;
    }

    public String install(String pluginId, Plugin plugin) {
        if (pluginId == null || plugin == null) {
            return null;
        }
        plugin.onCreate();
        PluginInfo info = new PluginInfo();
        info.id = pluginId;
        info.plugins.add(plugin);
        map.put(pluginId, info);
        return pluginId;
    }

    public String install(Plugin plugin) {
        String pluginId = UUID.randomUUID().toString();
        return install(pluginId, plugin);
    }

    public boolean uninstall(String id) {
        PluginInfo info = map.get(id);
        if (info != null) {
            map.remove(id);
            if (info.plugins != null && !info.plugins.isEmpty()) {
                for (Plugin plugin : info.plugins) {
                    if (plugin != null) {
                        plugin.onDestroy();
                    }
                }
            }
        }
        return true;
    }

    private PluginInfo loadPlugin(File file) {
        if (file == null || !file.exists()) {
            System.err.println("File not exists");
            return null;
        }
        PluginInfo info = new PluginInfo();

        JarFile jarFile = null;
        PluginClassLoader loader = null;
        try {
            jarFile = new JarFile(file);
            loader = new PluginClassLoader(file.getPath());

            try {
                Manifest manifest = jarFile.getManifest();
                Attributes attr = manifest.getMainAttributes();
                if (attr != null) {
                    String id = attr.getValue("Plugin-Id");
                    if (id != null && !id.isEmpty()) {
                        info.id = id;
                    }
                    String vendor = attr.getValue("Plugin-Vendor");
                    if (vendor != null && !vendor.isEmpty()) {
                        info.vendor = vendor;
                    }
                    String title = attr.getValue("Plugin-Title");
                    if (title != null && !title.isEmpty()) {
                        info.title = title;
                    }
                    String description = attr.getValue("Plugin-Description");
                    if (description != null && !description.isEmpty()) {
                        info.description = description;
                    }
                    String version = attr.getValue("Plugin-Version");
                    if (version != null && !version.isEmpty()) {
                        info.version = version;
                    }
                    String pluginClass = attr.getValue("Plugin-Class");
                    if (pluginClass != null && !pluginClass.isEmpty()) {
                        Class clazz = loader.loadClass(pluginClass);
                        if (Plugin.class.isAssignableFrom(clazz)) {
                            info.plugins.add((Plugin) clazz.newInstance());
                            return info;
                        }
                        System.err.println("'Plugin-Class' is not implementation class, [" + pluginClass + "]");
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            System.err.println("Scanning plugin implementation class, [" + jarFile.getName() + "]");
            List<Plugin> plugins = loader.scanPlugin(jarFile);
            if (plugins == null || plugins.isEmpty()) {
                System.err.println("Not found implementation class, [" + file.getPath() + "]");
                return null;
            }
            info.plugins.addAll(plugins);
            return info;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException ignored) {
                }
            }
            if (loader != null) {
                try {
                    loader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    public List<Plugin> getPlugins(String id) {
        List<Plugin> list = new ArrayList<>();
        try {
            if (id != null && !id.isEmpty()) {
                PluginInfo info = map.get(id);
                if (info != null && info.plugins != null) {
                    list.addAll(info.plugins);
                }
                return list;
            }
            for (String key : map.keySet()) {
                if (key != null && !"".equals(key)) {
                    list.addAll(map.get(key).plugins);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param method method
     * @param args
     * @return
     */
    public Object invokeMethod(String method, Object... args) throws PluginException {
        return invoke(null, method, args);
    }

    /**
     * @param id     pluginId
     * @param action method
     * @param args
     * @return
     */
    public Object invoke(String id, String action, Object... args) throws PluginException {
        List<Plugin> plugins = getPlugins(id);
        if (plugins == null || plugins.isEmpty()) {
            throw new PluginException("Plugin is not installed, [" + id + "]");
        }
        for (Plugin plugin : plugins) {
            Method[] methods = plugin.getClass().getDeclaredMethods();
            for (Method method : methods) {
                Action ann = method.getAnnotation(Action.class);
                if (ann != null && !"".equals(ann.value())) {
                    if (action.equals(ann.value())) {
                        try {
                            return method.invoke(plugin, args);
                        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                            throw new PluginException(e.getMessage(), e);
                        }
                    }
                }
            }
        }
        return null;
    }

}
