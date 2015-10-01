package com.corypotwin.movieapp.provider.favorites;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.corypotwin.movieapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code favorites} table.
 */
public class FavoritesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FavoritesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable FavoritesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable FavoritesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public FavoritesContentValues putDbId(long value) {
        mContentValues.put(FavoritesColumns.DB_ID, value);
        return this;
    }


    public FavoritesContentValues putMovieName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("movieName must not be null");
        mContentValues.put(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }


    public FavoritesContentValues putReleaseDate(@Nullable String value) {
        mContentValues.put(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesContentValues putReleaseDateNull() {
        mContentValues.putNull(FavoritesColumns.RELEASE_DATE);
        return this;
    }

    public FavoritesContentValues putRating(@Nullable String value) {
        mContentValues.put(FavoritesColumns.RATING, value);
        return this;
    }

    public FavoritesContentValues putRatingNull() {
        mContentValues.putNull(FavoritesColumns.RATING);
        return this;
    }

    public FavoritesContentValues putDescription(@Nullable String value) {
        mContentValues.put(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public FavoritesContentValues putDescriptionNull() {
        mContentValues.putNull(FavoritesColumns.DESCRIPTION);
        return this;
    }

    public FavoritesContentValues putPosterUrl(@Nullable String value) {
        mContentValues.put(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public FavoritesContentValues putPosterUrlNull() {
        mContentValues.putNull(FavoritesColumns.POSTER_URL);
        return this;
    }
}
