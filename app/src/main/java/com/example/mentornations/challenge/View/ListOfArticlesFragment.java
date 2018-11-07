package com.example.mentornations.challenge.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentornations.challenge.Adapter.ArticlesAdapter;
import com.example.mentornations.challenge.Model.Article;
import com.example.mentornations.challenge.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ListOfArticlesFragment extends Fragment implements ArticlesAdapter.DetailsListner {
    private static final String ARG_PARAM1 = "param1";
    private RecyclerView recyclerView;
    private ArrayList<Article> mParam1;
    private ArticlesAdapter aAdapter;

    public ListOfArticlesFragment() {
    }

    public static ListOfArticlesFragment newInstance(ArrayList<Article> param1) {
        ListOfArticlesFragment fragment = new ListOfArticlesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_of_articles, container, false);
        recyclerView = v.findViewById(R.id.list_articles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        aAdapter = new ArticlesAdapter(mParam1, this, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(aAdapter);

        return v;
    }

    @Override
    public void showDetailsArticle(Article article) {
        ((MainActivity) getContext()).getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_out_right, R.anim.slide_in_right).replace(R.id.container, DetailsArticleFragment.newInstance(article)).addToBackStack(null).commit();

    }


}
