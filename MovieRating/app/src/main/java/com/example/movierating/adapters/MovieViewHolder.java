package com.example.movierating.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movierating.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView imageView;
    RatingBar ratingBar;

    onMovieListener onMovieListener;
    public MovieViewHolder(@NonNull View itemView,onMovieListener onMovieListenr) {
        super(itemView);
        this.onMovieListener = onMovieListenr;

         imageView = itemView.findViewById(R.id.movie_image);
         ratingBar = itemView.findViewById(R.id.movie_rating_bar);
         itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
