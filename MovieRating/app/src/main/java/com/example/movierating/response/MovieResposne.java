package com.example.movierating.response;

import com.example.movierating.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Single movie resquest
public class MovieResposne {
    @SerializedName("results")
    @Expose
    private MovieModel movie;

    private MovieModel getMovie(){
        return movie;
    }

    @Override
    public String toString() {
        return "MovieResposne{" +
                "movie=" + movie +
                '}';
    }
}
