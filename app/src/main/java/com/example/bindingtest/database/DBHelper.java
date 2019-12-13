package com.example.bindingtest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bindingtest.Task;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String COMMENTS_TABLE_CREATE = "" +
            "CREATE TABLE task(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT, " +
            "description TEXT)";
    private static final String DB_NAME = "task_list.sqlite";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void insertTask(Task dataModel_task, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("title", dataModel_task.getTitle());
        cv.put("description", dataModel_task.getDescription());

        db.insert("task", null, cv);
    }

    public ArrayList<Task> getAllRows(SQLiteDatabase db){
        try {
            ArrayList<Task> tasks = new ArrayList<>();

            String query = "SELECT * FROM task";
            Cursor cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    tasks.add(new Task(
                            cursor.getString(cursor.getColumnIndex("title")),
                            cursor.getString(cursor.getColumnIndex("description")),
                            cursor.getInt(cursor.getColumnIndex("_id"))
                            ));

                } while (cursor.moveToNext());
            }

            cursor.close();

            return tasks;
        } catch (Exception ex) {
            Log.d("Debug", ex.toString());
        }
        return null;
    }


    public void updateRowById(Task task, SQLiteDatabase db, int id){
        ContentValues cv = new ContentValues();
        cv.put("title", task.getTitle());
        cv.put("description", task.getDescription());


        db.update("task", cv, "_id="+id, null);

    }

    public void deleteRowById(SQLiteDatabase db, int _id){
        db.delete("task",  "_id = " + _id,null);
        
    }


    public void deleteAllDB(SQLiteDatabase db){
        String query = "DELETE FROM task";
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(COMMENTS_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}