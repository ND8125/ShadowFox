package com.example.to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;



public class Database extends SQLiteOpenHelper {

    private static  final String DATABASE_NAME="To-Do_List";
    private static  final int DATABASE_VERSION=1;

    private static final String TABLE_NAME="Tasks";
    private static final String COL_ID="id";
    private static final String COL_TITTLE="title";
    private static final String COL_PRIORITY="priority";
    private static final String COL_COMPLETED="Completed";



    public Database( Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TITTLE + " TEXT, " +
                COL_PRIORITY + " TEXT, " +
                COL_COMPLETED + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Tasks");
        onCreate(db);
    }

    public void insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITTLE, task.getTitle());
        values.put(COL_PRIORITY, task.getPriority());
        values.put(COL_COMPLETED, task.isCompleted() ? 1 : 0);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deletetask (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,COL_ID + "=?" ,new String[]{String.valueOf(id)});

    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITTLE));
            String priority = cursor.getString(cursor.getColumnIndexOrThrow(COL_PRIORITY));
            boolean isCompleted = cursor.getInt(cursor.getColumnIndexOrThrow(COL_COMPLETED)) == 1;
            list.add(new Task(id,title, priority, isCompleted));
        }

        cursor.close();
        return list;
    }
}
