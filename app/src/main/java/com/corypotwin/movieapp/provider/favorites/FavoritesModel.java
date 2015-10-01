package com.corypotwin.movieapp.provider.favorites;

import com.corypotwin.movieapp.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A users favorite movies
 */
public interface FavoritesModel extends BaseModel {

    /**
     * Get the {@code db_id} value.
     */
    long getDbId();

    /**
     * Get the {@code movie_name} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getMovieName();

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReleaseDate();

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    String getRating();

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * Get the {@code poster_url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPosterUrl();
}
