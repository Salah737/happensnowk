package com.example.movierating.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movierating.models.MovieModel;
import com.example.movierating.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;


    public MovieListViewModel (){
         movieRepository = MovieRepository.getInstance();

    }
    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }
    public LiveData<List<MovieModel>> getPopularMovies(){
        return movieRepository.getPopularMovies();
    }
    //3- getting the data from repository
    public void searchMovieApi(String query,int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }
    public void searchPopularMovieApi(int pageNumber){
        movieRepository.searchPopularMovieApi(pageNumber);
    }
    public void setMovies(){
        movieRepository.setMovies();
    }


    public void searchNextPage(){
        movieRepository.searchNextPage();
    }
}
