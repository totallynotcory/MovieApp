package com.corypotwin.movieapp;

import java.util.List;

/**
 * Created by ctpotwin on 9/30/15.
 */

public class ReviewsAndTrailers {

    private List<List<String>> reviews;
    private List<String> trailers;

    public ReviewsAndTrailers(List<List<String>> revs, List<String> trails){
        reviews = revs;
        trailers = trails;
    }

    // Review Related Functions
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

    // Trailer related functions
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
