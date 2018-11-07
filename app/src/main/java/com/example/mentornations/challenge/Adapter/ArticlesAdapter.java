package com.example.mentornations.challenge.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mentornations.challenge.Model.Article;
import com.example.mentornations.challenge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import it.sephiroth.android.library.tooltip.Tooltip;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.Myviewholder> {

    private ArrayList<Article> articleArrayList;
    private DetailsListner listner;
    private Context mContext;
    private SharedPreferences prefs;


    public interface DetailsListner {
        void showDetailsArticle(Article article);
    }

    public ArticlesAdapter(ArrayList<Article> articles, DetailsListner detailsListner, Context context) {
        this.articleArrayList = articles;
        this.listner = detailsListner;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_article, viewGroup, false);

        return new Myviewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, int i) {
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        final boolean isTooltip = prefs.getBoolean("istooltip", false);

        if (i == 0 && !isTooltip) {
            Tooltip.make(mContext,
                    new Tooltip.Builder(101)
                            .anchor(myviewholder.url, Tooltip.Gravity.BOTTOM)
                            .closePolicy(new Tooltip.ClosePolicy()
                                    .insidePolicy(true, false)
                                    .outsidePolicy(true, false), 3000)
                            .activateDelay(800)
                            .showDelay(300)
                            .text("To see more details, click on the article! ")
                            .maxWidth(500)
                            .withArrow(true)
                            .withOverlay(true)
                            .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                            .build()
            ).show();
        }
        final Article article = articleArrayList.get(i);
        myviewholder.title.setText(article.getTitle());
        myviewholder.date.setText(article.getDate());
        Picasso.get()
                .load(article.getUrl())
                .placeholder(R.drawable.url_placeholder)
                .error(R.drawable.url_placeholder_error)
                .resize(200, 200)
                .centerCrop()
                .into(myviewholder.url);
        myviewholder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.showDetailsArticle(article);
                if (!isTooltip) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("istooltip", true);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        public TextView title, date;
        public ImageView url;
        public RelativeLayout container;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.item_container);
            title = itemView.findViewById(R.id.title_textview);
            date = itemView.findViewById(R.id.date_textview);
            url = itemView.findViewById(R.id.url_imageview);
        }
    }
}
