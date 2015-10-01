package com.corypotwin.movieapp.provider.trailers;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.corypotwin.movieapp.provider.base.AbstractCursor;
import com.corypotwin.movieapp.provider.favorites.*;

/**
 * Cursor wrapper for the {@code trailers} table.
 */
public class TrailersCursor extends AbstractCursor implements TrailersModel {
    public TrailersCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(TrailersColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Optional trailer.
     */
    public long getMovieId() {
        Long res = getLongOrNull(TrailersColumns.MOVIE_ID);
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
     * Get the {@code trailer_youtube_id} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTrailerYoutubeId() {
        String res = getStringOrNull(TrailersColumns.TRAILER_YOUTUBE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'trailer_youtube_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
