package task5.exception;

/**
 * custom exception for DynamicClassLoaderApp application
 * also can throw when wrap other Checked/Unchecked exceptions
 */
public class AppRuntimeException extends RuntimeException {

    private String messageForUser;

    public AppRuntimeException() {
    }

    public AppRuntimeException(Throwable cause) {
        super(cause);
    }

    AppRuntimeException(String message, String messageForUser) {
        super(message);
        this.messageForUser = messageForUser;
    }

    /**
     * get message readable for user
     *
     * @return message
     */
    public String getMessageForUser() {
        return messageForUser;
    }
}
