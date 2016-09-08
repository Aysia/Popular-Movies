package com.linux_girl.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by Lani on 9/6/2016.
 * Database Contract for popularmovies
 * Holds constants needed to create required tables and fields
 */
public class MovieContract {

    /**
     * When a change is made to the database, increment the DATABASE_VERSION
     */
    public static final int DATABASE_VERSION = 1;
    // Name of the Database
    public static final String DATABASE_NAME = "popularmovies.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String INTEGER = " INT";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private MovieContract() {
    }

    /**
     * Inner class to create the table @TABLE with constants referencing
     * the columns of @TABLE
     */
    public static abstract class Table implements BaseColumns {
        // Table Name
        public static final String TABLE_NAME = "Movies";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_PLOT = "movie_plot";
        public static final String COLUMN_MOVIE_RATING = "ratings";
        public static final String COLUMN_MOVIE_DATE = "release_date";
        public static final String COLUMN_MOVIE_POSTER = "poster_path";

        /**
         * create the table based on the global constants and the inner class
         */
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_MOVIE_TITLE + TEXT_TYPE + COMMA_SEP +
                COLUMN_MOVIE_PLOT + TEXT_TYPE + COMMA_SEP +
                COLUMN_MOVIE_RATING + TEXT_TYPE + COMMA_SEP +
                COLUMN_MOVIE_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_MOVIE_POSTER + TEXT_TYPE + ")";

        /**
         * Constant to delete the tables
         */
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
