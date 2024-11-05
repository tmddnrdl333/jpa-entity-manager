package persistence.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistenceContextImpl implements PersistenceContext {
    private final Map<Class<?>, Map<Long, Object>> originalStorage;
    private final Map<Class<?>, Map<Long, Object>> updatedStorage;
    private final Map<Class<?>, List<Object>> insertingStorage;
    private final Map<Class<?>, List<Long>> deletingStorage;

    public PersistenceContextImpl() {
        this.originalStorage = new HashMap<>();
        this.updatedStorage = new HashMap<>();
        this.insertingStorage = new HashMap<>();
        this.deletingStorage = new HashMap<>();
    }

    @Override
    public Object get(Class<?> clazz, Long id) {
        Map<Long, Object> entityMap = originalStorage.get(clazz);
        if (entityMap == null) {
            return null;
        }
        return entityMap.get(id);
    }

    @Override
    public void put(Object entity) {
        Long idValue = EntityUtils.getIdValue(entity);
        if (idValue == null) {
            putInsertingStorage(entity);
        } else {
            putUpdatedStorage(idValue, entity);
        }
    }

    private void putUpdatedStorage(Long idValue, Object entity) {
        Class<?> clazz = entity.getClass();
        Map<Long, Object> entityMap;
        if (updatedStorage.containsKey(clazz)) {
            entityMap = updatedStorage.get(clazz);
        } else {
            entityMap = new HashMap<>();
            updatedStorage.put(clazz, entityMap);
        }
        entityMap.put(idValue, entity);
    }

    private void putInsertingStorage(Object entity) {
        Class<?> clazz = entity.getClass();
        List<Object> entityList;
        if (insertingStorage.containsKey(clazz)) {
            entityList = insertingStorage.get(clazz);
        } else {
            entityList = new ArrayList<>();
            insertingStorage.put(clazz, entityList);
        }
        entityList.add(entity);
    }

    @Override
    public void remove(Object entity) {
        Class<?> clazz = entity.getClass();
        List<Long> idList;
        if (deletingStorage.containsKey(clazz)) {
            idList = deletingStorage.get(clazz);
        } else {
            idList = new ArrayList<>();
            deletingStorage.put(clazz, idList);
        }
        idList.add(EntityUtils.getIdValue(entity));
    }
}
