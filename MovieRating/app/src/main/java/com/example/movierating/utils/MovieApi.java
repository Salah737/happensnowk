package com.example.movierating.utils;

import com.example.movierating.models.MovieModel;
import com.example.movierating.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
  //Search for moviescher
    //https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Rea

    @GET("3/search/movie")
    Call<MovieSearchResponse> searchMovie(@Query("api_key")String key
                                           ,@Query("query")String query,
                                           @Query("page")int page);

    @GET("3/movie/{id}?")
    Call<MovieModel> getMovie(@Path("id" )int movie_id
    ,@Query("api_key")String api_key);

    //Get Popular Movie
    // https://api.themoviedb.org/3/movie/popular ?api_key=<<api_key>>&language=en-US&page=1
    @GET("3/movie/popular")
    Call<MovieSearchResponse> getPopularMovies(@Query("api_key")String key
                                        ,@Query("page")int page);


}
