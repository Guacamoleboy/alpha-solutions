package alpha.exception;

// Created by: Guacamoleboy
// ________________________
// Last updated: 21/02-2026
// By: Guacamoleboy

public class ApiException extends RuntimeException {

    //  Custom exception for API calls
    //      - Unchecked exception (runtime) no try-catch needed
    //      - RuntimeException -> Exception -> Throwable

    // Attributes
    private final int code;
    private final String location;

    // _________________________________________________________________________________________________________________
    // (+)Message (-)Location

    public ApiException(String message) {
        super(message);
        this.code = 500;
        this.location = null;
    }

    // _________________________________________________________________________________________________________________
    // (+)Message (+)Location

    public ApiException(String message, String location) {
        super(message);
        this.code = 500;
        this.location = location;
    }

    // _________________________________________________________________________________________________________________
    // (+)Message (+)Cause (-)Location
    public ApiException(String message, Throwable cause) {
        super(message, cause);
        this.code = 500;
        this.location = null;
    }

    // _________________________________________________________________________________________________________________
    // (+)Message (+)Cause (+)Location

    public ApiException(String message, Throwable cause, String location) {
        super(message, cause);
        this.code = 500;
        this.location = location;
    }

    // _________________________________________________________________________________________________________________

    // (+)Code (+)Message (-)Location
    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.location = null;
    }

    // _________________________________________________________________________________________________________________
    // (+)Code (+)Message (+)Location

    public ApiException(int code, String message, String location) {
        super(message);
        this.code = code;
        this.location = location;
    }

    // _________________________________________________________________________________________________________________
    // (+)Code (+)Message (+)Cause (-)Location

    public ApiException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.location = null;
    }

    // _________________________________________________________________________________________________________________
    // (+)Code (+)Message (+)Cause (+)Location

    public ApiException(int code, String message, Throwable cause, String location) {
        super(message, cause);
        this.code = code;
        this.location = location;
    }

    // _________________________________________________________________________________________________________________

    public int getCode(){
        return this.code;
    }

    // _________________________________________________________________________________________________________________

    public String getLocation(){
        return this.location;
    }

}