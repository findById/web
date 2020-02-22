package com.cn.web.core.plugin;

/**
 * Created by chenning on 2016/12/30.
 */
public class PluginException extends Exception {

    public PluginException(String message) {
        super(message);
    }

    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }

}
