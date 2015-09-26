package com.corypotwin.movieapp.asyncfetchers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.corypotwin.movieapp.Movie;
import com.corypotwin.movieapp.R;

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
 * Used to fetch data for the detail fragment - specifically Youtube URLs and Review text
 */
public class DetailDataFetcher extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = "DetailDataFetcher";

    private int movieId;
    private URL reviewUrl;
    private URL trailerUrl;
    private Context mContext;

    public DetailDataFetcher(int id, String rUrl, String tUrl, Context context)
            throws MalformedURLException{
        movieId = id;
        reviewUrl = new URL(rUrl);
        trailerUrl = new URL(tUrl);
        mContext = context;
    }

    protected Void doInBackground(Void... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON responses as a strings.
        UrlConnector reviewConnection = new UrlConnector(reviewUrl, LOG_TAG);
        if (reviewConnection.isNetWorkAvailable(mContext)) {
            String reviewJsonStr = reviewConnection.connectAndGetJson();
        }

        UrlConnector trailerConnection = new UrlConnector(trailerUrl, LOG_TAG);
        if (trailerConnection.isNetWorkAvailable(mContext)) {
            String trailerJsonStr = trailerConnection.connectAndGetJson();
        }

/*


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

    */
/**
 * Used by the MovieFetcherClass to parse the Movie Data from the JSON string returned
 * from the database
 *
 * @param urlReturns - JSON string returned from themovedb
 * @return - A list of lists, each inner list containing 5 strings (title, description, rating,
 *  release date, poster image)
 * @throws JSONException
 *//*

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
*/
        return null;
    }

}
