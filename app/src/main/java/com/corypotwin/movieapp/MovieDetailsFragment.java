package com.corypotwin.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.corypotwin.movieapp.favdata.FavoriteMovieContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    ArrayList<String> movieDetailsInHere;
    private String movieTitle;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            populateDetailChildViews(intent, rootView);
        }

        return rootView;
    }

    /**
     * Populates the Child Views in the Movie Details Fragment
     *
     * @param intent - Intent to pull data passed from previous activity
     * @param rootView - Fragment View used to find the appropriate child view from
     */
    private void populateDetailChildViews(Intent intent, View rootView){

        movieDetailsInHere = intent.getStringArrayListExtra(Intent.EXTRA_TEXT);

        // Title
        movieTitle = movieDetailsInHere.get(0);
        ((TextView) rootView.findViewById(R.id.title)).setText(movieTitle);

        // Description
        if(movieDetailsInHere.get(1) == null){
            ((TextView) rootView.findViewById(R.id.description))
                    .setText(R.string.no_description);
        } else {
            ((TextView) rootView.findViewById(R.id.description))
                    .setText(movieDetailsInHere.get(1));
        }

        // Ratings
        if(movieDetailsInHere.get(2).equals("0.0")){
            ((TextView) rootView.findViewById(R.id.rating))
                    .setText(R.string.no_rating);
        } else {
            ((TextView) rootView.findViewById(R.id.rating))
                    .setText("Rating: " + movieDetailsInHere.get(2) + "/10");
        }

        // Release Date
        if(movieDetailsInHere.get(3) == null) {
            ((TextView) rootView.findViewById(R.id.release_date))
                    .setText(R.string.no_release_date);

        } else {
            ((TextView) rootView.findViewById(R.id.release_date))
                    .setText("Release Date:\n" + movieDetailsInHere.get(3));
        }

        // Poster
        ImageView imgView = (ImageView) rootView.findViewById(R.id.movie_poster_detail);
        imgView.setAdjustViewBounds(true);
        Picasso.with(getActivity())
                .load(movieDetailsInHere.get(4))
                .placeholder(R.drawable.no_image_available_black)
                .error(R.drawable.no_image_available_black)
                .into(imgView);

/*        // Favorite
        RatingBar favoriteStar = (RatingBar) rootView.findViewById(R.id.favorite_star);
        if(setFavorite()){
            favoriteStar.setRating(1);
        }*/
    }

    public boolean setFavorite(){
        String[] selectionArgs = {movieTitle};

        Cursor favoriteMovie = getActivity().getContentResolver()
                .query(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                        null,
                        FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE,
                        selectionArgs,
                        null);

        return favoriteMovie.moveToNext();
    }

    public void favoriteClick(RatingBar v){
        if(v.getRating() == 1){
            getActivity().getContentResolver()
                    .delete(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                            null,
                            null);
        } else {
            //  TODO actually make these values so they get passed into the db
            ContentValues values = new ContentValues();
            values.put(FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE, movieTitle);
            getActivity().getContentResolver()
                    .insert(FavoriteMovieContract.FavoriteMovieEntry.CONTENT_URI,
                            values);
        }

    }

}
