package com.cn.web.core.database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class JPAAccess {

    @PersistenceContext
    EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Query createQuery(String ql) {
        return entityManager.createQuery(ql);
    }

    public Query createQuery(String ql, Class<?> entity) {
        return entityManager.createQuery(ql, entity);
    }

    public Query createNativeQuery(String sql) {
        return entityManager.createNativeQuery(sql);
    }

    public Query createNativeQuery(String sql, Class<?> entity) {
        return entityManager.createNativeQuery(sql, entity);
    }

    public <T> List<T> find(String ql, Map<String, Object> params, Class<T> entity) {
        return find(ql, params, 0, 0, entity);
    }

    public <T> List<T> find(String ql, Map<String, Object> params, int offset, int size, Class<T> entity) {
        Query query = entityManager.createQuery(ql, entity);
        if (params != null && !params.isEmpty()) {
            params.keySet().stream().filter(key -> key != null && !key.isEmpty()).forEachOrdered(key -> {
                query.setParameter(key, params.get(key));
            });
        }
        if (offset > 0) {
            query.setFirstResult(offset);
        }
        if (size > 0) {
            query.setMaxResults(size);
        }
        return query.getResultList();
    }

    public <T> T find(Class<T> entity, Object primaryKey) {
        return entityManager.find(entity, primaryKey);
    }

    public void save(Object entity) {
        entityManager.persist(entity);
    }

    public <T> T update(T entity) {
        return entityManager.merge(entity);
    }

    public int update(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && !params.isEmpty()) {
            params.keySet().stream().filter(key -> key != null && !key.isEmpty()).forEach(key -> {
                query.setParameter(key, params.get(key));
            });
        }
        return query.executeUpdate();
    }

    public void delete(Object entity) {
        entityManager.remove(entity);
    }

    public void delete(Class<?> entity, Object primaryKey) {
        entityManager.remove(entityManager.find(entity, primaryKey));
    }

    public <T> List<T> getResultList(String sql, Map<String, Object> params, Class<T> entity) {
        return getResultList(sql, params, 0, 0, entity);
    }

    public <T> List<T> getResultList(String sql, Map<String, Object> params, int offset, int size, Class<T> entity) {
        Query query = entityManager.createNativeQuery(sql, entity);
        if (params != null && !params.isEmpty()) {
            params.keySet().stream().filter(key -> key != null && !key.isEmpty()).forEachOrdered(key -> {
                query.setParameter(key, params.get(key));
            });
        }
        if (offset > 0) {
            query.setFirstResult(offset);
        }
        if (size > 0) {
            query.setMaxResults(size);
        }
        return query.getResultList();
    }

    public Object getSingleResult(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        if (params != null && !params.isEmpty()) {
            params.keySet().stream().filter(key -> key != null && !key.isEmpty()).forEach(key -> {
                query.setParameter(key, params.get(key));
            });
        }
        return query.getSingleResult();
    }

}
