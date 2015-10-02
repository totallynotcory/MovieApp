package com.corypotwin.movieapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback{

    // TODO add a color file to the values and redo stuff in touch_selector

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;
    private String sortBySetting;
    public static final String INDIVIDUAL_MOVIE_TAG = "Movie Details";
    private MovieDetailsFragment mDetailsFragment;

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
            getSupportActionBar().setElevation(0f);
            mTwoPane = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(mTwoPane){
            inflater.inflate(R.menu.menu_main_two_pane, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    /**
     * In the case of a two pane layout, this will take care of the behavior of the favorite button
     * on the details pane
     * @param v clicked view
     */
    public void favoriteClick(View v){
        mDetailsFragment.favoriteClick(v);
    }

    @Override
    public void onItemSelected(Movie singleMovieDetails) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(INDIVIDUAL_MOVIE_TAG, singleMovieDetails);

            mDetailsFragment = new MovieDetailsFragment();
            mDetailsFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment, mDetailsFragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetails.class)
                    .putExtra(Intent.EXTRA_TEXT, singleMovieDetails);
            startActivity(intent);
        }
    }
}
