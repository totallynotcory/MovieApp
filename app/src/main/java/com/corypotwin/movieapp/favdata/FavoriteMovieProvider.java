package com.corypotwin.movieapp.favdata;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Class to access Favorite Movie Database.  Originally used for com.corypotwin.movieapp
 */
public class FavoriteMovieProvider extends ContentProvider{

    public FavoriteMovieDbHelper favMovDatabase;

    public boolean onCreate() {
        favMovDatabase = new FavoriteMovieDbHelper(getContext());
        return true;
    }

    public String getType(Uri uri) {
        return FavoriteMovieContract.FavoriteMovieEntry.CONTENT_TYPE;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        Cursor favMovCursor;

        favMovCursor = favMovDatabase.getReadableDatabase().query(
                FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        favMovCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return favMovCursor;

    }

    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = favMovDatabase.getWritableDatabase();

        Uri returnUri;

        long _id = db.insert(FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME, null, values);
        if (_id > 0) {
            returnUri = FavoriteMovieContract.FavoriteMovieEntry.buildFavoriteMoviesUri(_id);
        } else {
            throw new android.database.SQLException("Failed to insert row into " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = favMovDatabase.getWritableDatabase();
        int rowsDeleted;

        // this makes delete all rows return the number of rows deleted
        if(null == selection) {
            selection = "1";
        }

        rowsDeleted = db.delete(
            FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME, selection, selectionArgs);

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;

    }

    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = favMovDatabase.getWritableDatabase();
        int rowsUpdated;

        rowsUpdated = db.update(FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                values, selection, selectionArgs);

        if(rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

}
