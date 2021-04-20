package com.latihan.applicationtodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Todo.db"; //nama database

    public static String TABLE_NAME="todo_table";//versi database

    private static final int DATABASE_VERSION=1 ; //colom tabel

    public static final String COL_1= "ID";
    public static final String COL_2= "NAME";
    public static final String COL_3= "STATUS";
    public static final String COL_4= "DATE";
    public static final String COL_5= "KEYTODO";

    public DataBaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table todo_table(id integer primary key autoincrement,"+
                "name text,"+
                "status text,"+
                "date text,"+
                "keytodo text);");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,"pergi");
        contentValues.put(COL_3,"pergi keluar");
        contentValues.put(COL_4,"12-04-2021");
        contentValues.put(COL_5,"1312");
        db.insert(TABLE_NAME,null,contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean updateData(String name, String status,String date,String id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,status);
        contentValues.put(COL_4,date);

        long result = db.update(TABLE_NAME, contentValues,"ID=?", new String[]{id});
        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

    //metode untuk mengambil data
    public  boolean insertData(String name, String status, String date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,status);
        contentValues.put(COL_4,date);

        long result = db.insert(TABLE_NAME,null, contentValues);

        if (result == -1){
            return false;
        }else {
            return  true;
        }

    }


    public List<Todo> getAllData(){
        List<Todo> todos = new ArrayList<>();
        String selectQuery = "SELECT * FROM TODO_TABLE";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Todo todo = new Todo();
                todo.setNametodo(cursor.getString(1));
                todo.setStatustodo(cursor.getString(2));
                todo.setDatetodo(cursor.getString(3));
                todo.setIdtodo(cursor.getString(0));
                todos.add(todo);
            }while (cursor.moveToNext());
        }

        db.close();
        return  todos;

    }

    public int deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME,"ID= ?",new String[]{id});
    }


}
