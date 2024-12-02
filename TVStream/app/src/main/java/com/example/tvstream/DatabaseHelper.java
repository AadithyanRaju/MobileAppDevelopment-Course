package com.example.tvstream;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tvstream.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "channels";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_URL = "url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create channels table
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_URL + " TEXT)";
        db.execSQL(createTable);

        // Insert default channel: Amrita TV
        String insertDefaultChannel = "INSERT INTO " + TABLE_NAME + " (" + COL_NAME + ", " + COL_URL + ") VALUES " +
                "('Amrita TV', 'https://dr1zhpsuem5f4.cloudfront.net/master.m3u8')";
        db.execSQL(insertDefaultChannel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllChannels() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
