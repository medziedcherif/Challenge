package com.example.mentornations.challenge.View;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mentornations.challenge.Controller.ArticleInterface;
import com.example.mentornations.challenge.Controller.GitHubServiceGenerator;
import com.example.mentornations.challenge.Model.Article;
import com.example.mentornations.challenge.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Article> retrieveList = new ArrayList<>();
    private ArrayList<Article> articleList = new ArrayList<>();
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private RelativeLayout loader;
    private AVLoadingIndicatorView indicatorView;
    private TextView loaderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loader = findViewById(R.id.loader);
        indicatorView = findViewById(R.id.avi);
        loaderText = findViewById(R.id.loader_textview);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        boolean isFirst = prefs.getBoolean("isfirst", false);
        if (!isFirst) {
            loader.setVisibility(View.VISIBLE);
            editor.putBoolean("Isfirst", false);
            editor.apply();
            RetrieveData();
        } else {
            replaceFragment();
        }


    }

    public void saveArrayList(ArrayList<Article> list, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<Article> getArrayList(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<Article>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void RetrieveData() {


        ArticleInterface service
                = GitHubServiceGenerator.createService(ArticleInterface.class);

        final Call<ArrayList<Article>> articles = service.getArticles("2018-06-01", "2018-06-15");
        articles.enqueue(new Callback<ArrayList<Article>>() {
            @Override
            public void onResponse(Call<ArrayList<Article>> call, Response<ArrayList<Article>> response) {
                if (response.code() == 200) {
                    loader.setVisibility(View.GONE);
                    retrieveList = response.body();
                    editor.putBoolean("isfirst", true);
                    editor.apply();
                    saveArrayList(retrieveList, "articles");
                    replaceFragment();
                } else {
                    indicatorView.setVisibility(View.GONE);
                    loaderText.setText(R.string.sorry);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Article>> call, Throwable t) {
                indicatorView.setVisibility(View.GONE);
                loaderText.setText(R.string.sorry);
            }
        });

    }

    private void replaceFragment() {
        articleList = getArrayList("articles");
        getSupportFragmentManager().beginTransaction().replace(R.id.container, ListOfArticlesFragment.newInstance(articleList)).commit();
    }

}
