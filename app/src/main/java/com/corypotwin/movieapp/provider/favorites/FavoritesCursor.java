package com.corypotwin.movieapp.provider.favorites;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.corypotwin.movieapp.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code favorites} table.
 */
public class FavoritesCursor extends AbstractCursor implements FavoritesModel {
    public FavoritesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(FavoritesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code db_id} value.
     */
    public long getDbId() {
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
    public String getMovieName() {
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
    public String getReleaseDate() {
        String res = getStringOrNull(FavoritesColumns.RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getRating() {
        String res = getStringOrNull(FavoritesColumns.RATING);
        return res;
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(FavoritesColumns.DESCRIPTION);
        return res;
    }

    /**
     * Get the {@code poster_url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPosterUrl() {
        String res = getStringOrNull(FavoritesColumns.POSTER_URL);
        return res;
    }
}
