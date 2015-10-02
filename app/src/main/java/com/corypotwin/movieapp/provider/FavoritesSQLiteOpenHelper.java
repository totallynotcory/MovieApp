package com.corypotwin.movieapp.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.corypotwin.movieapp.BuildConfig;
import com.corypotwin.movieapp.provider.favorites.FavoritesColumns;

public class FavoritesSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = FavoritesSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 2;
    private static FavoritesSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final FavoritesSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_FAVORITES = "CREATE TABLE IF NOT EXISTS "
            + FavoritesColumns.TABLE_NAME + " ( "
            + FavoritesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavoritesColumns.DB_ID + " INTEGER NOT NULL, "
            + FavoritesColumns.MOVIE_NAME + " TEXT NOT NULL, "
            + FavoritesColumns.RELEASE_DATE + " TEXT, "
            + FavoritesColumns.RATING + " TEXT, "
            + FavoritesColumns.DESCRIPTION + " TEXT, "
            + FavoritesColumns.POSTER_URL + " TEXT, "
            + FavoritesColumns.REVIEWS_URL + " TEXT, "
            + FavoritesColumns.TRAILERS_URL + " TEXT "
            + ", CONSTRAINT unique_id UNIQUE (db_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_INDEX_FAVORITES_DB_ID = "CREATE INDEX IDX_FAVORITES_DB_ID "
            + " ON " + FavoritesColumns.TABLE_NAME + " ( " + FavoritesColumns.DB_ID + " );";

    public static final String SQL_CREATE_INDEX_FAVORITES_MOVIE_NAME = "CREATE INDEX IDX_FAVORITES_MOVIE_NAME "
            + " ON " + FavoritesColumns.TABLE_NAME + " ( " + FavoritesColumns.MOVIE_NAME + " );";

    // @formatter:on

    public static FavoritesSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static FavoritesSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static FavoritesSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new FavoritesSQLiteOpenHelper(context);
    }

    private FavoritesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new FavoritesSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static FavoritesSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new FavoritesSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private FavoritesSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new FavoritesSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_FAVORITES);
        db.execSQL(SQL_CREATE_INDEX_FAVORITES_DB_ID);
        db.execSQL(SQL_CREATE_INDEX_FAVORITES_MOVIE_NAME);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
