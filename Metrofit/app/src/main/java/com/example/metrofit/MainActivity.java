package com.example.metrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.metrofit2.Metrofit;
import com.example.metrofit2.ResponseHandler;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Metrofit<PojoService> m = new Metrofit(PojoService.class,PojoService.BASE_URL);

        m.doThis(m.pojo().getAll())
                .then(result -> {

                        List<Response> responses = (List<Response>) result;

                        int i =0;
                        for(Response r : responses) {
                            System.out.println("Obj n " + (i++) + " : " + r);
                        }

                    })
                .handleError(result -> {
                    System.err.println(((Throwable) result).getMessage());
                })
                .bind();

        m.doThis(m.pojo().getById(2))
                .then(result -> {

                    Response response = (Response) result;

                    System.out.println("Obj n " + 2 + " : " + response);

                })
                .handleError(result -> {
                    System.err.println(((Throwable) result).getMessage());
                })
                .bind();

        m.doThis(m.pojo().getById(3))
                .handle(new ResponseHandler<Response>() {
                    @Override
                    public void handleError(Throwable t) {
                        System.err.println(t.getMessage());
                    }

                    @Override
                    public void handleResult(Response result) {
                        System.out.println("Obj n " + 2 + " : " + result);
                    }
                })
                .bind();

    }
}

/**
 * Define here the HTTP methods u need, if you dont know how, go study retrofit
 */
interface PojoService {

    public final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @GET("/todos")
    Observable<List<Response>> getAll();

    @GET("/todos/{id}")
    Observable<Response> getById(@Path("id") int id);

}

// GENERATE THIS WITH A JSON 2 POJO WEB SITE!
class Response {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("completed")
    @Expose
    private Boolean completed;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Response{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", completed=" + completed +
                '}';
    }
}

