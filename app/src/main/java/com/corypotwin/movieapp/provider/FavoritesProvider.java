package com.corypotwin.movieapp.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.corypotwin.movieapp.BuildConfig;
import com.corypotwin.movieapp.provider.base.BaseContentProvider;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;
import com.corypotwin.movieapp.provider.reviews.ReviewsColumns;
import com.corypotwin.movieapp.provider.trailers.TrailersColumns;

public class FavoritesProvider extends BaseContentProvider {
    private static final String TAG = FavoritesProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.corypotwin.movieapp.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_FAVORITES = 0;
    private static final int URI_TYPE_FAVORITES_ID = 1;

    private static final int URI_TYPE_REVIEWS = 2;
    private static final int URI_TYPE_REVIEWS_ID = 3;

    private static final int URI_TYPE_TRAILERS = 4;
    private static final int URI_TYPE_TRAILERS_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, FavoritesColumns.TABLE_NAME, URI_TYPE_FAVORITES);
        URI_MATCHER.addURI(AUTHORITY, FavoritesColumns.TABLE_NAME + "/#", URI_TYPE_FAVORITES_ID);
        URI_MATCHER.addURI(AUTHORITY, ReviewsColumns.TABLE_NAME, URI_TYPE_REVIEWS);
        URI_MATCHER.addURI(AUTHORITY, ReviewsColumns.TABLE_NAME + "/#", URI_TYPE_REVIEWS_ID);
        URI_MATCHER.addURI(AUTHORITY, TrailersColumns.TABLE_NAME, URI_TYPE_TRAILERS);
        URI_MATCHER.addURI(AUTHORITY, TrailersColumns.TABLE_NAME + "/#", URI_TYPE_TRAILERS_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return FavoritesSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_FAVORITES:
                return TYPE_CURSOR_DIR + FavoritesColumns.TABLE_NAME;
            case URI_TYPE_FAVORITES_ID:
                return TYPE_CURSOR_ITEM + FavoritesColumns.TABLE_NAME;

            case URI_TYPE_REVIEWS:
                return TYPE_CURSOR_DIR + ReviewsColumns.TABLE_NAME;
            case URI_TYPE_REVIEWS_ID:
                return TYPE_CURSOR_ITEM + ReviewsColumns.TABLE_NAME;

            case URI_TYPE_TRAILERS:
                return TYPE_CURSOR_DIR + TrailersColumns.TABLE_NAME;
            case URI_TYPE_TRAILERS_ID:
                return TYPE_CURSOR_ITEM + TrailersColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_FAVORITES:
            case URI_TYPE_FAVORITES_ID:
                res.table = FavoritesColumns.TABLE_NAME;
                res.idColumn = FavoritesColumns._ID;
                res.tablesWithJoins = FavoritesColumns.TABLE_NAME;
                res.orderBy = FavoritesColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_REVIEWS:
            case URI_TYPE_REVIEWS_ID:
                res.table = ReviewsColumns.TABLE_NAME;
                res.idColumn = ReviewsColumns._ID;
                res.tablesWithJoins = ReviewsColumns.TABLE_NAME;
                if (FavoritesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + FavoritesColumns.TABLE_NAME + " AS " + ReviewsColumns.PREFIX_FAVORITES + " ON " + ReviewsColumns.TABLE_NAME + "." + ReviewsColumns.MOVIE_ID + "=" + ReviewsColumns.PREFIX_FAVORITES + "." + FavoritesColumns._ID;
                }
                res.orderBy = ReviewsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TRAILERS:
            case URI_TYPE_TRAILERS_ID:
                res.table = TrailersColumns.TABLE_NAME;
                res.idColumn = TrailersColumns._ID;
                res.tablesWithJoins = TrailersColumns.TABLE_NAME;
                if (FavoritesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + FavoritesColumns.TABLE_NAME + " AS " + TrailersColumns.PREFIX_FAVORITES + " ON " + TrailersColumns.TABLE_NAME + "." + TrailersColumns.MOVIE_ID + "=" + TrailersColumns.PREFIX_FAVORITES + "." + FavoritesColumns._ID;
                }
                res.orderBy = TrailersColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_FAVORITES_ID:
            case URI_TYPE_REVIEWS_ID:
            case URI_TYPE_TRAILERS_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
