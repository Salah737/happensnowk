package com.example.movierating.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movierating.models.MovieModel;
import com.example.movierating.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;
    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();

        }
        return instance;
    }
    private MovieRepository(){
        movieApiClient= movieApiClient.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();
    }
    public LiveData<List<MovieModel>> getPopularMovies(){
        return movieApiClient.getPopularMovies();
    }


    //2- getting data from MovieApiClient
    public void searchMovieApi(String query , int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMovieApi(query,pageNumber);

    }
    public void searchPopularMovieApi(int pageNumber){
//        mPageNumber = pageNumber;
        movieApiClient.SearchPopularMovies(pageNumber);

    }
    public void setMovies(){
        movieApiClient.setMovies();
    }
    public void searchNextPage(){
        searchMovieApi(mQuery,mPageNumber+1);
    }


}
