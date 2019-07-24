package com.yeputra.moviecatalogue.repository.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yeputra.moviecatalogue.model.MovieFavorite;

import java.util.ArrayList;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = DatabaseContract.FavoriteColumns.TABLE_NAME;
    private final DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;

    private SQLiteDatabase database;

    private FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<MovieFavorite> query(String type) {
        ArrayList<MovieFavorite> arrayList = new ArrayList<>();
        Cursor cursor = providerQuery(type);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                arrayList.add(getMovieFromCursor(cursor));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public MovieFavorite queryById(String id) {
        Cursor cursor = providerQueryById(id);
        cursor.moveToFirst();
       return getMovieFromCursor(cursor);
    }

    public long insert(MovieFavorite movie) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DatabaseContract.FavoriteColumns._ID, movie.getId());
        initialValues.put(DatabaseContract.FavoriteColumns.TITLE, movie.getTitle());
        initialValues.put(DatabaseContract.FavoriteColumns.ORIG_TITLE, movie.getOrigTitle());
        initialValues.put(DatabaseContract.FavoriteColumns.OVERVIEW, movie.getOverview());
        initialValues.put(DatabaseContract.FavoriteColumns.ADULT, movie.getAdult());
        initialValues.put(DatabaseContract.FavoriteColumns.POSTER, movie.getPoster_path());
        initialValues.put(DatabaseContract.FavoriteColumns.BACKDROP, movie.getBackdrop_path());
        initialValues.put(DatabaseContract.FavoriteColumns.RELEASE_DATE, movie.getRelease_date());
        initialValues.put(DatabaseContract.FavoriteColumns.RATE, movie.getVote_average());
        initialValues.put(DatabaseContract.FavoriteColumns.TYPE, movie.getType());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int delete(String id) {
        return database.delete(
                DatabaseContract.FavoriteColumns.TABLE_NAME,
                DatabaseContract.FavoriteColumns._ID + " = '" + id + "'",
                null);
    }

    public Cursor providerQuery(String type) {
        return database.query(DATABASE_TABLE
                , null
                , DatabaseContract.FavoriteColumns.TYPE + " = ?"
                , new String[] {type}
                , null
                , null, DatabaseContract.FavoriteColumns._ID + " DESC"
                , null);
    }
    public Cursor providerQueryById(String id) {
        return database.query(DATABASE_TABLE, null
                , DatabaseContract.FavoriteColumns._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    private MovieFavorite getMovieFromCursor(Cursor cursor) {
        if (cursor.getCount() > 0) {
            return new MovieFavorite(
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ORIG_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.OVERVIEW)),
                    Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ADULT))),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.RELEASE_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.RATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.BACKDROP)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.POSTER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.TYPE))
            );
        } else {
            return null;
        }
    }
}
