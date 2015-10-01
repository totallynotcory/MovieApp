package com.corypotwin.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ctpotwin on 9/26/15.
 */
public class Movie implements Parcelable{

    // ids returned are currently 6 digits long, seems ok for an int.
    // If 1.9+ billion more movies are released, please change this to a long.
    private int mId;

    private String mTitle;
    private String mDescription;
    private String mRating;
    private String mReleaseDate;

    private String mPosterUrl;
    private String mTrailerUrl;
    private String mReviews;

    public Movie(int id, String title, String description, String rating, String releaseDate,
                    String posterUrl){
        mId = id;
        mTitle = title;
        mDescription = description;
        mRating = rating;
        mReleaseDate = releaseDate;
        mPosterUrl = posterUrl;
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mTitle);
        out.writeString(mDescription);
        out.writeString(mRating);
        out.writeString(mReleaseDate);
        out.writeString(mPosterUrl);
        out.writeString(mTrailerUrl);
        out.writeString(mReviews);
    }

    private Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mDescription = in.readString();
        mRating = in.readString();
        mReleaseDate = in.readString();
        mPosterUrl = in.readString();
        mTrailerUrl = in.readString();
        mReviews = in.readString();
    }

    public int getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmPosterUrl() {
        return mPosterUrl;
    }

    public String getmTrailerUrl() {
        return mTrailerUrl;
    }

    public String getmReviews() {
        return mReviews;
    }

    public void setmReviews(String mReviews) {
        this.mReviews = mReviews;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmRating(String mRating) {
        this.mRating = mRating;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public void setmPosterUrl(String mPosterUrl) {
        this.mPosterUrl = mPosterUrl;
    }

    public void setmTrailerUrl(String mTrailerUrl) {
        this.mTrailerUrl = mTrailerUrl;
    }

}
