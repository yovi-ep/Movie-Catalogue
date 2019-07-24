package com.yeputra.moviecatalogue.repository.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns._ID;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.ADULT;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.BACKDROP;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.ORIG_TITLE;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.OVERVIEW;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.POSTER;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.RATE;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.TABLE_NAME;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.TITLE;
import static com.yeputra.moviecatalogue.repository.contentprovider.DatabaseContract.FavoriteColumns.TYPE;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "movie_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + _ID + " TEXT PRIMARY KEY,"
                + TITLE + " TEXT,"
                + ORIG_TITLE + " TEXT,"
                + OVERVIEW + " TEXT,"
                + ADULT + " TEXT,"
                + POSTER + " TEXT,"
                + BACKDROP + " TEXT,"
                + RELEASE_DATE + " TEXT,"
                + RATE + " TEXT,"
                + TYPE + " TEXT)"
        );
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
}
