package com.corypotwin.movieapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.corypotwin.movieapp.asyncfetchers.DetailDataFetcher;
import com.corypotwin.movieapp.oldfavdata.FavoriteMovieContract;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;
import com.corypotwin.movieapp.provider.favorites.FavoritesContentValues;
import com.corypotwin.movieapp.provider.favorites.FavoritesCursor;
import com.corypotwin.movieapp.provider.favorites.FavoritesSelection;
import com.corypotwin.movieapp.provider.reviews.ReviewsColumns;
import com.corypotwin.movieapp.provider.reviews.ReviewsContentValues;
import com.corypotwin.movieapp.provider.reviews.ReviewsSelection;
import com.corypotwin.movieapp.provider.trailers.TrailersColumns;
import com.corypotwin.movieapp.provider.trailers.TrailersContentValues;
import com.corypotwin.movieapp.provider.trailers.TrailersSelection;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private final String LOG_TAG = "Movie Details Fragment";
    private Movie movieDetails;
    private String movieTitle;

    public void setmRevsAndTrails(ReviewsAndTrailers mRevsAndTrails) {
        this.mRevsAndTrails = mRevsAndTrails;
    }

    private ReviewsAndTrailers mRevsAndTrails;


    public MovieDetailsFragment() {
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

    public void updateReviewsAndTrailers(View rootView) {
        try {
        DetailDataFetcher ddFetcher = new DetailDataFetcher(
                movieDetails.getmId(),
                movieDetails.getmReviews(),
                movieDetails.getmTrailerUrl(),
                getActivity(),
                rootView,
                this);
        ddFetcher.execute();
        } catch(MalformedURLException e){
            Log.e(LOG_TAG, "updateReviewsAndTrailers ");
        }

    }

    /**
     * Populates the Child Views in the Movie Details Fragment
     *
     * @param rootView - Fragment View used to find the appropriate child view from
     */
    private void populateDetailChildViews(View rootView){

        // Title
        movieTitle = movieDetails.getmTitle();
        ((TextView) rootView.findViewById(R.id.title)).setText(movieTitle);

        // Description
        if(movieDetails.getmDescription() == null){
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


    private boolean movieInTables() {
        FavoritesSelection where = new FavoritesSelection();
        where.movieName(movieDetails.getmTitle());
        String[] projection = {FavoritesColumns.DB_ID};
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

        // Is the view now checked?
        boolean checkBoxIsChecked = ((CheckBox) v).isChecked();
        // Is the move in the table already?
        boolean movieExistsInTable = movieInTables();

        if (checkBoxIsChecked) {
            if (!movieExistsInTable) {
                addToFavoritesTables();
            }
        } else {
            if (movieExistsInTable) {
                removeFromFavoritesTables();
            }
        }
    }


    private void addToFavoritesTables(){
        FavoritesContentValues movieDetailsToSave = new FavoritesContentValues();
        movieDetailsToSave.putDbId(movieDetails.getmId())
                .putMovieName(movieDetails.getmTitle())
                .putDescription(movieDetails.getmDescription())
                .putRating(movieDetails.getmRating())
                .putReleaseDate(movieDetails.getmReleaseDate())
                .putPosterUrl(movieDetails.getmPosterUrl());

        getActivity().getContentResolver().insert(movieDetailsToSave.uri(),
                movieDetailsToSave.values());

        if(mRevsAndTrails.hasReviews()) {

            for (int i = 0; i < mRevsAndTrails.reviewsSize(); i++) {
                ReviewsContentValues reviewDetailsToSave = new ReviewsContentValues();
                reviewDetailsToSave.putMovieId(movieDetails.getmId())
                        .putReview(mRevsAndTrails.getReviews().get(i).get(0))
                        .putAuthor(mRevsAndTrails.getReviews().get(i).get(1));

                ContentValues cv = reviewDetailsToSave.values();

                getActivity().getContentResolver().insert(ReviewsColumns.CONTENT_URI,
                        reviewDetailsToSave.values());
            }


        }

        if(mRevsAndTrails.hasTrailers()){
            for (int i = 0; i < mRevsAndTrails.trailerSize(); i++) {
                TrailersContentValues trailersDetailsToSave = new TrailersContentValues();
                trailersDetailsToSave.putMovieId(movieDetails.getmId())
                        .putTrailerYoutubeId(mRevsAndTrails.getTrailers().get(i));

                getActivity().getContentResolver().insert(trailersDetailsToSave.uri(),
                        trailersDetailsToSave.values());
            }
        }
    }

    private void removeFromFavoritesTables(){
        FavoritesSelection where = new FavoritesSelection();
        where.dbId(movieDetails.getmId());
        int deletedFavorites = where.delete(getActivity().getContentResolver());

        ReviewsSelection where1 = new ReviewsSelection();
        where1.movieId(movieDetails.getmId());
        int deletedReviews = where1.delete(getActivity().getContentResolver());

        TrailersSelection where2 = new TrailersSelection();
        where2.movieId(movieDetails.getmId());
        int deletedTrailers = where2.delete(getActivity().getContentResolver());

    }

}
