package com.corypotwin.movieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MovieDetails extends AppCompatActivity {

    private MovieDetailsFragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (savedInstanceState == null){

            Bundle args = new Bundle();
            args.putParcelable(MainActivity.INDIVIDUAL_MOVIE_TAG,
                    getIntent().getParcelableExtra(Intent.EXTRA_TEXT));

            mDetailFragment = new MovieDetailsFragment();
            mDetailFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment, mDetailFragment)
                    .commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingActivityIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingActivityIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void favoriteClick(View v){
        mDetailFragment.favoriteClick(v);
    }

}
