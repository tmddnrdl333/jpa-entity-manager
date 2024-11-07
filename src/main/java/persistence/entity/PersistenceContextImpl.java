package persistence.entity;

import java.util.HashMap;
import java.util.Map;

public class PersistenceContextImpl implements PersistenceContext {
    private final Map<Class<?>, Map<Long, Object>> cacheStorage;

    public PersistenceContextImpl() {
        this.cacheStorage = new HashMap<>();
    }

    @Override
    public Object get(Class<?> clazz, Long id) {
        Map<Long, Object> entityMap = this.cacheStorage.get(clazz);
        if (entityMap == null) {
            return null;
        }
        return entityMap.get(id);
    }

    @Override
    public void put(Object entity) {
        Class<?> clazz = entity.getClass();
        Map<Long, Object> entityMap;
        if (this.cacheStorage.containsKey(clazz)) {
            entityMap = this.cacheStorage.get(clazz);
        } else {
            entityMap = new HashMap<>();
            this.cacheStorage.put(clazz, entityMap);
        }
        entityMap.put(EntityUtils.getIdValue(entity), entity);
    }

    @Override
    public void remove(Object entity) {
        Class<?> clazz = entity.getClass();
        if (!this.cacheStorage.containsKey(clazz)) {
            throw new IllegalArgumentException("Unable to remove such entity!");
        }
        Map<Long, Object> entityMap = this.cacheStorage.get(clazz);
        entityMap.remove(EntityUtils.getIdValue(entity));
    }
}
