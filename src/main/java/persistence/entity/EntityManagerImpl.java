package persistence.entity;

import jdbc.JdbcTemplate;

public class EntityManagerImpl implements EntityManager {
    private final EntityPersister entityPersister;
    private PersistenceContext persistenceContext;

    public EntityManagerImpl(JdbcTemplate jdbcTemplate) {
        this.entityPersister = new EntityPersister(jdbcTemplate);
    }

    public void beginTransaction() {
        this.persistenceContext = new PersistenceContextImpl();
    }

    public void commitTransaction() {
        /* TODO : 변경사항 감지 및 업데이트 쿼리 실행 */
    }

    @Override
    public Object find(Class<?> clazz, Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Unable to find without an ID!");
        }

        /* 관리 중인 엔티티라면 영속성 컨텍스트에서 꺼내서 반환 */
        Object managedEntity = persistenceContext.get(clazz, id);
        if (managedEntity != null) {
            return managedEntity;
        }

        /* 관리 중이지 않다면 DB 조회 */
        Object entity = entityPersister.find(clazz, id);
        persistenceContext.put(entity);
        return entity;
    }

    @Override
    public void persist(Object newEntity) {
        Long generatedKey = entityPersister.insert(newEntity);
        Object insertedEntity = entityPersister.find(newEntity.getClass(), generatedKey);
        persistenceContext.put(insertedEntity);
    }

    @Override
    public void remove(Object entity) {
        entityPersister.delete(entity);
    }
}
