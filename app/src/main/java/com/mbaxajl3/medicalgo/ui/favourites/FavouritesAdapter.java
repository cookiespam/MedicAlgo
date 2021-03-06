package com.mbaxajl3.medicalgo.ui.favourites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.mbaxajl3.medicalgo.Factory;
import com.mbaxajl3.medicalgo.R;
import com.mbaxajl3.medicalgo.controllers.FavouritesController;
import com.mbaxajl3.medicalgo.controllers.JSONController;
import com.mbaxajl3.medicalgo.models.AlgorithmMetadata;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    private final String TAG = "FavouritesAdapter";
    private List<AlgorithmMetadata> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private JSONController jsonController;
    private Context context;
    private FavouritesController favouritesController;

    // data is passed into the constructor
    FavouritesAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.favouritesController = Factory.getFavouritesController();
        this.mData = favouritesController.getFavourites() == null ? new ArrayList<>() : favouritesController.getFavourites();
        this.jsonController = Factory.getJSONController();
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.algorithms_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AlgorithmMetadata algorithm = mData.get(position);
        holder.tvAlgorithm.setText(algorithm.getAlgorithmName());
        holder.subtitleTvAlgorithm.setText(jsonController.getCategoryById(algorithm.getCategoryId()).getName());

        if (favouritesController.isAlgorithmInFavourites(algorithm)) {
            holder.likeButton.setLiked(true);
        } else {
            holder.likeButton.setLiked(false);
        }

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                favouritesController.like(algorithm);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                favouritesController.unlike(algorithm);
            }
        });

        if (!algorithm.getImagePath().equals(""))
            Glide.with(context)
                    .load(algorithm.getImagePath())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivAlgorithm);
        else
            holder.ivAlgorithm.setVisibility(View.GONE);

    }
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvAlgorithm;
        LikeButton likeButton;
        ImageView ivAlgorithm;
        TextView subtitleTvAlgorithm;

        ViewHolder(View itemView) {
            super(itemView);
            tvAlgorithm = itemView.findViewById(R.id.tvAlgorithm);
            likeButton = itemView.findViewById(R.id.favourite_button);
            ivAlgorithm = itemView.findViewById(R.id.ivAlgorithm);
            subtitleTvAlgorithm = itemView.findViewById(R.id.subtitleTvAlgorithm);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener == null)
                return;

            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    AlgorithmMetadata getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
