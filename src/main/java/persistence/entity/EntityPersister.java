package persistence.entity;

public interface EntityPersister {
    void update(Object entity);

    Object insert(Object entity);

    void delete(Object entity);
}

