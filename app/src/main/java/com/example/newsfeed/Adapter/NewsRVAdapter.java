package com.example.newsfeed.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.Articles;
import com.example.newsfeed.NewsDetailsActivity;
import com.example.newsfeed.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_rv_item,parent,false);
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        Articles articles=articlesArrayList.get(position);
        holder.subtitleTV.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
//        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        if ( articles.getUrlToImage()==null || articles.getUrlToImage().isEmpty()) {
            holder.newsIV.setImageResource(R.drawable.ic_launcher_foreground);
        } else{
            Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        }
        holder.itemView.setOnClickListener(view -> {
            Intent i=  new Intent(context, NewsDetailsActivity.class);
            i.putExtra("title", articles.getTitle());
            i.putExtra("content", articles.getContent());
            i.putExtra("desc", articles.getDescription());
            i.putExtra("image", articles.getUrlToImage());
            i.putExtra("url", articles.getUrl());
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTV, subtitleTV;
        private ImageView newsIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.tv_news);
            subtitleTV=itemView.findViewById(R.id.tv_subheading);
            newsIV=itemView.findViewById(R.id.iv_news);
        }
    }
}
