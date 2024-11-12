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
    public Map<Class<?>, Map<Long, Object>> getChangedEntities() {
        Map<Class<?>, Map<Long, Object>> cacheStates = new HashMap<>();

        for (Map.Entry<Class<?>, Map<Long, EntitySnapshot>> snapshotMapEntry : this.snapshotStorage.entrySet()) {
            Map<Long, Object> entityMap = getOrCreateEntityMap(snapshotMapEntry.getKey());
            Map<Long, EntitySnapshot> snapshotMap = this.snapshotStorage.get(snapshotMapEntry.getKey());
            cacheStates.put(snapshotMapEntry.getKey(), getChangedEntitiesInType(entityMap, snapshotMap));
        }
        return cacheStates;
    }

    private Map<Long, Object> getChangedEntitiesInType(Map<Long, Object> entityMap, Map<Long, EntitySnapshot> snapshotMap) {
        Map<Long, Object> changedEntitiesInType = new HashMap<>();

        for (Map.Entry<Long, EntitySnapshot> snapshotEntry : snapshotMap.entrySet()) {
            Object cacheEntity = entityMap.get(snapshotEntry.getKey());
            EntitySnapshot snapshot = snapshotEntry.getValue();
            EntityState entityState = snapshot.compareAndGetState(cacheEntity);

            switch (entityState) {
                case MODIFIED:
                    changedEntitiesInType.put(snapshotEntry.getKey(), cacheEntity);
                    break;
                case DELETED:
                    changedEntitiesInType.put(snapshotEntry.getKey(), null);
                    break;
                case UNCHANGED:
                default:
                    break;
            }
        }

        return changedEntitiesInType;
    }
}
