package com.cn.web.core.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by chenning on 2016/12/29.
 */
public class PluginInfo {
    public String id = UUID.randomUUID().toString();
    public String vendor;
    public String title;
    public String description;
    public String version;
    public List<Plugin> plugins = new ArrayList<>();
}
