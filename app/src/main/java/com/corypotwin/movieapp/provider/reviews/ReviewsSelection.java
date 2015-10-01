package com.corypotwin.movieapp.provider.reviews;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.corypotwin.movieapp.provider.base.AbstractSelection;
import com.corypotwin.movieapp.provider.favorites.*;

/**
 * Selection for the {@code reviews} table.
 */
public class ReviewsSelection extends AbstractSelection<ReviewsSelection> {
    @Override
    protected Uri baseUri() {
        return ReviewsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewsCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ReviewsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewsCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ReviewsCursor query(Context context) {
        return query(context, null);
    }


    public ReviewsSelection id(long... value) {
        addEquals("reviews." + ReviewsColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection idNot(long... value) {
        addNotEquals("reviews." + ReviewsColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection orderById(boolean desc) {
        orderBy("reviews." + ReviewsColumns._ID, desc);
        return this;
    }

    public ReviewsSelection orderById() {
        return orderById(false);
    }

    public ReviewsSelection movieId(long... value) {
        addEquals(ReviewsColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection movieIdNot(long... value) {
        addNotEquals(ReviewsColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection movieIdGt(long value) {
        addGreaterThan(ReviewsColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection movieIdGtEq(long value) {
        addGreaterThanOrEquals(ReviewsColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection movieIdLt(long value) {
        addLessThan(ReviewsColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection movieIdLtEq(long value) {
        addLessThanOrEquals(ReviewsColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection orderByMovieId(boolean desc) {
        orderBy(ReviewsColumns.MOVIE_ID, desc);
        return this;
    }

    public ReviewsSelection orderByMovieId() {
        orderBy(ReviewsColumns.MOVIE_ID, false);
        return this;
    }

    public ReviewsSelection favoritesDbId(long... value) {
        addEquals(FavoritesColumns.DB_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection favoritesDbIdNot(long... value) {
        addNotEquals(FavoritesColumns.DB_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection favoritesDbIdGt(long value) {
        addGreaterThan(FavoritesColumns.DB_ID, value);
        return this;
    }

    public ReviewsSelection favoritesDbIdGtEq(long value) {
        addGreaterThanOrEquals(FavoritesColumns.DB_ID, value);
        return this;
    }

    public ReviewsSelection favoritesDbIdLt(long value) {
        addLessThan(FavoritesColumns.DB_ID, value);
        return this;
    }

    public ReviewsSelection favoritesDbIdLtEq(long value) {
        addLessThanOrEquals(FavoritesColumns.DB_ID, value);
        return this;
    }

    public ReviewsSelection orderByFavoritesDbId(boolean desc) {
        orderBy(FavoritesColumns.DB_ID, desc);
        return this;
    }

    public ReviewsSelection orderByFavoritesDbId() {
        orderBy(FavoritesColumns.DB_ID, false);
        return this;
    }

    public ReviewsSelection favoritesMovieName(String... value) {
        addEquals(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public ReviewsSelection favoritesMovieNameNot(String... value) {
        addNotEquals(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public ReviewsSelection favoritesMovieNameLike(String... value) {
        addLike(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public ReviewsSelection favoritesMovieNameContains(String... value) {
        addContains(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public ReviewsSelection favoritesMovieNameStartsWith(String... value) {
        addStartsWith(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public ReviewsSelection favoritesMovieNameEndsWith(String... value) {
        addEndsWith(FavoritesColumns.MOVIE_NAME, value);
        return this;
    }

    public ReviewsSelection orderByFavoritesMovieName(boolean desc) {
        orderBy(FavoritesColumns.MOVIE_NAME, desc);
        return this;
    }

    public ReviewsSelection orderByFavoritesMovieName() {
        orderBy(FavoritesColumns.MOVIE_NAME, false);
        return this;
    }

    public ReviewsSelection favoritesReleaseDate(String... value) {
        addEquals(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection favoritesReleaseDateNot(String... value) {
        addNotEquals(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection favoritesReleaseDateLike(String... value) {
        addLike(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection favoritesReleaseDateContains(String... value) {
        addContains(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection favoritesReleaseDateStartsWith(String... value) {
        addStartsWith(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection favoritesReleaseDateEndsWith(String... value) {
        addEndsWith(FavoritesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection orderByFavoritesReleaseDate(boolean desc) {
        orderBy(FavoritesColumns.RELEASE_DATE, desc);
        return this;
    }

    public ReviewsSelection orderByFavoritesReleaseDate() {
        orderBy(FavoritesColumns.RELEASE_DATE, false);
        return this;
    }

    public ReviewsSelection favoritesRating(String... value) {
        addEquals(FavoritesColumns.RATING, value);
        return this;
    }

    public ReviewsSelection favoritesRatingNot(String... value) {
        addNotEquals(FavoritesColumns.RATING, value);
        return this;
    }

    public ReviewsSelection favoritesRatingLike(String... value) {
        addLike(FavoritesColumns.RATING, value);
        return this;
    }

    public ReviewsSelection favoritesRatingContains(String... value) {
        addContains(FavoritesColumns.RATING, value);
        return this;
    }

    public ReviewsSelection favoritesRatingStartsWith(String... value) {
        addStartsWith(FavoritesColumns.RATING, value);
        return this;
    }

    public ReviewsSelection favoritesRatingEndsWith(String... value) {
        addEndsWith(FavoritesColumns.RATING, value);
        return this;
    }

    public ReviewsSelection orderByFavoritesRating(boolean desc) {
        orderBy(FavoritesColumns.RATING, desc);
        return this;
    }

    public ReviewsSelection orderByFavoritesRating() {
        orderBy(FavoritesColumns.RATING, false);
        return this;
    }

    public ReviewsSelection favoritesDescription(String... value) {
        addEquals(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewsSelection favoritesDescriptionNot(String... value) {
        addNotEquals(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewsSelection favoritesDescriptionLike(String... value) {
        addLike(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewsSelection favoritesDescriptionContains(String... value) {
        addContains(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewsSelection favoritesDescriptionStartsWith(String... value) {
        addStartsWith(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewsSelection favoritesDescriptionEndsWith(String... value) {
        addEndsWith(FavoritesColumns.DESCRIPTION, value);
        return this;
    }

    public ReviewsSelection orderByFavoritesDescription(boolean desc) {
        orderBy(FavoritesColumns.DESCRIPTION, desc);
        return this;
    }

    public ReviewsSelection orderByFavoritesDescription() {
        orderBy(FavoritesColumns.DESCRIPTION, false);
        return this;
    }

    public ReviewsSelection favoritesPosterUrl(String... value) {
        addEquals(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public ReviewsSelection favoritesPosterUrlNot(String... value) {
        addNotEquals(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public ReviewsSelection favoritesPosterUrlLike(String... value) {
        addLike(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public ReviewsSelection favoritesPosterUrlContains(String... value) {
        addContains(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public ReviewsSelection favoritesPosterUrlStartsWith(String... value) {
        addStartsWith(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public ReviewsSelection favoritesPosterUrlEndsWith(String... value) {
        addEndsWith(FavoritesColumns.POSTER_URL, value);
        return this;
    }

    public ReviewsSelection orderByFavoritesPosterUrl(boolean desc) {
        orderBy(FavoritesColumns.POSTER_URL, desc);
        return this;
    }

    public ReviewsSelection orderByFavoritesPosterUrl() {
        orderBy(FavoritesColumns.POSTER_URL, false);
        return this;
    }

    public ReviewsSelection review(String... value) {
        addEquals(ReviewsColumns.REVIEW, value);
        return this;
    }

    public ReviewsSelection reviewNot(String... value) {
        addNotEquals(ReviewsColumns.REVIEW, value);
        return this;
    }

    public ReviewsSelection reviewLike(String... value) {
        addLike(ReviewsColumns.REVIEW, value);
        return this;
    }

    public ReviewsSelection reviewContains(String... value) {
        addContains(ReviewsColumns.REVIEW, value);
        return this;
    }

    public ReviewsSelection reviewStartsWith(String... value) {
        addStartsWith(ReviewsColumns.REVIEW, value);
        return this;
    }

    public ReviewsSelection reviewEndsWith(String... value) {
        addEndsWith(ReviewsColumns.REVIEW, value);
        return this;
    }

    public ReviewsSelection orderByReview(boolean desc) {
        orderBy(ReviewsColumns.REVIEW, desc);
        return this;
    }

    public ReviewsSelection orderByReview() {
        orderBy(ReviewsColumns.REVIEW, false);
        return this;
    }

    public ReviewsSelection author(String... value) {
        addEquals(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorNot(String... value) {
        addNotEquals(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorLike(String... value) {
        addLike(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorContains(String... value) {
        addContains(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorStartsWith(String... value) {
        addStartsWith(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorEndsWith(String... value) {
        addEndsWith(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection orderByAuthor(boolean desc) {
        orderBy(ReviewsColumns.AUTHOR, desc);
        return this;
    }

    public ReviewsSelection orderByAuthor() {
        orderBy(ReviewsColumns.AUTHOR, false);
        return this;
    }
}
