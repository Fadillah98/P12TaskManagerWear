package com.myapplicationdev.android.p12taskmanagerwear;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VER = 1;

    private static final String TASKS = "tasks";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableSql = "CREATE TABLE " + TASKS + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " TEXT, "
                + DESCRIPTION + " TEXT )";

        db.execSQL(createTableSql);
        Log.i("info", "created tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASKS);
        onCreate(db);
    }

    public long insertTask(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(DESCRIPTION, description);
        long result = db.insert(TASKS, null, values);
        db.close();
        Log.d("SQL Insert ", "" + result);
        return result;
    }

    public ArrayList<Tasks> getTasks() {
        ArrayList<Tasks> tasks = new ArrayList<Tasks>();
        String selectQuery = "SELECT " + ID + ", " + NAME + ", " + DESCRIPTION + " FROM " + TASKS;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);

                Tasks task = new Tasks(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return tasks;
    }

    public int updateTask(Tasks data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, data.getName());
        values.put(DESCRIPTION, data.getDesription());
        String condition = ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TASKS, values, condition, args);
        db.close();
        return result;
    }

    public int deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TASKS, condition, args);
        db.close();
        return result;
    }
}