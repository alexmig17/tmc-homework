package task5.exception;

/**
 * validation exception throw when uploaded class not valid
 */
public class DynamicClassValidationException extends AppRuntimeException {

    public DynamicClassValidationException(String message) {
        super(message, message);
    }
}
