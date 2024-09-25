package ru.s1riys.web.lab1;

import com.fastcgi.FCGIInterface;
import com.fastcgi.FCGIRequest;
import ru.s1riys.web.lab1.exceptions.HttpResponseException;
import ru.s1riys.web.lab1.exceptions.MissingParametersException;
import ru.s1riys.web.lab1.network.HttpResponse;
import ru.s1riys.web.lab1.network.HttpStatusCode;
import ru.s1riys.web.lab1.network.QueryParamsManager;

import java.util.HashMap;
import java.util.List;

public class Server {
    private static final long startTime = System.nanoTime();

    public static void main(String[] args) {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            processRequest(FCGIInterface.request);
        }
        System.err.println("FCGI server has been stopped");
    }

    private static void processRequest(FCGIRequest request) {
        // Request method must be GET
        String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
        if (!method.equals("GET")) {
            new HttpResponse(HttpStatusCode.METHOD_NOT_ALLOWED)
                    .setContentTypeJSONHeader()
                    .setBody(false, "Only GET method is allowed")
                    .publish();
            return;
        }

        try {
            // Retrieving and validating query parameters
            String queryString = FCGIInterface.request.params.getProperty("QUERY_STRING");
            HashMap<String, String> queryParams = QueryParamsManager.parse(queryString);
            Boolean isQueryParamsConsistent = QueryParamsManager.checkConsistency(
                    queryParams,
                    List.of("x", "y", "r")
            );
            if (!isQueryParamsConsistent) throw new MissingParametersException();

            Float x = Float.parseFloat(queryParams.get("x"));
            Float y = Float.parseFloat(queryParams.get("y"));
            Float r = Float.parseFloat(queryParams.get("r"));

            if (Validator.validateAll(x, y, r)) {
                // Collecting payload's data
                Boolean hit = Checker.hit(x, y, r);
                Long scriptTime = getExecutionTime();
                Long currentTime = System.currentTimeMillis();
                HashMap<String, Object> payload = new HashMap<>() {{
                    put("hit", hit);
                    put("scriptTime", scriptTime);
                    put("currentTime", currentTime);
                }};

                new HttpResponse(HttpStatusCode.OK)
                        .setContentTypeJSONHeader()
                        .setBody(true, "Success", payload)
                        .publish();
            } else {
                // Validation error
                new HttpResponse(HttpStatusCode.BAD_REQUEST)
                        .setContentTypeJSONHeader()
                        .setBody(false, "Invalid query parameters")
                        .publish();
            }

        } catch (MissingParametersException e) {
            new HttpResponse(HttpStatusCode.BAD_REQUEST)
                    .setContentTypeJSONHeader()
                    .setBody(false, "Required parameters are missing")
                    .publish();
        } catch (NumberFormatException e) {
            new HttpResponse(HttpStatusCode.BAD_REQUEST)
                    .setContentTypeJSONHeader()
                    .setBody(false, "Wrong type of provided parameters")
                    .publish();
        } catch (Exception e) {
            new HttpResponse(HttpStatusCode.INTERNAL_ERROR)
                    .setHeader("Content-Type", "text/plain; charset=utf-8")
                    .setBody("Internal error")
                    .publish();
        }
    }

    private static Long getExecutionTime() {
        long currentTime = System.nanoTime();
        return (currentTime - startTime) / 1000000;
    }
}
