package persistence.entity;

public interface PersistenceContext {
    Object get(Class<?> clazz, Long id);

    void put(Object entity);

    void remove(Object entity);
}
