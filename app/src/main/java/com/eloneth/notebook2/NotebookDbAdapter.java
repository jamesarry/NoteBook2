package com.eloneth.notebook2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by emmanuel on 3.8.2016.
 */
public class NotebookDbAdapter {
    //Creating the DB name, version, table and DB for all the info in our Note class, e.g id, title, message e.t.c
    private static final String DATABASE_NAME = "notebook.db";
    private static final int DATABASE_VERSION = 2;

    public static final String NOTE_TABLE = "note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATE = "date";

    //Create an array to hold all our Note class DB info
    private  String[] allColumns = { COLUMN_ID, COLUMN_TITLE, COLUMN_MESSAGE, COLUMN_CATEGORY,
            COLUMN_DATE };

    //Create a string that will create the DB for us. DB table and all the fields/info under it
    public static final String CREATE_TABLE_NOTE = "create table " + NOTE_TABLE + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_MESSAGE + " text not null, "
            + COLUMN_CATEGORY + " integer not null, "
            + COLUMN_DATE + ");";
      //We will use it to manipulate our database later on
     private SQLiteDatabase sqlDB;
     private Context context;
     private NotebookDbHelper notebookDbHelper;

      //NoteDbAdapter constructor
    public NotebookDbAdapter(Context ctx){
        context = ctx;

    }

           //Method to open our db before doing any thing with it
    public NotebookDbAdapter open() throws android.database.SQLException{
         notebookDbHelper = new NotebookDbHelper(context);
         sqlDB = notebookDbHelper.getWritableDatabase();
         return this;
    }

    //Method to close our db
    public void close(){
        notebookDbHelper.close();

    }

     //We will use getAllNote() to grab all of our note database items and display them in our Main Activity list fragment
    public ArrayList<Note> getAllNotes(){
        ArrayList<Note> notes = new ArrayList<Note>();

        //Grab all our information in our database for the notes in it
        Cursor cursor = sqlDB.query(NOTE_TABLE, allColumns, null, null, null, null, null);

        //Loop through all of the rows (notes)in our database and create new notes objects from those
        //rows and add them to our array list
        for(cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
            Note note = cursorToNote(cursor);

            notes.add(note);
        }
        //close cursor required
        cursor.close();

        //Return array list now filled with note in our database
        return notes;
    }

    //Give a cursor returns a note object
    private Note cursorToNote(Cursor cursor){
        Note newNote = new Note(cursor.getString(1), cursor.getString(2),
                Note.Category.valueOf(cursor.getString(3)), cursor.getLong(0), cursor.getLong(4));
        return newNote;
    }


          //SQLite helps to open, create  and update database
    private static class NotebookDbHelper extends SQLiteOpenHelper{

              NotebookDbHelper(Context ctx){
                  super(ctx, DATABASE_NAME, null, DATABASE_VERSION);

              }
              //This method is called when we need to create our database for the very first time
              @Override
              public void onCreate(SQLiteDatabase db) {
                  db.execSQL(CREATE_TABLE_NOTE);//We are creating our note table in our db for the very first time

              }
               //This method allows us to upgrade our database, i.e upgrading from db version 1 to 2
              @Override
              public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                  //a Debugger to show when our db is upgraded
                  Log.w(NotebookDbHelper.class.getName(),
                          "Upgrading database from version  " + oldVersion + " to "
                                  + newVersion + ", which will destroy all old data");
                  //upgrade to a new version if the table exist
                  db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
                  onCreate(db);

              }
          }

}
