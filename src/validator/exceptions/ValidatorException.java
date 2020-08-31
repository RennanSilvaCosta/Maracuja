package validator.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidatorException extends RuntimeException {

    private final Map<String, String> errors = new HashMap<>();

    public ValidatorException() {
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String errorMessage) {
        errors.put(fieldName, errorMessage);
    }
}
