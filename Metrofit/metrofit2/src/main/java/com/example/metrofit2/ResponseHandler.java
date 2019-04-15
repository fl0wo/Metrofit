package com.example.metrofit2;

/**
 * Interface used to handle HTTP responses
 * @param <T>
 */
public interface ResponseHandler<T> {

    /**
     * Method used to catch any kind of error
     * @param t
     */
    void handleError(Throwable t);

    /**
     * Method used to handle any type of Result
     * @param result
     */
    void handleResult(T result);
}