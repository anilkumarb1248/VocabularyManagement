package com.vocabulary.learning.app.exception;

import java.util.Map;

public class EntityNotFoundException extends Exception {

    private final String entityName;
    private final String lookupMethod;
    private transient Map<String, Object> lookupFields;

    public EntityNotFoundException(String entityName, String lookupMethod) {
        this(entityName, lookupMethod, null);
    }

    public EntityNotFoundException(String entityName, String lookupMethod, Map<String, Object> lookupFields) {
        this.entityName = entityName;
        this.lookupMethod = lookupMethod;
        this.lookupFields = lookupFields;
    }

    @Override
    public String getMessage() {
        return new StringBuilder()
                .append("Entity not found with entity name: ").append(entityName)
                .append(", lookupMethod: ").append(lookupMethod)
                .append(", lookupFields: ").append(lookupFields).toString();
    }

    public String getEntityName() {
        return entityName;
    }

    public String getLookupMethod() {
        return lookupMethod;
    }

    public Map<String, Object> getLookupFields() {
        return lookupFields;
    }
}
