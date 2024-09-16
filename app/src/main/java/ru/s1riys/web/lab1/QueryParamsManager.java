package ru.s1riys.web.lab1;

import java.util.HashMap;
import java.util.List;

public class QueryParamsManager {
    public static Boolean checkConsistency(HashMap<String, String> data, List<String> requiredKeys) {
        for (String key : requiredKeys) {
            if (data.get(key) == null) return false;
        }
        return true;
    }

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
