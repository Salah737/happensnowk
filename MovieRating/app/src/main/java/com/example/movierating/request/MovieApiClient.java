package com.example.movierating.request;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movierating.AppExecutors;
import com.example.movierating.MovieListActivity;
import com.example.movierating.models.MovieModel;
import com.example.movierating.response.MovieSearchResponse;
import com.example.movierating.utils.Credentials;
import com.example.movierating.utils.MovieApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    //Live data for search
    private MutableLiveData<List<MovieModel>> mMovies ;
    //Live data for popular movies
    private MutableLiveData<List<MovieModel>> mPopularMovies;
    private static MovieApiClient instance;
    //search movies
    private RetrieveMoviesRunnable retrieveMoviesRunnable;
    //popular movies
    private RetrievePopularMoviesRunnable retrievePopularMoviesRunnable;




    private MovieApiClient(){
        mMovies = new MutableLiveData<List<MovieModel>>();
        mPopularMovies = new MutableLiveData<>();
//        MovieApi movieApi=Servicey.getMovieApi();


    }
    //1-getting data from remote api
    public static MovieApiClient getInstance(){
        if(instance==null){
            instance= new  MovieApiClient();
        }

        return instance;
    }
    public LiveData<List<MovieModel>> getMovies(){

        return mMovies;
    }
    public LiveData<List<MovieModel>> getPopularMovies(){

        return mPopularMovies;
    }
    public void searchMovieApi(String query ,int pageNumber){

        if(retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;
        }
         retrieveMoviesRunnable =new RetrieveMoviesRunnable(query,pageNumber);

        final Future myHandler = AppExecutors.getInstance().netWorkIO().submit(retrieveMoviesRunnable);
        AppExecutors.getInstance().netWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler.cancel(true);

            }
        },3000, TimeUnit.MILLISECONDS);
    }
    public void SearchPopularMovies(int pageNumber){

        if(retrievePopularMoviesRunnable != null){
            retrievePopularMoviesRunnable = null;
        }
        retrievePopularMoviesRunnable =new RetrievePopularMoviesRunnable(pageNumber);

        final Future myHandler2 = AppExecutors.getInstance().netWorkIO().submit(retrievePopularMoviesRunnable);
        AppExecutors.getInstance().netWorkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Cancelling the retrofit call
                myHandler2.cancel(true);

            }
        },1000, TimeUnit.MILLISECONDS);
    }


    //Retrive data from api
    private class RetrieveMoviesRunnable implements Runnable{
        private String query;
        private int pageNumber;
        boolean cancelRequest;
        public RetrieveMoviesRunnable(String query ,int pageNumber){
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {
            try {
                Response response = getMovies(query,pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code()==200){
                    List<MovieModel> listMovies = new ArrayList<MovieModel>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber==1){
                        mMovies.postValue(listMovies);
                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(listMovies);
                        mMovies.postValue(currentMovies);
                    }

                }else {
                    String error = response.errorBody().string();
                    Log.v("Tag","Error "+error);
                    mMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }



            }
        //Search method or query
        private Call<MovieSearchResponse> getMovies (String query ,int pageNumber){
            return Servicey.getMovieApi().searchMovie(Credentials.API_KEY,
                    query,pageNumber);

        }
        private void setCancelRequest(){
            Log.v("Tag","Cancelling Search Request ");
            cancelRequest= true;
        }
    }

    private class RetrievePopularMoviesRunnable implements Runnable{
        private int pageNumber;
        boolean cancelRequest;
        public RetrievePopularMoviesRunnable(int pageNumber){
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {
            try {
                Response response2 = getPopularMovies(pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response2.code()==200){
                    List<MovieModel> listMovies = new ArrayList<MovieModel>(((MovieSearchResponse)response2.body()).getMovies());
                    if(pageNumber==1){
                        mPopularMovies.postValue(listMovies);
                    }else {
                        List<MovieModel> currentMovies = mPopularMovies.getValue();
                        currentMovies.addAll(listMovies);
                        mPopularMovies.postValue(currentMovies);
                    }

                }else {
                    String error = response2.errorBody().string();
                    Log.v("Tag","Error "+error);
                    mPopularMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mPopularMovies.postValue(null);
            }



        }
        //Search method or query
        private Call<MovieSearchResponse> getMovies (String query ,int pageNumber){
            return Servicey.getMovieApi().searchMovie(Credentials.API_KEY,
                    query,pageNumber);

        }
        private Call<MovieSearchResponse> getPopularMovies (int pageNumber){
            return Servicey.getMovieApi().getPopularMovies(Credentials.API_KEY
                                          ,pageNumber);

        }
        private void setCancelRequest(){
            Log.v("Tag","Cancelling Search Request ");
            cancelRequest= true;
        }
    }
    public void setMovies(){
        mMovies.postValue(null);
    }



}
