package com.corypotwin.movieapp.provider.reviews;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.corypotwin.movieapp.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code reviews} table.
 */
public class ReviewsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ReviewsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ReviewsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ReviewsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Optional review.
     */
    public ReviewsContentValues putMovieId(long value) {
        mContentValues.put(ReviewsColumns.MOVIE_ID, value);
        return this;
    }


    public ReviewsContentValues putReview(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("review must not be null");
        mContentValues.put(ReviewsColumns.REVIEW, value);
        return this;
    }


    public ReviewsContentValues putAuthor(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("author must not be null");
        mContentValues.put(ReviewsColumns.AUTHOR, value);
        return this;
    }

}
