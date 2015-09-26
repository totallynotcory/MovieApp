package com.corypotwin.movieapp.asyncfetchers;

/**
 * Created by ctpotwin on 9/26/15.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.corypotwin.movieapp.ImageAdapter;
import com.corypotwin.movieapp.Movie;
import com.corypotwin.movieapp.R;
import com.corypotwin.movieapp.SecretKeyFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Creates an ASync thread to request, receive, and parse data from the MovieDB API
 * Then, on the main thread, this will load the information into the ImageAdapter for the
 * grid view and inform the app that data has changed
 *
 */
public class MovieDataFetcher extends AsyncTask<Void, Void, List<Movie>> {

    private final Context mContext;
    private final ImageAdapter mImageAdapter;
    private final String mSortBy;
    private final Activity mActivity;

    private String screenSize = "w185";

    public MovieDataFetcher(Context context, Activity activity,
                            ImageAdapter imageAdapter, String sortBySetting) {
        mContext = context;
        mImageAdapter = imageAdapter;
        this.mSortBy = sortBySetting;
        mActivity = activity;
    }


    final String LOG_TAG = "MovieDataFetcher";

    final String MOVIE_BASE_URL = "http://api.themoviedb.org/3";
    final String USER_KEY_PARAM = "api_key";
    // SecretKeyFile contains the API key for TheMovieDB.  This file has been added to the
    // .gitignore file.
    String secretKey = SecretKeyFile.getKey();

    protected List<Movie> doInBackground(Void... params) {

        //  I feel like initiating this as "" is kind of a bad practice...
        // TODO is this a bad idea?
        String movieJsonStr = "";
        URL url;
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.

        String currentDate = new SimpleDateFormat("y").format(new Date());

        final String SORT_PARAM = "sort_by";
        final String YEAR_PARAM = "year";
        final String DISCOVER_PATH = "discover";
        final String MOVIE_PATH = "movie";

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(DISCOVER_PATH)
                .appendPath(MOVIE_PATH)
                .appendQueryParameter(YEAR_PARAM, currentDate)
                .appendQueryParameter(SORT_PARAM, mSortBy)
                .appendQueryParameter(USER_KEY_PARAM, secretKey)
                .build();

        try {
            url = new URL(builtUri.toString());
            UrlConnector mainPageConnection = new UrlConnector(url, LOG_TAG);
            if (mainPageConnection.isNetWorkAvailable(mContext)) {
                movieJsonStr = mainPageConnection.connectAndGetJson();
            }
        } catch(MalformedURLException e){
            Log.e(LOG_TAG, "Error when creating URL");
        }

        try {
            return getMovieDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Something went wrong parsing JSON");
        }

        return null;
    }

    protected void onPostExecute(List<Movie> movieResults){
        TextView noConnection = (TextView) mActivity.findViewById(R.id.no_connection);

        if (movieResults != null){
            mImageAdapter.clear();
            for (int i = 0; i < movieResults.size(); i++) {
                mImageAdapter.add(movieResults.get(i));
            }
            noConnection.setText("");
        } else {
            mImageAdapter.clear();
            noConnection.setText("Cannot Establish Connection");
        }
        mImageAdapter.notifyDataSetChanged();
    }

    /**
     * Used by the MovieFetcherClass to parse the Movie Data from the JSON string returned
     * from the database
     *
     * @param urlReturns - JSON string returned from themovedb
     * @return - A list of lists, each inner list containing 5 strings (title, description, rating,
     *  release date, poster image)
     * @throws JSONException
     */
    private List<Movie> getMovieDataFromJson(String urlReturns) throws JSONException {

        List<Movie> movieData = new ArrayList<>();

        final String NODE_RESULTS = "results";
        final String POSTER_KEY = "poster_path";
        final String MOVIE_ID = "id";
        final String TITLE_KEY = "title";
        final String RELEASE_DATE_KEY = "release_date";
        final String RATING_KEY = "vote_average";
        final String OVERVIEW_KEY = "overview";
        String posterUrl = "http://image.tmdb.org/t/p/" + screenSize + "/";
        final String TRAILERS_URL_ADD = "videos";
        final String REVIEWS_URL_ADD = "reviews";

        JSONObject movieJson = new JSONObject(urlReturns);
        JSONArray resultsArray = movieJson.getJSONArray(NODE_RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject individualMovie = resultsArray.getJSONObject(i);

            int id = individualMovie.getInt(MOVIE_ID);
            String title = individualMovie.getString(TITLE_KEY);
            String description = individualMovie.getString(OVERVIEW_KEY);
            String rating = individualMovie.getString(RATING_KEY);
            String releaseDate = individualMovie.getString(RELEASE_DATE_KEY);
            String imageUrl= posterUrl + individualMovie.getString(POSTER_KEY);

            String trailerUrl = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath("movie")
                    .appendPath(id + "")
                    .appendPath(TRAILERS_URL_ADD)
                    .appendQueryParameter(USER_KEY_PARAM, secretKey)
                    .build()
                    .toString();

            String reviewsUrl = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath("movie")
                    .appendPath(id + "")
                    .appendPath(REVIEWS_URL_ADD)
                    .appendQueryParameter(USER_KEY_PARAM, secretKey)
                    .build()
                    .toString();


            Movie thisMovie = new Movie(id, title, description, rating,
                    releaseDate, imageUrl, trailerUrl, reviewsUrl);

            movieData.add(thisMovie);
        }

        return movieData;
    }

    private boolean isNetWorkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}