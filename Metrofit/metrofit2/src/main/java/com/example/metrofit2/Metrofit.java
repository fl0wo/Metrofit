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

public class Metrofit<T>{

    private final String BASE_URL;

    private Class<T> pojoService;
    private T pojoServiceClass;

    private Retrofit retrofit;

    private ParameterizedTask resultMethod,catchMethod;

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

    public Metrofit(Class<T> pojoService,final String BASE_URL) {
        this.BASE_URL = BASE_URL;
        retrofit = builder();
        this.setPojoService(pojoService);
    }

    public void setPojoService(Class<T> pojoService) {
        this.pojoService = pojoService;
        this.pojoServiceClass = setExec();
    }

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

    private T setExec() {
        return this.retrofit.create(pojoService);
    }

    public Metrofit<T> doThis(Observable<?> observable){
        this.observable = observable;
        return this;
    }

    public T pojo(){ return pojoServiceClass; }

    public Metrofit<T> handle(ResponseHandler<?> rh){
        this.rh = rh;
        return this;
    }

    public void bind(){
        observable
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rh::handleResult, rh::handleError);
    }

    public Metrofit<T> then(Task<?> ptask) {
        resultMethod = new ParameterizedTask(ptask);
        return this;
    }

    public Metrofit<T> handleError(Task<?> ptask) {
        catchMethod = new ParameterizedTask(ptask);
        return this;
    }

}