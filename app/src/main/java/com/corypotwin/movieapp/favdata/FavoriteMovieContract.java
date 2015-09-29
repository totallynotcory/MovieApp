package com.corypotwin.movieapp.favdata;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the weather database.
 */
public class FavoriteMovieContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.corypotwin.movieapp";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.corypotwin.movieapp/favorites
    public static final String PATH_FAVORITES = "favorites";

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }


    /* Inner class that defines the table contents of the FavoriteMovie table */
    public static final class FavoriteMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

        public static final String TABLE_NAME = "favoritemovies";

        // id of the Movie
        public static final String COLUMN_ID = "id";
        // Title of the movie.
        public static final String COLUMN_TITLE = "title";
        // Release date of the movie
        public static final String COLUMN_DATE = "date";
        // Movie rating
        public static final String COLUMN_RATING = "rating";

        // Description of the movie
        public static final String COLUMN_DESC = "desc";

        // URL/Location of the image
        public static final String COLUMN_IMAGE = "image";

        // URL/Location of the trailer
        public static final String COLUMN_TRAILER = "trailer";

        // Humidity is stored as a float representing percentage
        public static final String COLUMN_REVIEWS = "reviews";

        public static Uri buildFavoriteMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getTitleFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildTitleUri(String movieTitle) {
            return CONTENT_URI.buildUpon().appendPath(movieTitle).build();
        }

        public static long getDateFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static long getRatingFromUri(Uri uri){
            return Long.parseLong(uri.getPathSegments().get(3));
        }

    }


}