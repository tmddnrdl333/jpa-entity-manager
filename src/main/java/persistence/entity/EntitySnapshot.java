package persistence.entity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntitySnapshot {
    private final Map<String, Object> fields = new HashMap<>();

    private EntitySnapshot() {
    }

    public static EntitySnapshot of(Object entity) {
        EntitySnapshot entitySnapshot = new EntitySnapshot();
        Map<String, Object> fields = entitySnapshot.fields;

        Class<?> clazz = entity.getClass();
        Field[] managedFields = EntityUtils.getManagedFields(clazz);
        for (Field field : managedFields) {
            String fieldName = field.getName();
            Object fieldValue = EntityUtils.getFieldValue(field, entity);
            fields.put(fieldName, fieldValue);
        }

        return entitySnapshot;
    }

    public EntityState compareAndGetState(Object entity) {
        Map<String, Object> originFields = this.fields;

        if (entity == null) {
            return EntityState.DELETED;
        }

        Map<String, Object> newFields = EntitySnapshot.of(entity).fields;

        for (String fieldName : originFields.keySet()) {
            Object originFieldValue = originFields.get(fieldName);
            Object newFieldValue = newFields.get(fieldName);

            if (!originFieldValue.equals(newFieldValue)) {
                return EntityState.MODIFIED;
            }
        }

        return EntityState.UNCHANGED;
    }
}
