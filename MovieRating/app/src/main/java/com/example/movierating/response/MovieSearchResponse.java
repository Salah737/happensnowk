package com.example.movierating.response;

import com.example.movierating.models.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//This class for getting multiple movies(popular movies).
public class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose()
    private int total_count;

    @SerializedName("results")
    @Expose()
    private List<MovieModel> popularMovies ;


    public int getTotal_count(){
        return total_count;
    }
    public List<MovieModel> getMovies(){
        return popularMovies;
    }

    @Override
    public String toString() {
        return "MovieSearchResponse{" +
                "total_count=" + total_count +
                ", popularMovies=" + popularMovies +
                '}';
    }
}
