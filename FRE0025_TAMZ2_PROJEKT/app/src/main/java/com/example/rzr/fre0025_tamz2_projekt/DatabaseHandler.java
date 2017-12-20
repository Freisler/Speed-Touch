package com.example.rzr.fre0025_tamz2_projekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RZr on 17.12.2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Game";
    // Table name
    private static final String TABLE_SCORE = "score";
    // Score Table Columns names
    public static final String KEY_ID_SCORE = "_id";
    private static final String NICK_SCORE = "nick";
    private static final String MINS_SCORE = "mins_value";
    private static final String SECS_SCORE = "secs_value";
    private static final String MILISECS_SCORE = "milisecs_value";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORE + "("
                + KEY_ID_SCORE + " INTEGER PRIMARY KEY,"
                + NICK_SCORE + " TEXT, "
                + MINS_SCORE + " INTEGER, "
                + SECS_SCORE + " INTEGER, "
                + MILISECS_SCORE + " INTEGER)";

        db.execSQL(CREATE_SCORE_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        // Create tables again
        onCreate(db);
    }

    // Adding new score
    public boolean addScore(String nick, int mins, int secs, int milisecs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NICK_SCORE, nick);
        values.put(MINS_SCORE, mins);
        values.put(SECS_SCORE, secs);
        values.put(MILISECS_SCORE, milisecs);
        // Inserting Values
        db.insert(TABLE_SCORE, null, values);
        return true;

    }

    // Getting All Scores
    public String[] getAllScores() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        int i = 0;

        String[] data = new String[cursor.getCount()];

        while (cursor.moveToNext()) {

            data[i] = cursor.getString(1);

            i = i++;

        }
        cursor.close();
        db.close();
        // return score array
        return data;
    }
}