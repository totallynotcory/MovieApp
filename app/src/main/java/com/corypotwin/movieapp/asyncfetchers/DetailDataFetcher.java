package com.corypotwin.movieapp.asyncfetchers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
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
import java.util.Arrays;
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
    private View mRootView;

    private ReviewsAndTrailers revsAndTrailsForThisMovie;

    public DetailDataFetcher(int id, String rUrl, String tUrl, Context context, View rootView)
            throws MalformedURLException{
        movieId = id;
        reviewUrl = new URL(rUrl);
        trailerUrl = new URL(tUrl);
        mContext = context;
        mRootView = rootView;
    }

    protected Void doInBackground(Void... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String reviewJsonStr;
        String trailerJsonStr;

        List<List<String>> reviews = new ArrayList<>();
        List<String> trailers = new ArrayList<>();

        // Will contain the raw JSON responses as a strings.
        UrlConnector reviewConnection = new UrlConnector(reviewUrl, LOG_TAG);
        if (reviewConnection.isNetWorkAvailable(mContext)) {
            reviewJsonStr = reviewConnection.connectAndGetJson();
        } else {
            reviewJsonStr = "error";
        }

        UrlConnector trailerConnection = new UrlConnector(trailerUrl, LOG_TAG);
        if (trailerConnection.isNetWorkAvailable(mContext)) {
            trailerJsonStr = trailerConnection.connectAndGetJson();
        } else {
            trailerJsonStr = "error";
        }

        try {
            reviews = getReviewDataFromJson(reviewJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Something went wrong parsing JSON");
        }


        try {
            trailers = getTrailerDataFromJson(trailerJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Something went wrong parsing JSON");
        }

        revsAndTrailsForThisMovie = new ReviewsAndTrailers(reviews, trailers);

        return null;
    }

/*
    protected void onPostExecute(List<Movie> movieResults){

        if (revsAndTrailsForThisMovie.getReviews()){

            for (int i = 0; i < movieResults.size(); i++) {

            }
        }

        if (!revsAndTrailsForThisMovie.getReviews().equals(null)){

        }
        mContext.notifyDataSetChanged();
*/

//    }

/**
 * Used by the MovieFetcherClass to parse the Movie Data from the JSON string returned
 * from the database
 *
 * @param urlReturns - JSON string returned from themovedb
 * @return - A list of lists, each inner list containing 5 strings (title, description, rating,
 *  release date, poster image)
 * @throws JSONException
 * */

    private List<List<String>> getReviewDataFromJson(String urlReturns) throws JSONException {

        List<List<String>> reviews = new ArrayList<>();
        final String NODE_RESULTS = "results";
        final String AUTHOR_KEY = "author";
        final String CONTENT_ID = "content";

        JSONObject movieJson = new JSONObject(urlReturns);
        JSONArray resultsArray = movieJson.getJSONArray(NODE_RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject individualReview = resultsArray.getJSONObject(i);

            String author = individualReview.getString(AUTHOR_KEY);
            String review = individualReview.getString(CONTENT_ID);

            List<String> reviewWithAuthor = Arrays.asList(review, author);
            reviews.add(reviewWithAuthor);
        }

        return reviews;
    }

    private List<String> getTrailerDataFromJson(String urlReturns) throws JSONException {

        List<String> trailers = new ArrayList<>();

        final String NODE_RESULTS = "results";
        final String TRAILER_KEY = "key";
        final String SITE_KEY = "site";
        final String TYPE_KEY = "type";
        String youtubeBaseUrl = "https://www.youtube.com/watch?v=";

        JSONObject movieJson = new JSONObject(urlReturns);
        JSONArray resultsArray = movieJson.getJSONArray(NODE_RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject individualMovie = resultsArray.getJSONObject(i);

            String type = individualMovie.getString(TYPE_KEY);
            String site = individualMovie.getString(SITE_KEY);
            String trailerKey = individualMovie.getString(TRAILER_KEY);

            if(type.equals("Trailer")){
                if(site.equals("YouTube")){
                    String returnUrl = youtubeBaseUrl + trailerKey;
                    trailers.add(returnUrl);
                }
            }

        }

        return trailers;
    }

    final class ReviewsAndTrailers {

        private List<List<String>> reviews;
        private List<String> trailers;

        public ReviewsAndTrailers(List<List<String>> revs, List<String> trails){
            reviews = revs;
            trailers = trails;
        }

        public List<List<String>> getReviews() {
            return reviews;
        }

        public void setReviews(List<List<String>> reviews) {
            this.reviews = reviews;
        }

        public List<String> getTrailers() {
            return trailers;
        }

        public void setTrailers(List<String> trailers) {
            this.trailers = trailers;
        }



    }
}
