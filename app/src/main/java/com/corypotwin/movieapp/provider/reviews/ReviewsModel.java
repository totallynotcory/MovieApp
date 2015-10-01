package com.corypotwin.movieapp.provider.reviews;

import com.corypotwin.movieapp.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Reviews for movies.
 */
public interface ReviewsModel extends BaseModel {

    /**
     * Optional review.
     */
    long getMovieId();

    /**
     * Get the {@code review} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getReview();

    /**
     * Get the {@code author} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getAuthor();
}
