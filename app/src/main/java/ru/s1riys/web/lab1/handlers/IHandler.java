package ru.s1riys.web.lab1.handlers;

import com.fastcgi.FCGIRequest;

public interface IHandler {
    void process(FCGIRequest request);
}
