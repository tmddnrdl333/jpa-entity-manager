package persistence.entity;

import java.util.HashMap;
import java.util.Map;

public class PersistenceContextImpl implements PersistenceContext {
    private final Map<Class<?>, Map<Long, Object>> cacheStorage;
    private final Map<Class<?>, Map<Long, EntitySnapshot>> snapshotStorage;

    public PersistenceContextImpl() {
        this.cacheStorage = new HashMap<>();
        this.snapshotStorage = new HashMap<>();
    }

    private Map<Long, Object> getOrCreateEntityMap(Class<?> clazz) {
        if (!this.cacheStorage.containsKey(clazz)) {
            this.cacheStorage.put(clazz, new HashMap<>());
        }
        return this.cacheStorage.get(clazz);
    }

    private Map<Long, EntitySnapshot> getOrCreateSnapshotMap(Class<?> clazz) {
        if (!this.snapshotStorage.containsKey(clazz)) {
            this.snapshotStorage.put(clazz, new HashMap<>());
        }
        return this.snapshotStorage.get(clazz);
    }

    @Override
    public Object getEntity(Class<?> clazz, Long id) {
        Map<Long, Object> entityMap = getOrCreateEntityMap(clazz);
        return entityMap.get(id);
    }

    @Override
    public void putEntity(Object entity) {
        Class<?> clazz = entity.getClass();
        Long idValue = EntityUtils.getIdValue(entity);

        Map<Long, Object> entityMap = getOrCreateEntityMap(clazz);
        entityMap.put(idValue, entity);

        Map<Long, EntitySnapshot> snapshotMap = getOrCreateSnapshotMap(clazz);
        snapshotMap.put(idValue, EntitySnapshot.of(entity));
    }

    @Override
    public void removeEntity(Object entity) {
        Class<?> clazz = entity.getClass();
        Map<Long, Object> entityMap = getOrCreateEntityMap(clazz);
        entityMap.remove(EntityUtils.getIdValue(entity));
    }

    @Override
    public EntitySnapshot getSnapshot(Long id, Object entity) {
        Class<?> clazz = entity.getClass();
        return getOrCreateSnapshotMap(clazz).get(id);
    }
}
