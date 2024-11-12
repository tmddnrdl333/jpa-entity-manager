package persistence.entity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntitySnapshot {
    private final Map<Class<?>, Object> fields = new HashMap<>();

    private EntitySnapshot() {
    }

    public static EntitySnapshot of(Object entity) {
        EntitySnapshot entitySnapshot = new EntitySnapshot();
        Map<Class<?>, Object> fields = entitySnapshot.fields;

        Long idValue = EntityUtils.getIdValue(entity);
        fields.put(Long.class, idValue);

        Class<?> clazz = entity.getClass();
        Field[] managedFields = EntityUtils.getManagedFields(clazz);
        for (Field field : managedFields) {
            Class<?> type = field.getType();
            Object fieldValue = EntityUtils.getFieldValue(field, entity);
            fields.put(type, fieldValue);
        }

        return entitySnapshot;
    }
}
