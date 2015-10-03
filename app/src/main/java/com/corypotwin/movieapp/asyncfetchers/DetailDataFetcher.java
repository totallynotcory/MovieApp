package com.corypotwin.movieapp.asyncfetchers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.corypotwin.movieapp.MovieDetailsFragment;
import com.corypotwin.movieapp.R;
import com.corypotwin.movieapp.Review;
import com.corypotwin.movieapp.ReviewsAndTrailers;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used to fetch data for the detail fragment - specifically Youtube URLs and Review text
 */
public class DetailDataFetcher extends AsyncTask<Void, Void, ReviewsAndTrailers> {

    private final String LOG_TAG = "DetailDataFetcher";

    private String reviewUrl;
    private String trailerUrl;
    private Context mContext;
    private MovieDetailsFragment mMdf;


    public DetailDataFetcher(String rUrl, String tUrl, Context context,
                             MovieDetailsFragment mdf)
            throws MalformedURLException{

        reviewUrl = rUrl;
        trailerUrl = tUrl;
        mContext = context;
        mMdf = mdf;
    }

    /**
     * Retrieve data from the movie db about the trailers and reviews.
     * @param params Always void
     * @return Reviews and Trailers stored in a custom class
     */
    protected ReviewsAndTrailers doInBackground(Void... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String reviewJsonStr = "";
        String trailerJsonStr = "";

        ArrayList<Review> reviews = new ArrayList<>();
        ArrayList<String> trailers = new ArrayList<>();

        // Retrieve JSON for Reviews
        try {
            URL reviewUrlUrl = new URL(reviewUrl);
            UrlConnector reviewConnection = new UrlConnector(reviewUrlUrl, LOG_TAG);
            if (reviewConnection.isNetWorkAvailable(mContext)) {
                reviewJsonStr = reviewConnection.connectAndGetJson();
            }
        } catch(MalformedURLException e){
            Log.e(LOG_TAG, "Error when creating URL");
        }

        // Retrieve JSON for Trailers
        try {
            URL trailerUrlUrl = new URL(trailerUrl);
            UrlConnector trailerConnection = new UrlConnector(trailerUrlUrl, LOG_TAG);
            if (trailerConnection.isNetWorkAvailable(mContext)) {
                trailerJsonStr = trailerConnection.connectAndGetJson();
            }
        }    catch(MalformedURLException e){
                Log.e(LOG_TAG, "Error when creating URL");
        }

        // Parse JSON for Reviews
        try {
            reviews = getReviewDataFromJson(reviewJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Something went wrong parsing review JSON");
        }

        // Parse JSON for trailers
        try {
            trailers = getTrailerDataFromJson(trailerJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Something went wrong parsing trailer JSON");
        }

        return new ReviewsAndTrailers(reviews, trailers);
    }

    /**
     * Adds Reviers and Trailers found during the AsyncTask to the current view.
     * There will be a slighty delay before their added, and the user may see a "No Image Available"
     * for the Trailer.
     * @param revsAndTrailsForThisMovie Trailers and Movies for this movie. Check inner class for
     *                                  structure details.
     */
    protected void onPostExecute(ReviewsAndTrailers revsAndTrailsForThisMovie){


        if (revsAndTrailsForThisMovie.hasTrailers()) {
            // Store Trailers in the Fragment as well as add one to the shareintent
            mMdf.storeTrailers(revsAndTrailsForThisMovie.getTrailers());
            mMdf.setFirstYoutubeTrailerToShare(revsAndTrailsForThisMovie.getTrailers().get(0));
            // Add trailers to the detail review if any exist.
            mMdf.insertTrailersBackIn(revsAndTrailsForThisMovie.getTrailers());
        }


        if (revsAndTrailsForThisMovie.hasReviews()){
            // Store Reviews in the Fragment.
            mMdf.storeReviews(revsAndTrailsForThisMovie.getReviews());
            // Add reviews to the detail view if any exist
            mMdf.insertReviewsBackIn(revsAndTrailsForThisMovie.getReviews());
        }
    }

    /**
     * Parses the Review Data from the JSON string returned in the DetailDataFetcher
     * from the database
     *
     * @param urlReturns - JSON string returned from themovedb of review data
     * @return - A list of lists, each inner list containing the details of one review, a review and
     * its author.
     * @throws JSONException
     * */
    private ArrayList<Review> getReviewDataFromJson(String urlReturns) throws JSONException {

        ArrayList<Review> reviews = new ArrayList<>();
        final String NODE_RESULTS = "results";
        final String AUTHOR_KEY = "author";
        final String CONTENT_ID = "content";

        JSONObject movieJson = new JSONObject(urlReturns);
        JSONArray resultsArray = movieJson.getJSONArray(NODE_RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject individualReview = resultsArray.getJSONObject(i);

            String author = individualReview.getString(AUTHOR_KEY);
            String review = individualReview.getString(CONTENT_ID);

            Review reviewWithAuthor = new Review(review, author);
            reviews.add(reviewWithAuthor);
        }

        return reviews;
    }

    /**
     * Parses the Trailer Data from the JSON string returned in the DetailDataFetcher
     * from the database
     *
     * @param urlReturns - JSON string returned from themovedb of trailer data
     * @return - A list containing the youtube identifier.
     * @throws JSONException
     * */
    private ArrayList<String> getTrailerDataFromJson(String urlReturns) throws JSONException {

        ArrayList<String> trailers = new ArrayList<>();

        final String NODE_RESULTS = "results";
        final String TRAILER_KEY = "key";
        final String SITE_KEY = "site";
        final String TYPE_KEY = "type";

        JSONObject movieJson = new JSONObject(urlReturns);
        JSONArray resultsArray = movieJson.getJSONArray(NODE_RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {

            JSONObject individualMovie = resultsArray.getJSONObject(i);

            String type = individualMovie.getString(TYPE_KEY);
            String site = individualMovie.getString(SITE_KEY);
            String trailerKey = individualMovie.getString(TRAILER_KEY);

            if(type.equals("Trailer")){
                if(site.equals("YouTube")){
                    trailers.add(trailerKey);
                }
            }

        }

        return trailers;
    }

}
