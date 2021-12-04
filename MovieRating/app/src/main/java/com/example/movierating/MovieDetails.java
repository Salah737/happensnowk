package com.example.movierating;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movierating.models.MovieModel;

public class MovieDetails extends AppCompatActivity {
    //widgets
    private ImageView imageViewDetails;
    private TextView titleDetails,descriptionDetaills;
    private RatingBar ratingBarDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        imageViewDetails = findViewById(R.id.imageView_details);
        titleDetails = findViewById(R.id.textView_title_details);
        descriptionDetaills= findViewById(R.id.textView2_description_details);
        ratingBarDetails = findViewById(R.id.ratingBar_details);
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        if(getIntent().hasExtra("movie")){
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            titleDetails.setText(movieModel.getTitle());
            descriptionDetaills.setText(movieModel.getOverview());
            ratingBarDetails.setRating(movieModel.getVote_average()/2);
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/"+movieModel.getPoster_path())
                    .into(imageViewDetails);

        }
    }
}