package com.corypotwin.movieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //  TODO for future versions, it might be necessary to change the width of the imported images
    private String screenSize = "w185";
    private ImageAdapter mImageAdapter;

    public MainActivityFragment() {
    }

    //  TODO debug this and figure out why rotating isn't fixing this
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart(){
        super.onStart();
        refreshMovieData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  This allows the Fragment to affect the menu
        setHasOptionsMenu(true);
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

        GridView gridview = (GridView) rootView.findViewById(R.id.movie_poster_grid);
        mImageAdapter = new ImageAdapter(getActivity());
        gridview.setAdapter(mImageAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ArrayList<String> singleMovieDetails =
                        (ArrayList) mImageAdapter.getFullSingleMovieDetails(position);
                Intent intent = new Intent(getActivity(), MovieDetails.class)
                        .putStringArrayListExtra(Intent.EXTRA_TEXT, singleMovieDetails);
                startActivity(intent);
            }
        });
        return rootView;

    }

    /**
     * Refreshes the movie data from the MovieDB API
     */
    public void refreshMovieData() {
        MovieDataFetcher refresher = new MovieDataFetcher();
        refresher.execute();
    }


    private List<List<String>> getMovieDataFromJson(String urlReturns) throws JSONException {

        List<List<String>> movieData = new ArrayList<List<String>>();

        final String NODE_RESULTS = "results";
        final String POSTER_KEY = "poster_path";
        final String TITLE_KEY = "title";
        final String RELEASE_DATE_KEY = "release_date";
        final String RATING_KEY = "vote_average";
        final String OVERVIEW_KEY = "overview";
        String posterUrl = "http://image.tmdb.org/t/p/" + screenSize + "/";

        JSONObject movieJson = new JSONObject(urlReturns);
        JSONArray resultsArray = movieJson.getJSONArray(NODE_RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject individualMovie = resultsArray.getJSONObject(i);

            String title = individualMovie.getString(TITLE_KEY);
            String description = individualMovie.getString(OVERVIEW_KEY);
            String rating = individualMovie.getString(RATING_KEY);
            String releaseDate = individualMovie.getString(RELEASE_DATE_KEY);
            String imageUrlTail = individualMovie.getString(POSTER_KEY);
            String imageUrl = posterUrl + imageUrlTail;

            List<String> movieDetails = new ArrayList<String>();
            movieDetails.add(title);
            movieDetails.add(description);
            movieDetails.add(rating);
            movieDetails.add(releaseDate);
            movieDetails.add(imageUrl);

            movieData.add(movieDetails);
        }

        return movieData;
    }

    /**
     * Creates an ASync thread to request, receive, and parse data from the MovieDB API
     * Then, on the main thread this will load the information into the ImageAdapter for the grid view
     */
    protected class MovieDataFetcher extends AsyncTask<Void, Void, List<List<String>>> {

        final String LOG_TAG = "MovieDataFetercher";

        // TODO remove this when sharing code
        String secretKey = SecretKeyFile.getKey();

        protected List<List<String>> doInBackground(Void... params) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortBy = prefs.getString(
                    getString(R.string.pref_sort_by_key),
                    getString(R.string.pref_sort_by_default));
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieJsonStr;
            String currentDate = new SimpleDateFormat("y").format(new Date());

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast

                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
                final String SORT_PARAM = "sort_by";
                final String YEAR_PARAM = "year";
                final String USER_KEY_PARAM = "api_key";
                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(YEAR_PARAM, currentDate)
                        .appendQueryParameter(SORT_PARAM, sortBy)
                        .appendQueryParameter(USER_KEY_PARAM, secretKey)
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to Movie API, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error Connecting", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Something went wrong parsing JSON");
            }
            Log.d("MovieDataFetcher","Finished fetching data");
            return null;
        }

        protected void onPostExecute(List<List<String>> movieResults){
            if (movieResults != null){
                mImageAdapter.clear();
                for (int i = 0; i < movieResults.size(); i++) {
                    mImageAdapter.add(movieResults.get(i));
                }
            }
            mImageAdapter.notifyDataSetChanged();
        }
    }

}




