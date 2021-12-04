package com.example.movierating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.movierating.adapters.MovieRecyclerViewAdapter;
import com.example.movierating.adapters.onMovieListener;
import com.example.movierating.models.MovieModel;
import com.example.movierating.request.MovieApiClient;
import com.example.movierating.request.Servicey;
import com.example.movierating.response.MovieSearchResponse;
import com.example.movierating.utils.Credentials;
import com.example.movierating.utils.MovieApi;
import com.example.movierating.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements onMovieListener {
    //ViewModel
    private MovieListViewModel mMovieListViewModel;
    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private Boolean isPopular = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        mMovieListViewModel = new ViewModelProvider(MovieListActivity.this).get(MovieListViewModel.class);
        ConfigureRecyclerView();
        ObservePopularMovies();
        ObserveAnyChange();
        mMovieListViewModel.searchPopularMovieApi(1);
        searchMovieBar();



    }

    private void ObservePopularMovies() {
        mMovieListViewModel.getPopularMovies().observe(MovieListActivity.this, new Observer<List<MovieModel>>() {

            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Oberving for any data change
                if (movieModels != null) {
                    movieRecyclerViewAdapter.setmMovies(movieModels);
                    Log.v("Tag", "onChange: " + movieModels.get(0).getTitle());
                    for (MovieModel movieModel : movieModels) {
                        Log.v("Tag", "onChange: " + movieModel.getTitle());

                    }
                } else {
                    Toast.makeText(MovieListActivity.this, "No more pages", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    //Oberving any data change
    private void ObserveAnyChange() {
        mMovieListViewModel.getMovies().observe(MovieListActivity.this, new Observer<List<MovieModel>>() {

            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Oberving for any data change
                if (movieModels != null) {
                    movieRecyclerViewAdapter.setmMovies(movieModels);
                    for (MovieModel movieModel : movieModels) {
                        Log.v("Tag", "onChange: " + movieModel.getTitle());

                    }
                } else {
                    Toast.makeText(MovieListActivity.this, "No more pages", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void getRetrofitRespose() {
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
                Credentials.API_KEY, "Action"
                , 1);
        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.code() == 200) {
                    Log.v("response", response.body().toString());
                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                    for (MovieModel movie : movies) {
                        Log.v("release data", movie.getTitle());

                    }


                } else {

                    Log.v("Error", response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });


    }

    private void getRetrofitResponseAccordingToId() {
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieModel> reponseCall = movieApi.getMovie(75780, Credentials.API_KEY);
        reponseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    Log.v("tag", response.body().toString());
                    MovieModel movie = response.body();

                } else {
                    Toast.makeText(getBaseContext(), "asaError", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();


            }
        });
    }

    //intiallizing recyclerView & adding data to it
    private void ConfigureRecyclerView() {
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
               if(!recyclerView.canScrollHorizontally(1)){
                   mMovieListViewModel.searchNextPage();

               }
            }
        });

    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie",movieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void searchMovieBar() {
        final SearchView searchView = findViewById(R.id.search_view);
searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                movieRecyclerViewAdapter.setmMovies(new ArrayList<>());
                mMovieListViewModel.searchMovieApi(query, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular=false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMovieListViewModel.setMovies();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMovieListViewModel.setMovies();
    }
}