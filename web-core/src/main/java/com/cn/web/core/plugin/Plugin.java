package com.cn.web.core.plugin;

/**
 * Created by chenning on 2016/12/29.
 */
public interface Plugin {
    default void onCreate() {
    }

    default void onDestroy() {
    }

    default void onProfile(Profile profile) {
    }

    default PluginStatus status() {
        return PluginStatus.RUNNING;
    }
}
