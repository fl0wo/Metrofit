package com.example.metrofit2;

/**
 * Class used to contain a Task and a Parameter
 */
public class ParameterizedTask implements Runnable {

    private Object result;

    private Task taskExecutor;

    /**
     * Constructor setting up the task will be called
     * @param taskExecutor The task this class will run
     */
    public ParameterizedTask(Task taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void run() {
        taskExecutor.exec(result);
    }

    /**
     * Set up the parameter the task will use in execution
     * @param result The parameter result
     */
    public void setParam(Object result) {
        this.result = result;
    }
}
