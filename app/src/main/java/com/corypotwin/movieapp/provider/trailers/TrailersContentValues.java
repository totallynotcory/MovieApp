package com.corypotwin.movieapp.provider.trailers;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.corypotwin.movieapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code trailers} table.
 */
public class TrailersContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TrailersColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable TrailersSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable TrailersSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Optional trailer.
     */
    public TrailersContentValues putMovieId(long value) {
        mContentValues.put(TrailersColumns.MOVIE_ID, value);
        return this;
    }


    public TrailersContentValues putTrailerYoutubeId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("trailerYoutubeId must not be null");
        mContentValues.put(TrailersColumns.TRAILER_YOUTUBE_ID, value);
        return this;
    }

}
