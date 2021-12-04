package com.example.movierating.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movierating.R;

public class PopularMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView2;
    RatingBar ratingBar2;

    onMovieListener onMovieListener;
    public PopularMovieViewHolder(@NonNull View itemView, onMovieListener onMovieListenr) {
        super(itemView);
        this.onMovieListener = onMovieListenr;

         imageView2 = itemView.findViewById(R.id.movie_image_popular);
         ratingBar2 = itemView.findViewById(R.id.movie_rating_bar_popular);
         itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
