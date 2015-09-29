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

import com.corypotwin.movieapp.R;
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
public class DetailDataFetcher extends AsyncTask<Void, Void, Integer> {

    private final String LOG_TAG = "DetailDataFetcher";

    private int movieId;
    private String reviewUrl;
    private String trailerUrl;
    private Context mContext;
    private View mRootView;

    private ReviewsAndTrailers revsAndTrailsForThisMovie;

    public DetailDataFetcher(int id, String rUrl, String tUrl, Context context, View rootView)
            throws MalformedURLException{
        movieId = id;
        reviewUrl = rUrl;
        trailerUrl = tUrl;
        mContext = context;
        mRootView = rootView;
    }

    protected Integer doInBackground(Void... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String reviewJsonStr = "";
        String trailerJsonStr = "";

        List<List<String>> reviews = new ArrayList<>();
        List<String> trailers = new ArrayList<>();

        try {
            URL reviewUrlUrl = new URL(reviewUrl);
            UrlConnector reviewConnection = new UrlConnector(reviewUrlUrl, LOG_TAG);
            if (reviewConnection.isNetWorkAvailable(mContext)) {
                reviewJsonStr = reviewConnection.connectAndGetJson();
            }
        } catch(MalformedURLException e){
            Log.e(LOG_TAG, "Error when creating URL");
        }

        try {
            URL trailerUrlUrl = new URL(trailerUrl);
            UrlConnector trailerConnection = new UrlConnector(trailerUrlUrl, LOG_TAG);
            if (trailerConnection.isNetWorkAvailable(mContext)) {
                trailerJsonStr = trailerConnection.connectAndGetJson();
            }
        }    catch(MalformedURLException e){
                Log.e(LOG_TAG, "Error when creating URL");
        }



        try {
            reviews = getReviewDataFromJson(reviewJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Something went wrong parsing review JSON");
        }


        try {
            trailers = getTrailerDataFromJson(trailerJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Something went wrong parsing trailer JSON");
        }

        revsAndTrailsForThisMovie = new ReviewsAndTrailers(reviews, trailers);
        return 1;

    }

    protected void onPostExecute(Integer one){

        // Add trailers if we've got any
        if (revsAndTrailsForThisMovie.hasTrailers()){

            String youtubeBaseVideoUrl = "https://www.youtube.com/watch?v=";
            String youtubeBaseJpgUrl = "http://img.youtube.com/vi/";

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
                        placeholder(R.drawable.no_image_available_white).
                        error(R.drawable.no_image_available_black).
                        into(image);

                image.setImageResource(R.drawable.no_image_available_black);
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

        // Add reviews if we have any
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

        public int reviewsSize(){
            return reviews.size();
        }

        public boolean hasReviews() {
            if(this.getReviews().size() > 0) {
                return true;
            } else {
                return false;
            }
        }

        public List<String> getTrailers() {
            return trailers;
        }

        public void setTrailers(List<String> trailers) {
            this.trailers = trailers;
        }

        public int trailerSize(){
            return trailers.size();
        }

        public boolean hasTrailers() {
            if(this.getTrailers().size() > 0) {
                return true;
            } else {
                return false;
            }
        }


    }
}
