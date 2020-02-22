package com.cn.web.core.plugin;

import java.util.HashMap;

public class Profile {
    private HashMap<String, Object> extras;

    public HashMap<String, Object> getExtras() {
        return extras;
    }

    public Object get(String key) {
        unparcel();
        return extras.get(key);
    }

    public int getInteger(String key) {
        return getInteger(key, 0);
    }

    public int getInteger(String key, int def) {
        unparcel();
        Object o = extras.get(key);
        if (o == null) {
            return def;
        }
        try {
            return Integer.parseInt(o.toString());
        } catch (Throwable e) {
            return def;
        }
    }

    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public long getLong(String key, long def) {
        unparcel();
        Object o = extras.get(key);
        if (o == null) {
            return def;
        }
        try {
            return Long.parseLong(o.toString());
        } catch (Throwable e) {
            return def;
        }
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean def) {
        unparcel();
        Object o = extras.get(key);
        if (o == null) {
            return def;
        }
        try {
            return Boolean.parseBoolean(o.toString());
        } catch (Throwable e) {
            return def;
        }
    }

    public String getString(String key) {
        unparcel();
        Object o = extras.get(key);
        try {
            return (String) o;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public String getString(String key, String def) {
        String s = getString(key);
        return (s == null) ? def : s;
    }

    public void put(String key, Object value) {
        unparcel();
        extras.put(key, value);
    }

    public void putString(String key, String value) {
        unparcel();
        extras.put(key, value);
    }

    private void unparcel() {
        if (extras == null) {
            synchronized (this) {
                if (extras == null) {
                    extras = new HashMap<>();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Profile{" +
                "extras=" + extras +
                '}';
    }
}
