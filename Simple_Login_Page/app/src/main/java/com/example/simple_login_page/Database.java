package com.example.simple_login_page;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME="Users";

    private static final int DB_VRESION=1;
    private static final String TABLE_NAME="Details";


    public Database( Context context) {
        super(context,DB_NAME,null, DB_VRESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE_NAME+ "(username TEXT PRIMARY KEY,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public void Insertuser(String username,String password){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",username);
        values.put("password",password);
        db.insert(TABLE_NAME,null,values);
    }

    public boolean checkuser(String username){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " +TABLE_NAME+ " WHERE username = ? ",new String[]{username});
        return cursor.moveToFirst();

    }

    public boolean validateuser(String username,String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " +TABLE_NAME+ " WHERE username = ? AND password = ?",new String[]{username,password});
        return cursor.moveToFirst();
    }


}
