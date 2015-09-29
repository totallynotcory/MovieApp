package com.corypotwin.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback{

    // TODO add a color file to the values and redo stuff in touch_selector

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;
    private String sortBySetting;
    public static final String INDIVIDUAL_MOVIE_TAG = "Movie Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sortBySetting = PreferenceManager.getDefaultSharedPreferences(this).getString(
                getString(R.string.pref_sort_by_key),
                getString(R.string.pref_sort_by_default));

        if (findViewById(R.id.detail_fragment) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment, new MovieDetailsFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

/*        ForecastFragment forecastFragment =  ((ForecastFragment)getSupportFragmentManager()
                .findFragmentById(R.id.fragment_main));
        forecastFragment.setUseTodayLayout(!mTwoPane);*/
    }

/*

    @Override
    protected void onResume() {
        super.onResume();
        // Grab the sortBy preference
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = prefs.getString(
                getString(R.string.pref_sort_by_key),
                getString(R.string.pref_sort_by_default));

        // If the previous sortBy preference does not match the current preference,
        // we need to refresh. This include the initial load as well.
        if(!sortBy.equals(sortBySetting)){
            sortBySetting = sortBy;
            MainActivityFragment mf = (MainActivityFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.main_fragment);
            if( null != mf ) {
                mf.refreshMovieData();
            }
            MovieDetailsFragment mdf = (MovieDetailsFragment) getSupportFragmentManager()
                    .findFragmentByTag(DETAILFRAGMENT_TAG);
*/
/*          This code was originally intended to reset the details after the preferences changed.
            I'm not sure I need to do that and it's messy. lawlz.
            if ( null != mdf ) {
                onItemSelected(mf.getmImageAdapter().getFullSingleMovieDetails(0));
            }*//*

        }
    }

*/


    /*
    Fragmentmanager fm = getFragmentManager();

    Fragment fragB = new DetailsFragment();
    int containerID = R.id.fragment_container;
    String tag = null;

    FragmentTransaction ft = fm.beginTransaction();
    ft.replace(containerID, fragB);
    ft.addToBackStack(tag);
    ft.commit();
     */



    @Override
    public void onItemSelected(Movie singleMovieDetails) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(INDIVIDUAL_MOVIE_TAG, singleMovieDetails);

            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetails.class)
                    .putExtra(Intent.EXTRA_TEXT, singleMovieDetails);
            startActivity(intent);
        }
    }
}
