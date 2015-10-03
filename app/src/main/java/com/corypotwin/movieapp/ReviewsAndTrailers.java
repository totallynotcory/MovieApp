package com.corypotwin.movieapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ctpotwin on 9/30/15.
 */

public class ReviewsAndTrailers {

    private ArrayList<Review> reviews;
    private ArrayList<String> trailers;

    public ReviewsAndTrailers(ArrayList<Review> revs, ArrayList<String> trails){
        reviews = revs;
        trailers = trails;
    }

    // Review Related Functions
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
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

    // Trailer related functions
    public ArrayList<String> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<String> trailers) {
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
