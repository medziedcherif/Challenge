package com.example.mentornations.challenge.Controller;

import com.example.mentornations.challenge.Model.Article;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleInterface {

    @GET("apod?api_key=WGFry1kwkw8ZloxavIgonTuEjthJZsQRl8EeOzH7")
    public Call<ArrayList<Article>> getArticles(
            @Query("start_date") String start_date,
            @Query("end_date") String end_date);
}
