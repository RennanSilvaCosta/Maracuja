package http.response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final Map<Integer, String> errors = new HashMap<>();

    public HttpResponse() {
    }

    public Map<Integer, String> getResponse() {
        return errors;
    }

    public void addResponse(Integer codeResponse, String message) {
        errors.put(codeResponse, message);
    }
}
