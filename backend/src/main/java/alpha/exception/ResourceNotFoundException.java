package alpha.exception;

// Created by: Guacamoleboy
// ________________________
// Last updated: 21/02-2026
// By: Guacamoleboy

public class ResourceNotFoundException extends RuntimeException {

    //  Custom exception for missing Resource calls
    //  - Unchecked exception (runtime) no try-catch needed
    //  - RuntimeException -> Exception -> Throwable

    // Attributes
    private final String entity;
    private final String entityValue;
    private final String location;

    // _________________________________________________________________________________________________________________
    // (+)Message

    public ResourceNotFoundException(String entity, String entityValue) {
        super(entity + " not found: " + entityValue);
        this.entity = entity;
        this.entityValue = entityValue;
        this.location = null;
    }

    // _________________________________________________________________________________________________________________
    // (+)Message (+)location

    public ResourceNotFoundException(String entity, String entityValue, String location) {
        super(entity + " not found: " + entityValue + " | " + location);
        this.entity = entity;
        this.entityValue = entityValue;
        this.location = location;
    }

    // _________________________________________________________________________________________________________________
    // (+)Cause (-)location

    public ResourceNotFoundException(String entity, String entityValue, Throwable cause) {
        super(entity + " not found: " + entityValue, cause);
        this.entity = entity;
        this.entityValue = entityValue;
        this.location = null;
    }

    // _________________________________________________________________________________________________________________
    // (+)Cause (+)location
    public ResourceNotFoundException(String entity, String entityValue, String location, Throwable cause) {
        super(entity + " not found: " + entityValue + " | " + location, cause);
        this.entity = entity;
        this.entityValue = entityValue;
        this.location = location;
    }

    // _________________________________________________________________________________________________________________

    public String getEntity() {
        return this.entity;
    }

    // _________________________________________________________________________________________________________________

    public String getEntityValue() {
        return this.entityValue;
    }

    // _________________________________________________________________________________________________________________

    private String getLocation(){
        return this.location;
    }

}