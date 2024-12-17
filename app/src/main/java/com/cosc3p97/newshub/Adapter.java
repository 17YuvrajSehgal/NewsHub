package com.cosc3p97.newshub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context c;
    ArrayList<Model> modelArrayList;
    private int lastPosition = -1;

    public Adapter(Context c, ArrayList<Model> modelArrayList) {
        this.c = c;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        holder.headlines.setText(modelArrayList.get(position).getTitle());
        holder.mainNews.setText(modelArrayList.get(position).getDescription());
        holder.author.setText(modelArrayList.get(position).getAuthor());
        holder.publishedAt.setText(modelArrayList.get(position).getPublishedAt() + "Source" + modelArrayList.get(position).getAuthor());
        setAnimation(holder.itemView, position);
        Glide.with(c).load(modelArrayList.get(position).getUrlToImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(c,ReadNewsActivity.class);
                i.putExtra("URL", modelArrayList.get(position).getUrl());
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(c,R.anim.slide_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView headlines, mainNews, author, publishedAt;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headlines = itemView.findViewById(R.id.headline);
            mainNews = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.newsImageView);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
        }
    }
}
