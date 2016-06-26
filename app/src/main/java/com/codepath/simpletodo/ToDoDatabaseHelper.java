package com.codepath.simpletodo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "toDo.db";
    private static final int DATABASE_VERSION = 1;

    public ToDoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static {
        // register our models
        cupboard().register(Item.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // this will ensure that all tables are created
        cupboard().withDatabase(db).createTables();
        // add indexes and other database tweaks if you want

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this line  will upgrade your database, adding columns and new tables.
        // Note that existing columns will not be converted from what they originally were
        cupboard().withDatabase(db).upgradeTables();
    }
}
