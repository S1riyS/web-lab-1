package ru.s1riys.web.lab1.handlers.main;

import com.fastcgi.FCGIInterface;
import com.fastcgi.FCGIRequest;
import ru.s1riys.web.lab1.HitChecker;
import ru.s1riys.web.lab1.Validator;
import ru.s1riys.web.lab1.exceptions.MissingParametersException;
import ru.s1riys.web.lab1.handlers.IHandler;
import ru.s1riys.web.lab1.network.HttpResponse;
import ru.s1riys.web.lab1.network.HttpStatusCode;
import ru.s1riys.web.lab1.network.QueryParamsManager;

import java.util.HashMap;
import java.util.List;

public class Handler implements IHandler {
    private static final long startTime = System.nanoTime();

    @Override
    public void process(FCGIRequest request) {
        try {
            // Retrieving and validating query parameters
            String queryString = FCGIInterface.request.params.getProperty("QUERY_STRING");
            HashMap<String, String> queryParams = QueryParamsManager.parse(queryString);
            Boolean isQueryParamsConsistent = QueryParamsManager.checkConsistency(
                    queryParams,
                    List.of("x", "y", "r")
            );
            if (!isQueryParamsConsistent) throw new MissingParametersException();

            Validator validator = new Validator(queryParams.get("x"), queryParams.get("y"), queryParams.get("r"));
            if (validator.check()) {
                HitChecker hitChecker = new HitChecker(queryParams.get("x"), queryParams.get("y"), queryParams.get("r"));

                // Collecting payload's data
                Boolean hit = hitChecker.getResult();
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
