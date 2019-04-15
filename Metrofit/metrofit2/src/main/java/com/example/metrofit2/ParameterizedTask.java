package com.example.metrofit2;

public class ParameterizedTask implements Runnable {

    private Object result;

    private Task taskExecutor;

    public ParameterizedTask(Task taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void run() {
        taskExecutor.exec(result);
    }

    public void setParam(Object result) {
        this.result = result;
    }
}
