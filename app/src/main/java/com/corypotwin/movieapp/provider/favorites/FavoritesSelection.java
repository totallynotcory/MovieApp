package com.corypotwin.movieapp.provider.favorites;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.corypotwin.movieapp.provider.base.AbstractSelection;

/**
 * Selection for the {@code favorites} table.
 */
public class FavoritesSelection extends AbstractSelection<FavoritesSelection> {
    @Override
    protected Uri baseUri() {
        return FavoritesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoritesCursor} object, which is positioned before the first entry, or null.
     */
    public FavoritesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoritesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public FavoritesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavoritesCursor} object, which is positioned before the first entry, or null.
     */
    public FavoritesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavoritesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public FavoritesCursor query(Context context) {
        return query(context, null);
    }


    public FavoritesSelection id(long... value) {
        addEquals("favorites." + FavoritesColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoritesSelection idNot(long... value) {
        addNotEquals("favorites." + FavoritesColumns._ID, toObjectArray(value));
        return this;
    }

    public FavoritesSelection orderById(boolean desc) {
        orderBy("favorites." + FavoritesColumns._ID, desc);
        return this;
    }

    public FavoritesSelection orderById() {
        return orderById(false);
    }

    public FavoritesSelection dbId(long... value) {
        addEquals(FavoritesColumns.DB_ID, toObjectArray(value));
        return this;
    }

    public FavoritesSelection dbIdNot(long... value) {
        addNotEquals(FavoritesColumns.DB_ID, toObjectArray(value));
        return this;
    }

    public FavoritesSelection dbIdGt(long value) {
        addGreaterThan(FavoritesColumns.DB_ID, value);
        return this;
    }

    public FavoritesSelection dbIdGtEq(long value) {
        addGreaterThanOrEquals(FavoritesColumns.DB_ID, value);
        return this;
    }

    public FavoritesSelection dbIdLt(long value) {
        addLessThan(FavoritesColumns.DB_ID, value);
        return this;
    }

    public FavoritesSelection dbIdLtEq(long value) {
        addLessThanOrEquals(FavoritesColumns.DB_ID, value);
        return this;
    }

    public FavoritesSelection orderByDbId(boolean desc) {
        orderBy(FavoritesColumns.DB_ID, desc);
        return this;
    }

    public FavoritesSelection orderByDbId() {
        orderBy(FavoritesColumns.DB_ID, false);
        return this;
    }

    public FavoritesSelection movieName(String... value) {
        addEquals(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public FavoritesSelection movieNameNot(String... value) {
        addNotEquals(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public FavoritesSelection movieNameLike(String... value) {
        addLike(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public FavoritesSelection movieNameContains(String... value) {
        addContains(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public FavoritesSelection movieNameStartsWith(String... value) {
        addStartsWith(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public FavoritesSelection movieNameEndsWith(String... value) {
        addEndsWith(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public FavoritesSelection orderByMovieName(boolean desc) {
        orderBy(FavoritesColumns.MOVIE_NAME, desc);
        return this;
    }

    public FavoritesSelection orderByMovieName() {
        orderBy(FavoritesColumns.MOVIE_NAME, false);
        return this;
    }

    public FavoritesSelection releaseDate(String... value) {
        addEquals(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateNot(String... value) {
        addNotEquals(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateLike(String... value) {
        addLike(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateContains(String... value) {
        addContains(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateStartsWith(String... value) {
        addStartsWith(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection releaseDateEndsWith(String... value) {
        addEndsWith(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public FavoritesSelection orderByReleaseDate(boolean desc) {
        orderBy(FavoritesColumns.RELEASE_DATE, desc);
        return this;
    }

    public FavoritesSelection orderByReleaseDate() {
        orderBy(FavoritesColumns.RELEASE_DATE, false);
        return this;
    }

    public FavoritesSelection rating(String... value) {
        addEquals(FavoritesColumns.RATING, value);
        return this;
    }

    public FavoritesSelection ratingNot(String... value) {
        addNotEquals(FavoritesColumns.RATING, value);
        return this;
    }

    public FavoritesSelection ratingLike(String... value) {
        addLike(FavoritesColumns.RATING, value);
        return this;
    }

    public FavoritesSelection ratingContains(String... value) {
        addContains(FavoritesColumns.RATING, value);
        return this;
    }

    public FavoritesSelection ratingStartsWith(String... value) {
        addStartsWith(FavoritesColumns.RATING, value);
        return this;
    }

    public FavoritesSelection ratingEndsWith(String... value) {
        addEndsWith(FavoritesColumns.RATING, value);
        return this;
    }

    public FavoritesSelection orderByRating(boolean desc) {
        orderBy(FavoritesColumns.RATING, desc);
        return this;
    }

    public FavoritesSelection orderByRating() {
        orderBy(FavoritesColumns.RATING, false);
        return this;
    }

    public FavoritesSelection description(String... value) {
        addEquals(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public FavoritesSelection descriptionNot(String... value) {
        addNotEquals(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public FavoritesSelection descriptionLike(String... value) {
        addLike(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public FavoritesSelection descriptionContains(String... value) {
        addContains(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public FavoritesSelection descriptionStartsWith(String... value) {
        addStartsWith(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public FavoritesSelection descriptionEndsWith(String... value) {
        addEndsWith(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public FavoritesSelection orderByDescription(boolean desc) {
        orderBy(FavoritesColumns.DESCRIPTION, desc);
        return this;
    }

    public FavoritesSelection orderByDescription() {
        orderBy(FavoritesColumns.DESCRIPTION, false);
        return this;
    }

    public FavoritesSelection posterUrl(String... value) {
        addEquals(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public FavoritesSelection posterUrlNot(String... value) {
        addNotEquals(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public FavoritesSelection posterUrlLike(String... value) {
        addLike(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public FavoritesSelection posterUrlContains(String... value) {
        addContains(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public FavoritesSelection posterUrlStartsWith(String... value) {
        addStartsWith(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public FavoritesSelection posterUrlEndsWith(String... value) {
        addEndsWith(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public FavoritesSelection orderByPosterUrl(boolean desc) {
        orderBy(FavoritesColumns.POSTER_URL, desc);
        return this;
    }

    public FavoritesSelection orderByPosterUrl() {
        orderBy(FavoritesColumns.POSTER_URL, false);
        return this;
    }

    public FavoritesSelection reviewsUrl(String... value) {
        addEquals(FavoritesColumns.REVIEWS_URL, value);
        return this;
    }

    public FavoritesSelection reviewsUrlNot(String... value) {
        addNotEquals(FavoritesColumns.REVIEWS_URL, value);
        return this;
    }

    public FavoritesSelection reviewsUrlLike(String... value) {
        addLike(FavoritesColumns.REVIEWS_URL, value);
        return this;
    }

    public FavoritesSelection reviewsUrlContains(String... value) {
        addContains(FavoritesColumns.REVIEWS_URL, value);
        return this;
    }

    public FavoritesSelection reviewsUrlStartsWith(String... value) {
        addStartsWith(FavoritesColumns.REVIEWS_URL, value);
        return this;
    }

    public FavoritesSelection reviewsUrlEndsWith(String... value) {
        addEndsWith(FavoritesColumns.REVIEWS_URL, value);
        return this;
    }

    public FavoritesSelection orderByReviewsUrl(boolean desc) {
        orderBy(FavoritesColumns.REVIEWS_URL, desc);
        return this;
    }

    public FavoritesSelection orderByReviewsUrl() {
        orderBy(FavoritesColumns.REVIEWS_URL, false);
        return this;
    }

    public FavoritesSelection trailersUrl(String... value) {
        addEquals(FavoritesColumns.TRAILERS_URL, value);
        return this;
    }

    public FavoritesSelection trailersUrlNot(String... value) {
        addNotEquals(FavoritesColumns.TRAILERS_URL, value);
        return this;
    }

    public FavoritesSelection trailersUrlLike(String... value) {
        addLike(FavoritesColumns.TRAILERS_URL, value);
        return this;
    }

    public FavoritesSelection trailersUrlContains(String... value) {
        addContains(FavoritesColumns.TRAILERS_URL, value);
        return this;
    }

    public FavoritesSelection trailersUrlStartsWith(String... value) {
        addStartsWith(FavoritesColumns.TRAILERS_URL, value);
        return this;
    }

    public FavoritesSelection trailersUrlEndsWith(String... value) {
        addEndsWith(FavoritesColumns.TRAILERS_URL, value);
        return this;
    }

    public FavoritesSelection orderByTrailersUrl(boolean desc) {
        orderBy(FavoritesColumns.TRAILERS_URL, desc);
        return this;
    }

    public FavoritesSelection orderByTrailersUrl() {
        orderBy(FavoritesColumns.TRAILERS_URL, false);
        return this;
    }
}
