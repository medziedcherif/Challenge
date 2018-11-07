package com.example.mentornations.challenge.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mentornations.challenge.Model.Article;
import com.example.mentornations.challenge.R;
import com.squareup.picasso.Picasso;

public class DetailsArticleFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private TextView title, date, explanation, copyright;
    private ImageView url;
    // TODO: Rename and change types of parameters
    private Article mParam1;


    public DetailsArticleFragment() {
        // Required empty public constructor
    }

    public static DetailsArticleFragment newInstance(Article param1) {
        DetailsArticleFragment fragment = new DetailsArticleFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details_article, container, false);
        url = v.findViewById(R.id.url_details_imageview);
        title = v.findViewById(R.id.title_details_textview);
        date = v.findViewById(R.id.date_details_textview);
        explanation = v.findViewById(R.id.explanation_textview);
        copyright = v.findViewById(R.id.copyright_textview);

        Picasso.get()
                .load(mParam1.getUrl())
                .placeholder(R.drawable.url_placeholder)
                .error(R.drawable.url_placeholder_error).fit().centerCrop()
                .into(url);
        title.setText(mParam1.getTitle());
        date.setText(mParam1.getDate());
        explanation.setText(mParam1.getExplanation());
        copyright.setText(mParam1.getCopyright());
        return v;
    }

}
