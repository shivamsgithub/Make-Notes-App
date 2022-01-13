package com.shivam.androidtask.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    public MyDBHandler(Context context){
        super(context, Params.DB_NAME, null ,Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE " + Params.TABLE_NAME + "(" + Params.KEY_ID + " INTEGER PRIMARY KEY,"
                + Params.KEY_TITLE + " TEXT, " +  Params.KEY_DESCRIPTION+ " TEXT, " + Params.KEY_IMAGES + " TEXT" + ")" ;
        Log.d("dbShivam", "Query being run is : " + create);
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addNotes (Notes notes){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TITLE, notes.getTitle());
        values.put(Params.KEY_DESCRIPTION, notes.getDescription());
//        values.put(Params.KEY_IMAGES, notes.getImages());

        database.insert(Params.TABLE_NAME, null, values);
//        Log.d("dbShivam", "Successfully Inserted");
        database.close();

    }

    public void addNotesData (String title, String description){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TITLE, title);
        values.put(Params.KEY_DESCRIPTION, description);
//        values.put(Params.KEY_IMAGES, notes.getImages());

        database.insert(Params.TABLE_NAME, null, values);
//        Log.d("dbShivam", "Successfully Inserted");
        database.close();

    }

    public List<Notes> getAllNotes(){
        List<Notes> contactList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        // Generate the query to read from database
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = database.rawQuery(select, null);

        //Loop through now
        if(cursor.moveToFirst()){
            do{
                Notes Notes = new Notes();
                Notes.setId(Integer.parseInt(cursor.getString(0)));
                Notes.setTitle(cursor.getString(1));
                Notes.setDescription(cursor.getString(2));
                contactList.add(Notes);
            } while(cursor.moveToNext());
        }
        return contactList;
    }

    public int updateNotes (Notes Notes){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TITLE, Notes.getTitle());
        values.put(Params.KEY_DESCRIPTION, Notes.getDescription());
        values.put(Params.KEY_IMAGES, Notes.getImages());

        // Lets update now
        return database.update(Params.TABLE_NAME, values, Params.KEY_ID +"=?" ,
                new String[]{String.valueOf(Notes.getId())});
    }

    public void deleteNotesById (int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Params.TABLE_NAME, Params.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        database.close();
    }

    public void deleteNotes (Notes Notes){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Params.TABLE_NAME, Params.KEY_ID + "=?",
                new String[]{String.valueOf(Notes.getId())});
        database.close();
    }

    public int getNotesCount(){
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        return cursor.getCount();
    }
}
