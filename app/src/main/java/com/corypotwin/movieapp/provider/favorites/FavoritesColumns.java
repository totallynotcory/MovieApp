package com.corypotwin.movieapp.provider.favorites;

import android.net.Uri;
import android.provider.BaseColumns;

import com.corypotwin.movieapp.provider.FavoritesProvider;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;
import com.corypotwin.movieapp.provider.reviews.ReviewsColumns;
import com.corypotwin.movieapp.provider.trailers.TrailersColumns;

/**
 * A users favorite movies
 */
public class FavoritesColumns implements BaseColumns {
    public static final String TABLE_NAME = "favorites";
    public static final Uri CONTENT_URI = Uri.parse(FavoritesProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String DB_ID = "db_id";

    public static final String MOVIE_NAME = "movie_name";

    public static final String RELEASE_DATE = "release_date";

    public static final String RATING = "rating";

    public static final String DESCRIPTION = "description";

    public static final String POSTER_URL = "poster_url";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            DB_ID,
            MOVIE_NAME,
            RELEASE_DATE,
            RATING,
            DESCRIPTION,
            POSTER_URL
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(DB_ID) || c.contains("." + DB_ID)) return true;
            if (c.equals(MOVIE_NAME) || c.contains("." + MOVIE_NAME)) return true;
            if (c.equals(RELEASE_DATE) || c.contains("." + RELEASE_DATE)) return true;
            if (c.equals(RATING) || c.contains("." + RATING)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(POSTER_URL) || c.contains("." + POSTER_URL)) return true;
        }
        return false;
    }

}
