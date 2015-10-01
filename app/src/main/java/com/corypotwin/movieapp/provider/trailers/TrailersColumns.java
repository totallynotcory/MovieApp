package com.corypotwin.movieapp.provider.trailers;

import android.net.Uri;
import android.provider.BaseColumns;

import com.corypotwin.movieapp.provider.FavoritesProvider;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;
import com.corypotwin.movieapp.provider.reviews.ReviewsColumns;
import com.corypotwin.movieapp.provider.trailers.TrailersColumns;

/**
 * Trailers for movies.
 */
public class TrailersColumns implements BaseColumns {
    public static final String TABLE_NAME = "trailers";
    public static final Uri CONTENT_URI = Uri.parse(FavoritesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * Optional trailer.
     */
    public static final String MOVIE_ID = "movie_id";

    public static final String TRAILER_YOUTUBE_ID = "trailer_youtube_id";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MOVIE_ID,
            TRAILER_YOUTUBE_ID
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(TRAILER_YOUTUBE_ID) || c.contains("." + TRAILER_YOUTUBE_ID)) return true;
        }
        return false;
    }

    public static final String PREFIX_FAVORITES = TABLE_NAME + "__" + FavoritesColumns.TABLE_NAME;
}
