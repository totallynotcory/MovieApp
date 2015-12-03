package com.corypotwin.movieapp.customdata;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.corypotwin.movieapp.R;
import com.corypotwin.movieapp.customdata.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates an ImageAdapter to hold the details of GridViews
 */
public class ImageAdapter extends BaseAdapter  {
    private final String LOG_TAG = "ImageAdapter";
    private Context mContext;
    private List<Movie> movieResults;

    public ImageAdapter(Context c) {
        mContext = c;
        movieResults = new ArrayList<>();
    }

    public int getCount() {
        return movieResults.size();
    }

    public String getItem(int position) {
        return movieResults.get(position).getmPosterUrl();
    }

    public Movie getFullSingleMovieDetails(int position){
        return movieResults.get(position);
    }

    public List<Movie> getMovieResults(){
        return movieResults;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void add (Movie singleMovieData){
        movieResults.add(singleMovieData);
    }

    public void clear (){
        if (movieResults != null){
            movieResults.clear();
        }
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        View v;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // if it's not recycled, initialize some attributes
            v = inflater.inflate(R.layout.grid_item, null);
            imageView = (ImageView) v.findViewById(R.id.movie_poster_image_view);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String transitionName = mContext.getString(R.string.transition_movie) + position;
            imageView.setTransitionName(transitionName);
        }

        String url = getItem(position);
        Picasso.with(mContext).
                load(url).
                placeholder(R.drawable.no_image_available_white).
                error(R.drawable.no_image_available_white).
                into(imageView);
        return imageView;
    }

}
