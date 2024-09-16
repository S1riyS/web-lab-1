package ru.s1riys.web.lab1;

import java.util.HashMap;

public class QueryParser {
    public static HashMap<String, String> parse(String queryString) {
        HashMap<String, String> params = new HashMap<String, String>();

        for (String expression : queryString.split("&")) {
            String[] keyValuePair = expression.split("=");
            if (keyValuePair.length > 1) {
                params.put(keyValuePair[0], keyValuePair[1]);
            } else {
                params.put(keyValuePair[0], "");
            }
        }

        return params;
    }
}
