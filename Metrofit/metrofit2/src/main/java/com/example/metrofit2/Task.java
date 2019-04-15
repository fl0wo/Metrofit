package com.example.metrofit2;

/**
 * Interface used to execute any type of istruction
 * @param <R> type of argument Object
 */
public interface Task<R> {
    /**
     * Method called to exec a method that takes as argument a R object
     * @param result
     */
    void exec(R result);
}
