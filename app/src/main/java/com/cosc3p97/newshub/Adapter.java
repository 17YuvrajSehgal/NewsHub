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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Context context;
    private List<Model> modelList;
    private int lastPosition = -1;

    public Adapter(Context context, List<Model> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = modelList.get(position);

        // Use default values for nulls
        holder.headlines.setText(model.getTitle() != null ? model.getTitle() : "Headline unavailable");
        holder.mainNews.setText(model.getDescription() != null ? model.getDescription() : "Description unavailable");
        holder.author.setText(model.getAuthor() != null ? model.getAuthor() : "Unknown author");
        holder.publishedAt.setText(model.getPublishedAt() != null ? model.getPublishedAt() : "Date unknown");

        // Load image with placeholder and error fallback
        Glide.with(context)
                .load(model.getUrlToImage())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_shape)
                .into(holder.imageView);

        // Handle click events
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ReadNewsActivity.class);
            intent.putExtra("URL", model.getUrl());
            context.startActivity(intent);
        });

        // Set animation
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return modelList != null ? modelList.size() : 0;
    }

    /**
     * Animates item if it's displayed for the first time.
     */
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    /**
     * Updates the adapter's data with a DiffUtil for better performance.
     */
    public void updateList(List<Model> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return modelList != null ? modelList.size() : 0;
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                // Compare based on URL, assuming URL is unique for each news item
                return modelList.get(oldItemPosition).getUrl().equals(newList.get(newItemPosition).getUrl());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                // Compare contents of the items
                return modelList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }
        });

        modelList = newList;
        diffResult.dispatchUpdatesTo(this);
    }


    /**
     * ViewHolder: Holds references to the views for each item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
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
