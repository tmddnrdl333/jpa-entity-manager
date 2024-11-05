package persistence.entity;

import jdbc.EntityRowMapper;
import jdbc.JdbcTemplate;
import persistence.sql.dml.delete.DeleteQueryBuilder;
import persistence.sql.dml.insert.InsertQueryBuilder;
import persistence.sql.dml.select.SelectQueryBuilder;
import persistence.sql.dml.update.UpdateQueryBuilder;

public class EntityPersister {
    private final JdbcTemplate jdbcTemplate;

    public EntityPersister(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Object find(Class<?> clazz, Long id) {
        String findByIdQuery = SelectQueryBuilder.generateQuery(clazz, id);
        return jdbcTemplate.queryForObject(findByIdQuery, new EntityRowMapper<>(clazz));
    }

    public void update(Object entity) {
        String updateQuery = UpdateQueryBuilder.generateQuery(entity);
        jdbcTemplate.execute(updateQuery);
    }

    public void insert(Object entity) {
        String insertQuery = InsertQueryBuilder.generateQuery(entity);
        jdbcTemplate.execute(insertQuery);
    }

    public void delete(Object entity) {
        String deleteQuery = DeleteQueryBuilder.generateQuery(entity.getClass(), entity);
        jdbcTemplate.execute(deleteQuery);
    }
}
