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

    private int movieId;
    private String reviewUrl;
    private String trailerUrl;
    private Context mContext;
    private View mRootView;
    private MovieDetailsFragment mMdf;

    private String youtubeBaseVideoUrl = "https://www.youtube.com/watch?v=";
    private String youtubeBaseJpgUrl = "http://img.youtube.com/vi/";

    public DetailDataFetcher(int id, String rUrl, String tUrl, Context context, View rootView,
                             MovieDetailsFragment mdf)
            throws MalformedURLException{
        movieId = id;
        reviewUrl = rUrl;
        trailerUrl = tUrl;
        mContext = context;
        mRootView = rootView;
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

        List<List<String>> reviews = new ArrayList<>();
        List<String> trailers = new ArrayList<>();

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

        mMdf.setmRevsAndTrails(revsAndTrailsForThisMovie);

        // Add trailers to the detail review if any exist.
        if (revsAndTrailsForThisMovie.hasTrailers()){

            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            List<String> allTrailers = revsAndTrailsForThisMovie.getTrailers();

            TextView trailersHeader = (TextView) mRootView.findViewById(R.id.trailer_header);
            trailersHeader.setText(mContext.getString(R.string.trailers_header));

            ViewGroup insertPoint = (ViewGroup) mRootView.findViewById(R.id.trailers_container);

            for (int i = 0; i < revsAndTrailsForThisMovie.trailerSize(); i++) {

                View trailerView = vi.inflate(R.layout.trailer_item, null);

                ImageView image = (ImageView) trailerView.findViewById(R.id.trailer_image);
                TextView text = (TextView) trailerView.findViewById(R.id.trailer_title);

                String imageUrl = youtubeBaseJpgUrl +
                        revsAndTrailsForThisMovie.getTrailers().get(i) + "/0.jpg";

                Picasso.with(mContext).
                        load(imageUrl).
                        memoryPolicy(MemoryPolicy.NO_CACHE).
                        networkPolicy(NetworkPolicy.NO_CACHE).
                        placeholder(R.drawable.no_image_available_black).
                        error(R.drawable.no_image_available_black).
                        into(image);

                text.setText("Trailer " + (i + 1));

                final Uri youtubeUrl = Uri.parse(youtubeBaseVideoUrl +
                        revsAndTrailsForThisMovie.getTrailers().get(i));

                // Setup trailers so each points to the corresponding Youtube link
                trailerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent videoIntent =new Intent(Intent.ACTION_VIEW);
                        videoIntent.setData(youtubeUrl);

                        if (videoIntent.resolveActivity(mContext.getPackageManager()) != null) {
                            mContext.startActivity(videoIntent);
                        } else {
                            Log.d(LOG_TAG, "Couldn't call " + youtubeUrl + ", no receiving apps installed!");
                        }
                    }
                });

                insertPoint.addView(trailerView, i, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
        }

        // Add reviews to the detail view if any exist
        if (revsAndTrailsForThisMovie.hasReviews()){

            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            List<List<String>> allReviews = revsAndTrailsForThisMovie.getReviews();

            TextView reviewHeader = (TextView) mRootView.findViewById(R.id.reviews_header);
            reviewHeader.setText(mContext.getString(R.string.reviews_header));

            ViewGroup insertPoint = (ViewGroup) mRootView.findViewById(R.id.reviews_container);

            for (int i = 0; i < revsAndTrailsForThisMovie.reviewsSize(); i++) {

                View reviewView = vi.inflate(R.layout.review_item, null);

                TextView review = (TextView) reviewView.findViewById(R.id.review_body);
                TextView reviewer = (TextView) reviewView.findViewById(R.id.reviewer_name);

                review.setText(allReviews.get(i).get(0));
                reviewer.setText("-" + allReviews.get(i).get(1));

                insertPoint.addView(reviewView, i, new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
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

    /**
     * Parses the Trailer Data from the JSON string returned in the DetailDataFetcher
     * from the database
     *
     * @param urlReturns - JSON string returned from themovedb of trailer data
     * @return - A list containing the youtube identifier.
     * @throws JSONException
     * */
    private List<String> getTrailerDataFromJson(String urlReturns) throws JSONException {

        List<String> trailers = new ArrayList<>();

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
