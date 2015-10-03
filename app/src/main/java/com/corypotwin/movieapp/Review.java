package com.corypotwin.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ctpotwin on 10/3/15.
 */
public class Review implements Parcelable {

    // ids returned from the movie db are currently 6 digits long, seems ok for an int.
    // If 1.9+ billion more movies are released, please change this to a long.

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
