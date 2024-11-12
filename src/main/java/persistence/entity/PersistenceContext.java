package persistence.entity;

public interface PersistenceContext {
    /**
     * 관리하고 있는 엔티티를 캐싱된 스토리지에서 가져온다.
     *
     * @param clazz 엔티티 클래스
     * @param id    식별자
     * @return 관리 중인 엔티티
     */
    Object getEntity(Class<?> clazz, Long id);

    /**
     * 캐시 스토리지에 해당 엔티티를 생성/수정 한다.
     *
     * @param entity 생성/수정 대상 엔티티
     */
    void putEntity(Object entity);

    /**
     * 캐시 스토리지에서 해당 엔티티를 제거한다.
     *
     * @param entity 삭제 대상 엔티티
     */
    void removeEntity(Object entity);

    /**
     * Entity 스냅샷 생성
     *
     * @param id     식별자
     * @param entity 엔티티
     * @return 스냅샷
     */
    Object getSnapshot(Long id, Object entity);
}
