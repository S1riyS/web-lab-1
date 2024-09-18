package ru.s1riys.web.lab1.network;

import org.codehaus.jackson.map.ObjectMapper;
import ru.s1riys.web.lab1.exceptions.HttpResponseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpResponse {
    private final int statusCode;
    private final Map<String, String> headers = new HashMap<>();
    private String body;
    private String constructedResponse = null;

    // Constructor
    public HttpResponse(HttpStatusCode statusCode) {
        this.statusCode = statusCode.getValue();
    }

    public HttpResponse setHeader(String name, Object value) {
        headers.put(name, String.valueOf(value));
        return this;
    }

    public HttpResponse setContentTypeJSONHeader() {
        return this.setHeader("Content-Type", "application/json; charset=utf-8");
    }

    public HttpResponse setBody(String body) {
        this.body = body;
        return this;
    }

    public HttpResponse setBody(Boolean success, String message, HashMap<String, Object> data) throws HttpResponseException {
        HashMap<String, Object> processedData = Objects.requireNonNullElseGet(data, HashMap::new);
        HashMap<String, Object> fullResponseData = new HashMap<>() {{
            put("success", success);
            put("message", message);
            put("data", processedData);
        }};

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.body = objectMapper.writeValueAsString(fullResponseData);
            return this;
        } catch (IOException e) {
            throw new HttpResponseException();
        }
    }

    public HttpResponse setBody(Boolean success, String message) throws HttpResponseException {
        return this.setBody(success, message, null);
    }

    public String constructResponse() {
        if (constructedResponse != null) return constructedResponse;

        StringBuilder responseBuilder = new StringBuilder();

        // HTTP status header
        this.setHeader("Status", this.statusCode);
        // Content length header
        this.setHeader("Content-length", this.body.getBytes(StandardCharsets.UTF_8).length);
        // Other headers
        for (Map.Entry<String, String> header : headers.entrySet()) {
            responseBuilder.append(header.getKey())
                    .append(": ")
                    .append(header.getValue())
                    .append("\n");
        }

        responseBuilder.append("\n"); // End of headers
        responseBuilder.append(body); // Body of the response

        constructedResponse = responseBuilder.toString();
        return constructedResponse;
    }

    public void publish() {
        System.out.println(this.constructResponse());
    }
}
