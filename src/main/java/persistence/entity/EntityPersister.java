package persistence.entity;

import jdbc.JdbcTemplate;
import persistence.sql.dml.delete.DeleteQueryBuilder;
import persistence.sql.dml.insert.InsertQueryBuilder;
import persistence.sql.dml.update.UpdateQueryBuilder;

public class EntityPersister {
    private final JdbcTemplate jdbcTemplate;

    public EntityPersister(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void update(Object entity) {
        String updateQuery = UpdateQueryBuilder.generateQuery(entity);
        jdbcTemplate.execute(updateQuery);
    }

    public Long insert(Object entity) {
        String insertQuery = InsertQueryBuilder.generateQuery(entity);
        return jdbcTemplate.executeAndReturnGeneratedKey(insertQuery);
    }

    public void delete(Object entity) {
        String deleteQuery = DeleteQueryBuilder.generateQuery(entity.getClass(), entity);
        jdbcTemplate.execute(deleteQuery);
    }
}
