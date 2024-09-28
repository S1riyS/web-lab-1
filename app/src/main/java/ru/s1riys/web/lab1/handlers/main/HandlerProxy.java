package ru.s1riys.web.lab1.handlers.main;

import com.fastcgi.FCGIInterface;
import com.fastcgi.FCGIRequest;
import ru.s1riys.web.lab1.handlers.IHandler;
import ru.s1riys.web.lab1.network.HttpResponse;
import ru.s1riys.web.lab1.network.HttpStatusCode;

public class HandlerProxy implements IHandler {
    private static Handler handler;
    private final String allowedMethod;

    public HandlerProxy(String allowedMethod) {
        this.allowedMethod = allowedMethod;
        handler = new Handler();
    }

    @Override
    public void process(FCGIRequest request) {
        // Request method must be GET
        String method = FCGIInterface.request.params.getProperty("REQUEST_METHOD");
        if (!method.equals(this.allowedMethod)) {
            String message = "Only %s method is allowed".formatted(this.allowedMethod);
            new HttpResponse(HttpStatusCode.METHOD_NOT_ALLOWED)
                    .setContentTypeJSONHeader()
                    .setBody(false, message)
                    .publish();
            return;
        }

        handler.process(request);
    }
}
