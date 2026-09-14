package alpha.exception;

public class DatabaseException extends Exception {

    // Attributes

    // _________________________________________________________________________________________________________________

    public DatabaseException(String userMessage, Throwable cause) {
        super(userMessage, cause);
        System.out.println("userMessage: " + userMessage);
        System.out.println("cause: " + cause);
    }

    // _________________________________________________________________________________________________________________

    public DatabaseException(String userMessage) {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
    }

    // _________________________________________________________________________________________________________________

    public DatabaseException(String userMessage, String systemMessage) {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
        System.out.println("errorMessage: " + systemMessage);
    }

}