package com.corypotwin.movieapp.provider.reviews;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.corypotwin.movieapp.provider.base.AbstractCursor;
import com.corypotwin.movieapp.provider.favorites.*;

/**
 * Cursor wrapper for the {@code reviews} table.
 */
public class ReviewsCursor extends AbstractCursor implements ReviewsModel {
    public ReviewsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ReviewsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Optional review.
     */
    public long getMovieId() {
        Long res = getLongOrNull(ReviewsColumns.MOVIE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'movie_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code db_id} value.
     */
    public long getFavoritesDbId() {
        Long res = getLongOrNull(FavoritesColumns.DB_ID);
        if (res == null)
            throw new NullPointerException("The value of 'db_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code movie_name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getFavoritesMovieName() {
        String res = getStringOrNull(FavoritesColumns.MOVIE_NAME);
        if (res == null)
            throw new NullPointerException("The value of 'movie_name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFavoritesReleaseDate() {
        String res = getStringOrNull(FavoritesColumns.RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFavoritesRating() {
        String res = getStringOrNull(FavoritesColumns.RATING);
        return res;
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFavoritesDescription() {
        String res = getStringOrNull(FavoritesColumns.DESCRIPTION);
        return res;
    }

    /**
     * Get the {@code poster_url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFavoritesPosterUrl() {
        String res = getStringOrNull(FavoritesColumns.POSTER_URL);
        return res;
    }

    /**
     * Get the {@code review} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getReview() {
        String res = getStringOrNull(ReviewsColumns.REVIEW);
        if (res == null)
            throw new NullPointerException("The value of 'review' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code author} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getAuthor() {
        String res = getStringOrNull(ReviewsColumns.AUTHOR);
        if (res == null)
            throw new NullPointerException("The value of 'author' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
