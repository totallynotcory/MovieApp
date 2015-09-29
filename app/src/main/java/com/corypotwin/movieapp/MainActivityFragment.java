package com.corypotwin.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.corypotwin.movieapp.asyncfetchers.MovieDataFetcher;

/**
 * A fragment containing the Movie grid view
 */
public class MainActivityFragment extends Fragment {

    //  TODO for future versions, it might be necessary to change the width of the imported images

    public ImageAdapter getmImageAdapter() {
        return mImageAdapter;
    }

    private ImageAdapter mImageAdapter;
    private GridView mGridView;
    private String sortBySetting;

    private final String POSITION = "position";
    private int mPosition;

    public MainActivityFragment() {
    }

/*    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, mPosition);
    }*/

    @Override
    public void onConfigurationChanged(Configuration newConfiguration){
        super.onConfigurationChanged(newConfiguration);
        mGridView.smoothScrollToPosition(mPosition);
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  This allows the Fragment to affect the menu
        setHasOptionsMenu(true);

        if (savedInstanceState != null && savedInstanceState.containsKey(POSITION)) {
            mPosition = savedInstanceState.getInt(POSITION);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refreshMovieData();
            return true;
        }
        if (id == R.id.action_settings) {
            Intent settingActivityIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingActivityIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                ((Callback) getActivity()).onItemSelected(singleMovieDetails);
            }
        });
        return rootView;
    }

    /**
     * Refreshes the movie data from the MovieDB API
     */
    public void refreshMovieData() {
        if(sortBySetting != "favorites") {
            MovieDataFetcher refresher = new MovieDataFetcher(getActivity(), getActivity(),
                    mImageAdapter, sortBySetting);
            refresher.execute();
        }
        // TODO else { get favorites from DB }
    }

    /**
     * For any Activity that includes this Fragment, they must define behavior for this Callback
     * Otherwise, nothing will happen when a Movie is clicked
     */

    public interface Callback {
        public void onItemSelected(Movie singleMovieDetails);
        }
}




