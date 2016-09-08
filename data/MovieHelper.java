package com.linux_girl.popularmovies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.linux_girl.popularmovies.Movies;

import java.util.ArrayList;

/**
 * Created by Lani on 9/7/2016.
 * Database helper
 */

public class MovieHelper extends SQLiteOpenHelper {

    public MovieHelper(Context context) {

        super(context, MovieContract.DATABASE_NAME, null,
                MovieContract.DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieContract.Table.CREATE_TABLE);
    }

    // Method is called during an upgrade of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MovieContract.Table.DELETE_TABLE);
        onCreate(db);
    }

    public Cursor getAllMovies(String selection, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                MovieContract.Table._ID,
                MovieContract.Table.COLUMN_MOVIE_TITLE,
                MovieContract.Table.COLUMN_MOVIE_PLOT,
                MovieContract.Table.COLUMN_MOVIE_RATING,
                MovieContract.Table.COLUMN_MOVIE_DATE,
                MovieContract.Table.COLUMN_MOVIE_POSTER
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = MovieContract.Table._ID + " ASC";

        Cursor cursor = db.query(
                MovieContract.Table.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder
        );
        return cursor;
    }

    public long insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MovieContract.Table.TABLE_NAME, null, values);

        return newRowId;
    }

    public Cursor getMovie(int rowId) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                MovieContract.Table._ID,
                MovieContract.Table.COLUMN_MOVIE_TITLE,
                MovieContract.Table.COLUMN_MOVIE_PLOT,
                MovieContract.Table.COLUMN_MOVIE_RATING,
                MovieContract.Table.COLUMN_MOVIE_DATE,
                MovieContract.Table.COLUMN_MOVIE_POSTER
        };
        Cursor cursor = db.query(
                MovieContract.Table.TABLE_NAME,
                projection, MovieContract.Table._ID + "=" + rowId, null, null, null, null
        );
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public void deleteDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(MovieContract.Table.DELETE_TABLE);
    }

    // Delete all data in the database
    public void deleteTablesData() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + MovieContract.Table.TABLE_NAME);
    }

    public int bulkInsert(ContentValues[] values) {
        int returnCount = 0;
        final SQLiteDatabase db = getWritableDatabase();

        for (ContentValues value : values) {
            long _id = db.insert(MovieContract.Table.TABLE_NAME, null, value);
            if (_id != -1) {
                returnCount++;
            }
        }
        return returnCount;
    }

    public void insertData(ArrayList<Movies> movie) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        for (Movies m : movie) {
            values.put(MovieContract.Table.COLUMN_MOVIE_TITLE, m.getMovieTitle());
            values.put(MovieContract.Table.COLUMN_MOVIE_PLOT, m.getMoviePlot());
            values.put(MovieContract.Table.COLUMN_MOVIE_RATING, m.getUserRating());
            values.put(MovieContract.Table.COLUMN_MOVIE_DATE, m.getReleaseDate());
            values.put(MovieContract.Table.COLUMN_MOVIE_POSTER, m.getImageUrl());
        }

        db.insert(
                MovieContract.Table.TABLE_NAME, null, values);
    }

}

