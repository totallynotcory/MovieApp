package com.corypotwin.movieapp.provider.trailers;

import com.corypotwin.movieapp.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Trailers for movies.
 */
public interface TrailersModel extends BaseModel {

    /**
     * Optional trailer.
     */
    long getMovieId();

    /**
     * Get the {@code trailer_youtube_id} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTrailerYoutubeId();
}
