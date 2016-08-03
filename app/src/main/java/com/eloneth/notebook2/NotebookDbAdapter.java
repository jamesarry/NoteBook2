package com.eloneth.notebook2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by emmanuel on 3.8.2016.
 */
public class NotebookDbAdapter {
    //Creating the DB name, version, table and DB for all the info in our Note class, e.g id, title, message e.t.c
    private static final String DATABASE_NAME = "notebook.db";
    private static  final int DATABASE_VERSION = 1;

    public static final String NOTE_TABLE = "note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";

    //Create an array to hold all our Note class DB info
    private  String[] allColumn = { COLUMN_ID, COLUMN_TITLE, COLUMN_MESSAGE, COLUMN_CATEGORY, COLUMN_DATE};

    //Create a string that will create the DB for us. DB table and all the fields/info under it
    public static  final String CREATE_TABLE_NOTE = "create table " + NOTE_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + "text not null, "
            + COLUMN_MESSAGE + "text not null, "
            + COLUMN_CATEGORY + "integer not null, "
            + COLUMN_DATE + ");";
      //We will use it to manipulate our database later on
     private SQLiteDatabase sqlDB;
    private Context context;



}
