package com.example.movierating.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movierating.R;
import com.example.movierating.models.MovieModel;
import com.example.movierating.utils.Credentials;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MovieModel> mMovies;
    private onMovieListener onMovieListener;
    private static final int DISPALY_POPULAR=1;
    private static final int DISPLAY_SEARCH= 2;



    public MovieRecyclerViewAdapter(onMovieListener onMovieListener){
        this.onMovieListener= onMovieListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if(viewType==DISPLAY_SEARCH){
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
             return new MovieViewHolder(view,onMovieListener);
        }else {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_movie,parent,false);
             return  new PopularMovieViewHolder(view,onMovieListener);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType==DISPLAY_SEARCH){
            ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(position).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"+
                            mMovies.get(position).getPoster_path())
                    .into(((MovieViewHolder)holder).imageView);
        }else {
            ((PopularMovieViewHolder)holder).ratingBar2.setRating((mMovies.get(position).getVote_average())/2);

            Glide.with(holder.itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500/"+
                            mMovies.get(position).getPoster_path())
                    .into(((PopularMovieViewHolder)holder).imageView2);

        }



    }

    @Override
    public int getItemCount() {
        if(mMovies!= null) {
            return mMovies.size();
        }
        return 0;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }
    public MovieModel getSelectedMovie(int position){
        if(mMovies!=null){
            if(mMovies.size()>0){
                return mMovies.get(position);
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(Credentials.POPULAR) {
            return DISPALY_POPULAR;
        }else {
            return DISPLAY_SEARCH;
        }
    }
}
