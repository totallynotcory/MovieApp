package com.corypotwin.movieapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.corypotwin.movieapp.asyncfetchers.DetailDataFetcher;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;
import com.corypotwin.movieapp.provider.favorites.FavoritesContentValues;
import com.corypotwin.movieapp.provider.favorites.FavoritesSelection;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private String firstYoutubeTrailerToShare;
    private ShareActionProvider mShareActionProvider;
    private final String LOG_TAG = "Movie Details Fragment";
    // Stores details of movie to show in this fragment
    private Movie movieDetails;

    public MovieDetailsFragment() {
        setHasOptionsMenu(true);
        firstYoutubeTrailerToShare = "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Bundle args = getArguments();
        if(args != null){
            movieDetails = args.getParcelable(MainActivity.INDIVIDUAL_MOVIE_TAG);
            updateReviewsAndTrailers(rootView);
            populateDetailChildViews(rootView);
        } else {
           return null;
        }

        return rootView;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate menu resource file.

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if (firstYoutubeTrailerToShare != null && !firstYoutubeTrailerToShare.isEmpty()){
            mShareActionProvider.setShareIntent(createShareTrailerIntent());
        }
    }

    /**
     * Launches an async task to retrieve Trailers and Reviews from the server.
     *
     * @param rootView - Fragment View used to find appropriate children views
     */
    public void updateReviewsAndTrailers(View rootView) {
        try {
        DetailDataFetcher ddFetcher = new DetailDataFetcher(
                movieDetails.getmId(),
                movieDetails.getmReviewsUrl(),
                movieDetails.getmTrailerUrl(),
                getActivity(),
                rootView,
                this);
        ddFetcher.execute();
        } catch(MalformedURLException e){
            Log.e(LOG_TAG, "updateReviewsAndTrailers had a malformed URL");
        }

    }

    /**
     * Populates the Child Views in the Movie Details Fragment
     *
     * @param rootView - Fragment View used to find the appropriate child view from
     */
    private void populateDetailChildViews(View rootView){

        // Title
        ((TextView) rootView.findViewById(R.id.title)).setText(movieDetails.getmTitle());

        // Description
        if(movieDetails.getmDescription().equals("null")){
            ((TextView) rootView.findViewById(R.id.description))
                    .setText(R.string.no_description);
        } else {
            ((TextView) rootView.findViewById(R.id.description))
                    .setText(movieDetails.getmDescription());
        }

        // Ratings
        if(movieDetails.getmRating().equals("0.0")){
            ((TextView) rootView.findViewById(R.id.rating))
                    .setText(R.string.no_rating);
        } else {
            ((TextView) rootView.findViewById(R.id.rating))
                    .setText("Rating: " + movieDetails.getmRating() + "/10");
        }

        // Release Date
        if(movieDetails.getmReleaseDate() == null) {
            ((TextView) rootView.findViewById(R.id.release_date))
                    .setText(R.string.no_release_date);

        } else {
            ((TextView) rootView.findViewById(R.id.release_date))
                    .setText("Release Date:\n" + movieDetails.getmReleaseDate());
        }

        // Poster
        ImageView imgView = (ImageView) rootView.findViewById(R.id.movie_poster_detail);
        imgView.setAdjustViewBounds(true);
        Picasso.with(getActivity())
                .load(movieDetails.getmPosterUrl())
                .placeholder(R.drawable.no_image_available_black)
                .error(R.drawable.no_image_available_black)
                .into(imgView);

        // Favorite Checkbox
        CheckBox favoriteMovieCheckBox = (CheckBox) rootView.findViewById(R.id.favorite_checkbox);
        boolean favorite = movieInTables();
        favoriteMovieCheckBox.setChecked(favorite);
        if (favorite) {
            favoriteMovieCheckBox.setText(R.string.favorite);
        }
    }

    private boolean movieInTables() {
        FavoritesSelection where = new FavoritesSelection();
        where.movieName(movieDetails.getmTitle());

        Cursor c = getActivity().getContentResolver().query(FavoritesColumns.CONTENT_URI, null,
                where.sel(), where.args(), null);

        if (!(c.moveToFirst()) || c.getCount() == 0) {
            c.close();
            return false;
        } else {
            c.close();
            return true;
        }
    }

    public void favoriteClick(View v){

        CheckBox favoriteBox = (CheckBox) v;
        // Is the view now checked?
        boolean checkBoxIsChecked = favoriteBox.isChecked();
        // Is the move in the table already?
        boolean movieExistsInTable = movieInTables();

        if (checkBoxIsChecked) {
            favoriteBox.setText(R.string.favorite);
            if (!movieExistsInTable) {
                addToFavoritesTables();
            }
        } else {
            favoriteBox.setText(R.string.not_favorite);
            if (movieExistsInTable) {
                removeFromFavoritesTables();
            }
        }
    }

    /**
     * Inserts Favorite Movies into the Favorite Moves database
     */
    private void addToFavoritesTables(){
        FavoritesContentValues movieDetailsToSave = new FavoritesContentValues();
        movieDetailsToSave.putDbId(movieDetails.getmId())
                .putMovieName(movieDetails.getmTitle())
                .putDescription(movieDetails.getmDescription())
                .putRating(movieDetails.getmRating())
                .putReleaseDate(movieDetails.getmReleaseDate())
                .putPosterUrl(movieDetails.getmPosterUrl())
                .putReviewsUrl(movieDetails.getmReviewsUrl())
                .putTrailersUrl(movieDetails.getmTrailerUrl());

        getActivity().getContentResolver().insert(movieDetailsToSave.uri(),
                movieDetailsToSave.values());
    }

    /**
     * Removes movie from the Favorites table
     */
    private void removeFromFavoritesTables(){
        FavoritesSelection where = new FavoritesSelection();
        where.dbId(movieDetails.getmId());
        int deletedFavorites = where.delete(getActivity().getContentResolver());

    }

    public void setFirstYoutubeTrailerToShare(String firstYoutubeTrailerToShare) {
        this.firstYoutubeTrailerToShare = firstYoutubeTrailerToShare;
        if(mShareActionProvider != null){
            mShareActionProvider.setShareIntent(createShareTrailerIntent());
        }

    }

    private Intent createShareTrailerIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, firstYoutubeTrailerToShare);
        return shareIntent;
    }

}
