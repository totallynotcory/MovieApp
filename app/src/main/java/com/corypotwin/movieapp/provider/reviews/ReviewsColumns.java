package com.corypotwin.movieapp.provider.reviews;

import android.net.Uri;
import android.provider.BaseColumns;

import com.corypotwin.movieapp.provider.FavoritesProvider;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;
import com.corypotwin.movieapp.provider.reviews.ReviewsColumns;
import com.corypotwin.movieapp.provider.trailers.TrailersColumns;

/**
 * Reviews for movies.
 */
public class ReviewsColumns implements BaseColumns {
    public static final String TABLE_NAME = "reviews";
    public static final Uri CONTENT_URI = Uri.parse(FavoritesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Optional review.
     */
    public static final String MOVIE_ID = "movie_id";

    public static final String REVIEW = "review";

    public static final String AUTHOR = "author";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MOVIE_ID,
            REVIEW,
            AUTHOR
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(REVIEW) || c.contains("." + REVIEW)) return true;
            if (c.equals(AUTHOR) || c.contains("." + AUTHOR)) return true;
        }
        return false;
    }

    public static final String PREFIX_FAVORITES = TABLE_NAME + "__" + FavoritesColumns.TABLE_NAME;
}
