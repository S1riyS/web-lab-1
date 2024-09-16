package ru.s1riys.web.lab1;

import com.fastcgi.FCGIInterface;
import ru.s1riys.web.lab1.exceptions.MissingParametersException;

import java.util.HashMap;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            try {
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
                    // Checking hit
                    Boolean hit = Checker.hit(x, y, r);
                    System.out.println(renderResponse(hit));
                } else {
                    // Validation error
                    System.out.println(renderError("Invalid query parameters"));
                }

            } catch (MissingParametersException e) {
                System.out.println(renderError("Required query parameters are missing"));
            } catch (NumberFormatException e) {
                System.out.println(renderError("Wrong type of query parameters"));
            } catch (Exception e) {
                System.out.println(renderError(e.getMessage()));
            }
        }
    }

    private static String renderError(String msg) {
        return """
                Content-Type: application/json charset=utf-8
                
                {"error":"%s", "success": false}
                """.formatted(msg);
    }

    private static String renderResponse(boolean hit) {
        return """
                Content-Type: application/json; charset=utf-8
                
                
                {"hit": %b, "success": true}
                """.formatted(hit);
    }
}
