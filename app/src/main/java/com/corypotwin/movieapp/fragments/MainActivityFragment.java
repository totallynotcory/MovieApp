package com.corypotwin.movieapp.fragments;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.corypotwin.movieapp.R;
import com.corypotwin.movieapp.asyncfetchers.MovieDataFetcher;
import com.corypotwin.movieapp.customdata.ImageAdapter;
import com.corypotwin.movieapp.customdata.Movie;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;
import com.corypotwin.movieapp.provider.favorites.FavoritesSelection;

import java.util.ArrayList;

/**
 * A fragment containing the Movie grid view
 */
public class MainActivityFragment extends Fragment {

    public ImageAdapter getmImageAdapter() {
        return mImageAdapter;
    }
    public void setmImageAdapter(ImageAdapter imageAdapter) { mImageAdapter = imageAdapter; }

    private ImageAdapter mImageAdapter;
    private GridView mGridView;
    private String sortBySetting;

    private ArrayList<Movie> movieData;

    private final String MOVIE_ARRAY = "Movie Array";

    private final String POSITION = "position";
    private int mPosition;

    public MainActivityFragment() {
    }

    @Override
    public void onConfigurationChanged(Configuration newConfiguration){
        super.onConfigurationChanged(newConfiguration);
        mGridView.smoothScrollToPosition(mPosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(POSITION)) {
            mPosition = savedInstanceState.getInt(POSITION);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_ARRAY)) {
            movieData = savedInstanceState.getParcelableArrayList(MOVIE_ARRAY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) rootView.findViewById(R.id.movie_poster_grid);
        mImageAdapter = new ImageAdapter(getActivity());
        mGridView.setAdapter(mImageAdapter);

        // On clicking a movie, send data over to the new intent.
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                mPosition = position;
                Movie singleMovieDetails = mImageAdapter.getFullSingleMovieDetails(position);
                singleMovieDetails.setmPosition(position);
                ((Callback) getActivity()).onItemSelected(singleMovieDetails, position);
            }
        });

        if(savedInstanceState != null && savedInstanceState.containsKey(MOVIE_ARRAY)){
            insertDataBackIntoImageAdapter();
        }
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        // Grab the sortBy preference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = prefs.getString(
                getString(R.string.pref_sort_by_key),
                getString(R.string.pref_sort_by_default));


        // If the previous sortBy preference does not match the current preference,
        // we need to refresh. This include the initial load as well.
        if(!sortBy.equals(sortBySetting)){
            sortBySetting = sortBy;
            refreshMovieData();
        }
    }



    /**
     * Recreates the information previously stored in the image adapter.
     */
    private void insertDataBackIntoImageAdapter(){
        mImageAdapter.clear();
        for (int i = 0; i < movieData.size(); i++) {
            mImageAdapter.add(movieData.get(i));
        }
        mImageAdapter.notifyDataSetChanged();
    }

    /**
     * Refreshes the movie data from the MovieDB API
     */
    public void refreshMovieData() {
        if(!sortBySetting.equals("favorites")) {
            MovieDataFetcher refresher = new MovieDataFetcher(getActivity(), getActivity(),
                    mImageAdapter, sortBySetting, this);
            refresher.execute();
        } else {
            insertFavoriteMovies();
        }
    }

    /**
     * Retrieves favorite move data from the favorites.db to insert into the ImageAdapter used in
     * the main fragment
     */
    private void insertFavoriteMovies(){

        if(movieData != null) {
            movieData.clear();
        }

        FavoritesSelection where = new FavoritesSelection();
        Cursor c = getActivity().getContentResolver().query(FavoritesColumns.CONTENT_URI, null,
                null, null, null);

        if (c.moveToFirst()){
            ArrayList<Movie> favoritesMovies = new ArrayList<>(c.getCount());
            mImageAdapter.clear();
            do{
                Movie aFavoriteMovie = new Movie(
                        c.getInt(c.getColumnIndex(FavoritesColumns.DB_ID)),
                        c.getString(c.getColumnIndex(FavoritesColumns.MOVIE_NAME)),
                        c.getString(c.getColumnIndex(FavoritesColumns.DESCRIPTION)),
                        c.getString(c.getColumnIndex(FavoritesColumns.RATING)),
                        c.getString(c.getColumnIndex(FavoritesColumns.RELEASE_DATE)),
                        c.getString(c.getColumnIndex(FavoritesColumns.POSTER_URL)),
                        c.getString(c.getColumnIndex(FavoritesColumns.REVIEWS_URL)),
                        c.getString(c.getColumnIndex(FavoritesColumns.TRAILERS_URL))
                        );
                mImageAdapter.add(aFavoriteMovie);
                favoritesMovies.add(aFavoriteMovie);
            } while (c.moveToNext());
            setMovieData(favoritesMovies);

        } else {
            mImageAdapter.clear();
        }
        mImageAdapter.notifyDataSetChanged();
    }

    /**
     * For any Activity that includes this Fragment, they must define behavior for this Callback
     * Otherwise, nothing will happen when a Movie is clicked
     */
    public interface Callback {
        public void onItemSelected(Movie singleMovieDetails, int position);
        }

    public void setMovieData(ArrayList<Movie> movieData) {
        this.movieData = movieData;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(MOVIE_ARRAY, movieData);
        savedInstanceState.putInt(POSITION, mPosition);
        super.onSaveInstanceState(savedInstanceState);
    }
}




