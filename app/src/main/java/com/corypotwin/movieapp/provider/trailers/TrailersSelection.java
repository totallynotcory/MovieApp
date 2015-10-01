package com.corypotwin.movieapp.provider.trailers;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.corypotwin.movieapp.provider.base.AbstractSelection;
import com.corypotwin.movieapp.provider.favorites.*;

/**
 * Selection for the {@code trailers} table.
 */
public class TrailersSelection extends AbstractSelection<TrailersSelection> {
    @Override
    protected Uri baseUri() {
        return TrailersColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailersCursor} object, which is positioned before the first entry, or null.
     */
    public TrailersCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailersCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public TrailersCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailersCursor} object, which is positioned before the first entry, or null.
     */
    public TrailersCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailersCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public TrailersCursor query(Context context) {
        return query(context, null);
    }


    public TrailersSelection id(long... value) {
        addEquals("trailers." + TrailersColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection idNot(long... value) {
        addNotEquals("trailers." + TrailersColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection orderById(boolean desc) {
        orderBy("trailers." + TrailersColumns._ID, desc);
        return this;
    }

    public TrailersSelection orderById() {
        return orderById(false);
    }

    public TrailersSelection movieId(long... value) {
        addEquals(TrailersColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection movieIdNot(long... value) {
        addNotEquals(TrailersColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection movieIdGt(long value) {
        addGreaterThan(TrailersColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection movieIdGtEq(long value) {
        addGreaterThanOrEquals(TrailersColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection movieIdLt(long value) {
        addLessThan(TrailersColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection movieIdLtEq(long value) {
        addLessThanOrEquals(TrailersColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection orderByMovieId(boolean desc) {
        orderBy(TrailersColumns.MOVIE_ID, desc);
        return this;
    }

    public TrailersSelection orderByMovieId() {
        orderBy(TrailersColumns.MOVIE_ID, false);
        return this;
    }

    public TrailersSelection favoritesDbId(long... value) {
        addEquals(FavoritesColumns.DB_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection favoritesDbIdNot(long... value) {
        addNotEquals(FavoritesColumns.DB_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection favoritesDbIdGt(long value) {
        addGreaterThan(FavoritesColumns.DB_ID, value);
        return this;
    }

    public TrailersSelection favoritesDbIdGtEq(long value) {
        addGreaterThanOrEquals(FavoritesColumns.DB_ID, value);
        return this;
    }

    public TrailersSelection favoritesDbIdLt(long value) {
        addLessThan(FavoritesColumns.DB_ID, value);
        return this;
    }

    public TrailersSelection favoritesDbIdLtEq(long value) {
        addLessThanOrEquals(FavoritesColumns.DB_ID, value);
        return this;
    }

    public TrailersSelection orderByFavoritesDbId(boolean desc) {
        orderBy(FavoritesColumns.DB_ID, desc);
        return this;
    }

    public TrailersSelection orderByFavoritesDbId() {
        orderBy(FavoritesColumns.DB_ID, false);
        return this;
    }

    public TrailersSelection favoritesMovieName(String... value) {
        addEquals(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public TrailersSelection favoritesMovieNameNot(String... value) {
        addNotEquals(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public TrailersSelection favoritesMovieNameLike(String... value) {
        addLike(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public TrailersSelection favoritesMovieNameContains(String... value) {
        addContains(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public TrailersSelection favoritesMovieNameStartsWith(String... value) {
        addStartsWith(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public TrailersSelection favoritesMovieNameEndsWith(String... value) {
        addEndsWith(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public TrailersSelection orderByFavoritesMovieName(boolean desc) {
        orderBy(FavoritesColumns.MOVIE_NAME, desc);
        return this;
    }

    public TrailersSelection orderByFavoritesMovieName() {
        orderBy(FavoritesColumns.MOVIE_NAME, false);
        return this;
    }

    public TrailersSelection favoritesReleaseDate(String... value) {
        addEquals(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection favoritesReleaseDateNot(String... value) {
        addNotEquals(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection favoritesReleaseDateLike(String... value) {
        addLike(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection favoritesReleaseDateContains(String... value) {
        addContains(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection favoritesReleaseDateStartsWith(String... value) {
        addStartsWith(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection favoritesReleaseDateEndsWith(String... value) {
        addEndsWith(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection orderByFavoritesReleaseDate(boolean desc) {
        orderBy(FavoritesColumns.RELEASE_DATE, desc);
        return this;
    }

    public TrailersSelection orderByFavoritesReleaseDate() {
        orderBy(FavoritesColumns.RELEASE_DATE, false);
        return this;
    }

    public TrailersSelection favoritesRating(String... value) {
        addEquals(FavoritesColumns.RATING, value);
        return this;
    }

    public TrailersSelection favoritesRatingNot(String... value) {
        addNotEquals(FavoritesColumns.RATING, value);
        return this;
    }

    public TrailersSelection favoritesRatingLike(String... value) {
        addLike(FavoritesColumns.RATING, value);
        return this;
    }

    public TrailersSelection favoritesRatingContains(String... value) {
        addContains(FavoritesColumns.RATING, value);
        return this;
    }

    public TrailersSelection favoritesRatingStartsWith(String... value) {
        addStartsWith(FavoritesColumns.RATING, value);
        return this;
    }

    public TrailersSelection favoritesRatingEndsWith(String... value) {
        addEndsWith(FavoritesColumns.RATING, value);
        return this;
    }

    public TrailersSelection orderByFavoritesRating(boolean desc) {
        orderBy(FavoritesColumns.RATING, desc);
        return this;
    }

    public TrailersSelection orderByFavoritesRating() {
        orderBy(FavoritesColumns.RATING, false);
        return this;
    }

    public TrailersSelection favoritesDescription(String... value) {
        addEquals(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public TrailersSelection favoritesDescriptionNot(String... value) {
        addNotEquals(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public TrailersSelection favoritesDescriptionLike(String... value) {
        addLike(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public TrailersSelection favoritesDescriptionContains(String... value) {
        addContains(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public TrailersSelection favoritesDescriptionStartsWith(String... value) {
        addStartsWith(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public TrailersSelection favoritesDescriptionEndsWith(String... value) {
        addEndsWith(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public TrailersSelection orderByFavoritesDescription(boolean desc) {
        orderBy(FavoritesColumns.DESCRIPTION, desc);
        return this;
    }

    public TrailersSelection orderByFavoritesDescription() {
        orderBy(FavoritesColumns.DESCRIPTION, false);
        return this;
    }

    public TrailersSelection favoritesPosterUrl(String... value) {
        addEquals(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public TrailersSelection favoritesPosterUrlNot(String... value) {
        addNotEquals(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public TrailersSelection favoritesPosterUrlLike(String... value) {
        addLike(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public TrailersSelection favoritesPosterUrlContains(String... value) {
        addContains(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public TrailersSelection favoritesPosterUrlStartsWith(String... value) {
        addStartsWith(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public TrailersSelection favoritesPosterUrlEndsWith(String... value) {
        addEndsWith(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public TrailersSelection orderByFavoritesPosterUrl(boolean desc) {
        orderBy(FavoritesColumns.POSTER_URL, desc);
        return this;
    }

    public TrailersSelection orderByFavoritesPosterUrl() {
        orderBy(FavoritesColumns.POSTER_URL, false);
        return this;
    }

    public TrailersSelection trailerYoutubeId(String... value) {
        addEquals(TrailersColumns.TRAILER_YOUTUBE_ID, value);
        return this;
    }

    public TrailersSelection trailerYoutubeIdNot(String... value) {
        addNotEquals(TrailersColumns.TRAILER_YOUTUBE_ID, value);
        return this;
    }

    public TrailersSelection trailerYoutubeIdLike(String... value) {
        addLike(TrailersColumns.TRAILER_YOUTUBE_ID, value);
        return this;
    }

    public TrailersSelection trailerYoutubeIdContains(String... value) {
        addContains(TrailersColumns.TRAILER_YOUTUBE_ID, value);
        return this;
    }

    public TrailersSelection trailerYoutubeIdStartsWith(String... value) {
        addStartsWith(TrailersColumns.TRAILER_YOUTUBE_ID, value);
        return this;
    }

    public TrailersSelection trailerYoutubeIdEndsWith(String... value) {
        addEndsWith(TrailersColumns.TRAILER_YOUTUBE_ID, value);
        return this;
    }

    public TrailersSelection orderByTrailerYoutubeId(boolean desc) {
        orderBy(TrailersColumns.TRAILER_YOUTUBE_ID, desc);
        return this;
    }

    public TrailersSelection orderByTrailerYoutubeId() {
        orderBy(TrailersColumns.TRAILER_YOUTUBE_ID, false);
        return this;
    }
}
