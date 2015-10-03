package com.corypotwin.movieapp.customdata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Parcelable to hold reviews.
 */
public class Review implements Parcelable {

    private String review;
    private String author;

    public Review(String review, String author){
        this.review = review;
        this.author = author;
    }

    // This is the necessary bits for making a parcelable.  Yeehaw!

    public static final Parcelable.Creator<Review> CREATOR
            = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {

        out.writeString(review);
        out.writeString(author);

    }

    private Review(Parcel in) {
        review = in.readString();
        author = in.readString();

    }

    // Setters and Getters

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
