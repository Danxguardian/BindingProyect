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
            "CREATE TABLE recipes(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title TEXT, " +
            "description TEXT)";
    private static final String DB_NAME = "task.sqlite";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void insertTask(Task dataModel_task, SQLiteDatabase db){
        Gson gson = new Gson();

        ContentValues cv = new ContentValues();
        cv.put("title", dataModel_task.getTitle());
        cv.put("description", dataModel_task.getDescription());

        db.insert("recipes", null, cv);
    }

    public ArrayList<Task> getAllRows(SQLiteDatabase db){
        try {
            ArrayList<Task> tasks = new ArrayList<>();
            Gson gson = new Gson();

            String query = "SELECT * FROM recipes";
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

    public void getRowById(){
        //regresar la fila seleccionada en un arraylist de tipo recipe
    }
/*
    public void updateRowById(DataModel_Recipe dataModel_recipe, SQLiteDatabase db, int id){
        Gson gson = new Gson();
        String ingredientsJson = gson.toJson(dataModel_recipe.getIngredients());
        String stepsJson = gson.toJson(dataModel_recipe.getSteps());

        ContentValues cv = new ContentValues();
        cv.put("title", dataModel_recipe.getTitle());
        cv.put("description", dataModel_recipe.getDescription());
        cv.put("photo", dataModel_recipe.getPhoto());
        cv.put("timeHour", dataModel_recipe.getTimeHour());
        cv.put("timeMinute", dataModel_recipe.getTimeMinute());
        cv.put("difficulty", dataModel_recipe.getDifficulty());
        cv.put("portions", dataModel_recipe.getPortions());
        cv.put("ingredients", ingredientsJson);
        cv.put("steps", stepsJson);

        db.update("recipes", cv, "_id="+id, null);

    }*/

    public void deleteRowById(SQLiteDatabase db, int _id){
        db.delete("recipes",  "_id = " + _id,null);
        
    }


    public void deleteAllDB(SQLiteDatabase db){
        String query = "DELETE FROM recipes";
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