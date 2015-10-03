package com.corypotwin.movieapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
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

import com.corypotwin.movieapp.activities.MainActivity;
import com.corypotwin.movieapp.R;
import com.corypotwin.movieapp.asyncfetchers.DetailDataFetcher;
import com.corypotwin.movieapp.customdata.Movie;
import com.corypotwin.movieapp.customdata.Review;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;
import com.corypotwin.movieapp.provider.favorites.FavoritesContentValues;
import com.corypotwin.movieapp.provider.favorites.FavoritesSelection;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private String firstYoutubeTrailerToShare;
    private ShareActionProvider mShareActionProvider;
    private final String LOG_TAG = "Movie Details Fragment";
    // Stores details of movie to show in this fragment
    private Movie movieDetails;

    private String youtubeBaseVideoUrl = "https://www.youtube.com/watch?v=";
    private String youtubeBaseJpgUrl = "http://img.youtube.com/vi/";
    private final String TRAILERS = "Trailers";
    private ArrayList<String> mTrailers;

    private final String REVIEWS = "Reviews";
    private ArrayList<Review> mReviews;

    private View mRootView;

    public MovieDetailsFragment() {
        setHasOptionsMenu(true);
        firstYoutubeTrailerToShare = "";
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration){
        super.onConfigurationChanged(newConfiguration);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(TRAILERS)) {
            mTrailers = savedInstanceState.getStringArrayList(TRAILERS);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(REVIEWS)) {
            mReviews = savedInstanceState.getParcelableArrayList(REVIEWS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Bundle args = getArguments();
        if(args != null){
            movieDetails = args.getParcelable(MainActivity.INDIVIDUAL_MOVIE_TAG);
            if(savedInstanceState == null) {
                updateReviewsAndTrailers();
            }
            populateDetailChildViews(mRootView);
        } else {
           return null;
        }

        return mRootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        if (firstYoutubeTrailerToShare != null && !firstYoutubeTrailerToShare.isEmpty()){
            mShareActionProvider.setShareIntent(createShareTrailerIntent());
        }
    }

    @Override
    public void onStart(){
        super.onStart();

        if(mTrailers != null){
            insertTrailersBackIn(mTrailers);
        }
        if(mReviews != null){
            insertReviewsBackIn(mReviews);
        }

    }

    /**
     * Inserts trailers into detail view.
     * @param trailers trailers to insert into view
     */
    public void insertTrailersBackIn(ArrayList<String> trailers){

        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView trailersHeader = (TextView) mRootView.findViewById(R.id.trailer_header);
        trailersHeader.setText(getActivity().getString(R.string.trailers_header));

        ViewGroup insertPoint = (ViewGroup) mRootView.findViewById(R.id.trailers_container);

        if(insertPoint.findViewById(R.id.trailer_image) == null) {

            for (int i = 0; i < trailers.size(); i++) {

                View trailerView = vi.inflate(R.layout.trailer_item, null);

                ImageView image = (ImageView) trailerView.findViewById(R.id.trailer_image);
                TextView text = (TextView) trailerView.findViewById(R.id.trailer_title);

                String imageUrl = youtubeBaseJpgUrl +
                        trailers.get(i) + "/0.jpg";

                Picasso.with(getActivity()).
                        load(imageUrl).
                        memoryPolicy(MemoryPolicy.NO_CACHE).
                        networkPolicy(NetworkPolicy.NO_CACHE).
                        placeholder(R.drawable.no_image_available_black).
                        error(R.drawable.no_image_available_black).
                        into(image);

                text.setText("Trailer " + (i + 1));

                final Uri youtubeUrl = Uri.parse(youtubeBaseVideoUrl +
                        trailers.get(i));

                // Setup trailers so each points to the corresponding Youtube link
                trailerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                        videoIntent.setData(youtubeUrl);

                        if (videoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            getActivity().startActivity(videoIntent);
                        } else {
                            Log.d(LOG_TAG, "Couldn't call " + youtubeUrl + ", no receiving apps installed!");
                        }
                    }
                });

                insertPoint.addView(trailerView, i, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
        }
    }

    /**
     * Inserts reviews into detail view.
     * @param reviews reviews to insert into view
     */
    public void insertReviewsBackIn(ArrayList<Review> reviews){

        LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView reviewHeader = (TextView) mRootView.findViewById(R.id.reviews_header);
        reviewHeader.setText(getActivity().getString(R.string.reviews_header));

        ViewGroup insertPoint = (ViewGroup) mRootView.findViewById(R.id.reviews_container);

        if(insertPoint.findViewById(R.id.review_body) == null) {

            for (int i = 0; i < reviews.size(); i++) {

                View reviewView = vi.inflate(R.layout.review_item, null);

                TextView review = (TextView) reviewView.findViewById(R.id.review_body);
                TextView reviewer = (TextView) reviewView.findViewById(R.id.reviewer_name);

                review.setText(reviews.get(i).getReview());
                reviewer.setText("-" + reviews.get(i).getAuthor());

                insertPoint.addView(reviewView, i, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
        }
    }


    /**
     * Launches an async task to retrieve Trailers and Reviews from the server.
     */
    public void updateReviewsAndTrailers() {
        try {
            DetailDataFetcher ddFetcher = new DetailDataFetcher(
                    movieDetails.getmReviewsUrl(),
                    movieDetails.getmTrailerUrl(),
                    getActivity(),
                    this);
            ddFetcher.execute();
        } catch(MalformedURLException e){
            Log.e(LOG_TAG, "updateReviewsAndTrailers received a malformed URL");
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

    /**
     * Checks the favorites to see if the movie in details exists
     * @return true if movie is favorited, else false
     */
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

    /**
     * Adds or removes movie from favorite database dependant on the new state of the check box
     * and whether it already exists or doesn't in table.
     * @param v checkbox view
     */
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

    /**
     * sets ShareAction provider to the first trailer
     * @param trailerUrl url of the trailer to share
     */
    public void setFirstYoutubeTrailerToShare(String trailerUrl) {
        this.firstYoutubeTrailerToShare = youtubeBaseVideoUrl + trailerUrl;
        if(mShareActionProvider != null){
            mShareActionProvider.setShareIntent(createShareTrailerIntent());
        }

    }

    /**
     * Creates share Intent for the trailers
     * @return share intent.
     */
    private Intent createShareTrailerIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, firstYoutubeTrailerToShare);
        return shareIntent;
    }

    public void storeTrailers(ArrayList<String> trailers){
        mTrailers = trailers;
    }

    public void storeReviews(ArrayList<Review> reviews){
        mReviews = reviews;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArrayList(TRAILERS, mTrailers);
        savedInstanceState.putParcelableArrayList(REVIEWS, mReviews);
        super.onSaveInstanceState(savedInstanceState);
    }
}
