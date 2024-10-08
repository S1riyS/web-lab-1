package ru.s1riys.web.lab1;

import com.fastcgi.FCGIInterface;
import com.fastcgi.FCGIRequest;
import ru.s1riys.web.lab1.handlers.main.HandlerProxy;

public class Server {

    public static void main(String[] args) {
        var fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            handleRequest(FCGIInterface.request);
        }
        System.err.println("FCGI server has been stopped");
    }

    private static void handleRequest(FCGIRequest request) {
        HandlerProxy handler = new HandlerProxy("GET");
        handler.process(request);
    }
}
