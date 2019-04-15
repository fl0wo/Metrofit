package com.example.metrofit2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author Florian
 * @date 15/04/2019
 *
 * @message THANKS FOR USING THIS LIBRARY!
 * U made me a lil happy :P
 */


/**
 * Class used at place of Retrofit
 * T is the type of PojoService
 * @param <T>
 */
public class Metrofit<T>{

    private final String BASE_URL;

    private Class<T> pojoService;

    private T pojoServiceClass;

    private Retrofit retrofit;

    private ParameterizedTask resultMethod,catchMethod;

    /**
     * Object used to handle any response
     */
    private ResponseHandler rh = new ResponseHandler<Object>() {
        @Override
        public void handleError(Throwable t) {
            catchMethod.setParam(t);
            catchMethod.run();
        }

        @Override
        public void handleResult(Object result) {
            resultMethod.setParam(result);
            resultMethod.run();
        }
    };

    private Observable<?> observable;


    /**
     * Constructor used to define Retroift class
     * @param pojoService .class of PojoService interface
     * @param BASE_URL base url of API request
     */
    public Metrofit(Class<T> pojoService,final String BASE_URL) {
        this.BASE_URL = BASE_URL;
        retrofit = builder();
        this.setPojoService(pojoService);
    }

    /**
     * Set the pojo service
     * @param pojoService class of PojoService interface
     */
    public void setPojoService(Class<T> pojoService) {
        this.pojoService = pojoService;
        this.pojoServiceClass = setExec();
    }

    /**
     * It builds the retrofit, u dont... :3
     * @return a Retrofit Class
     */
    private Retrofit builder(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit r = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return r;
    }

    /**
     * Return the PojoInterface from his class
     * @return PojoInterface
     */
    private T setExec() {
        return this.retrofit.create(pojoService);
    }

    /**
     * Method used to perform a particular HTTP request
     * @param observable this param needs to come from PojoService Interface
     * @return this
     */
    public Metrofit<T> doThis(Observable<?> observable){
        this.observable = observable;
        return this;
    }

    /**
     * Get the pojo class with witch u can perform HTTP request defined in your PojoService interface
     * @return
     */
    public T pojo(){ return pojoServiceClass; }

    /**
     * Allow to define how to handle a error or a success request
     * @param rh The ResponseHandler class
     * @return this
     */
    public Metrofit<T> handle(ResponseHandler<?> rh){
        this.rh = rh;
        return this;
    }

    /**
     * Attach the ResponseHandler created to the observable, so when the request is done
     * with rxJava it will handle the result.
     * It's subscribed On .computation Scheduler and observed on the main Thread of Android
     * If you want change those settings, even if those are the best, just make your own bind method
     * copying this one.
     */
    public void bind(){
        observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rh::handleResult, rh::handleError);
    }

    /**
     * Method used to define what to do if a HTTP request was executed successfully
     * @param ptask The Task to execute if so
     * @return this
     */
    public Metrofit<T> then(Task<?> ptask) {
        resultMethod = new ParameterizedTask(ptask);
        return this;
    }

    /**
     * Method used to define what to do if a HTTP request was NOT executed successfully
     * @param ptask The Task to execute if so
     * @return this
     */
    public Metrofit<T> handleError(Task<?> ptask) {
        catchMethod = new ParameterizedTask(ptask);
        return this;
    }

}