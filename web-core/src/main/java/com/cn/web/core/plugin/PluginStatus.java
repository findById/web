package com.cn.web.core.plugin;

public class PluginStatus {
    public static final PluginStatus CONNECTED = PluginStatus.newInstance(0, "connected");
    public static final PluginStatus DISCONNECTED = PluginStatus.newInstance(1, "disconnected");
    public static final PluginStatus CONNECTING = PluginStatus.newInstance(2, "connecting");
    public static final PluginStatus RECONNECTING = PluginStatus.newInstance(3, "reconnecting");
    public static final PluginStatus AWAIT = PluginStatus.newInstance(4, "await");
    public static final PluginStatus WORKING = PluginStatus.newInstance(5, "working");
    public static final PluginStatus RUNNING = PluginStatus.newInstance(6, "running");
    public static final PluginStatus ERROR = PluginStatus.newInstance(7, "error");

    private int statusCode;
    private String message;

    public PluginStatus(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public static PluginStatus newInstance(int statusCode, String message) {
        return new PluginStatus(statusCode, message);
    }

    public int statusCode() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
