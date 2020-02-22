package com.cn.web.core.database;

import javax.persistence.Entity;
import javax.persistence.Table;

public class EntityUtils {

    public static String getEntityName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table != null && !table.name().isEmpty()) {
            return table.name();
        }
        Entity entity = clazz.getAnnotation(Entity.class);
        if (entity != null && !entity.name().isEmpty()) {
            return entity.name();
        }
        return clazz.getSimpleName();
    }

}
