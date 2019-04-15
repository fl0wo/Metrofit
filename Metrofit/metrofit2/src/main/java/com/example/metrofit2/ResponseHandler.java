package com.example.metrofit2;

public interface ResponseHandler<T> {

    void handleError(Throwable t);

    void handleResult(T result);
}