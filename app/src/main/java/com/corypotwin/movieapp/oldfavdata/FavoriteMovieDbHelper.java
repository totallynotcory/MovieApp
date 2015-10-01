package com.corypotwin.movieapp.oldfavdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.corypotwin.movieapp.oldfavdata.FavoriteMovieContract.FavoriteMovieEntry;

/**
 * Class to construct the favorite movie database for users
 */
public class FavoriteMovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "favoritemovies.db";

    public FavoriteMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITEMOVIES_TABLE = "CREATE TABLE " + FavoriteMovieEntry.TABLE_NAME + " (" +
                // Autoincrement is a questionable choice
                // TODO do we really want an id that's auto-increment?
                FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                FavoriteMovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                FavoriteMovieEntry.COLUMN_RATING + " REAL NOT NULL, " +
                FavoriteMovieEntry.COLUMN_DESC + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_TRAILER + " TEXT NOT NULL, " +
                FavoriteMovieEntry.COLUMN_REVIEWS + " TEXT NOT NULL, " +

                // We want to make sure the same movie doesn't get added twice, but also need to
                // make sure it's the same movie.  We're running out of titles for movies it seems
                " UNIQUE (" + FavoriteMovieEntry.COLUMN_TITLE + ", " +
                FavoriteMovieEntry.COLUMN_DATE + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITEMOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onCreate(sqLiteDatabase);
    }


}
