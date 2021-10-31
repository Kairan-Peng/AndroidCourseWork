package com.example.homework6;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    private static final String DB_NAME = "student.db";
    private static final String DB_TABLE = "student_info";
    private static final int DB_VERSION = 1;
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_AGE = "age";
    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    public DBAdapter(Context context) {
        this.context = context;
    }

    static class DBOpenHelper extends SQLiteOpenHelper {

        private static final String DB_CREATE = "create table " + DB_TABLE + " (" +
                KEY_ID + " integer primary key autoincrement, " +
                KEY_NAME + " text not null, " +
                KEY_GENDER + " text, " +
                KEY_AGE + " integer);";

        public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException e) {
//            e.printStackTrace();
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public long insert(Student student) {
        ContentValues values = new ContentValues();
        values.put(KEY_ID, student.id);
        values.put(KEY_NAME, student.name);
        values.put(KEY_GENDER, student.gender);
        values.put(KEY_AGE, student.age);
        return db.insert(DB_TABLE, null, values);
    }

    public long deleteOneData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneData(long id) {
        return db.delete(DB_TABLE, KEY_ID + "=" + id, null);
    }

    public long updateOneData(long id, Student student) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.name);
        values.put(KEY_GENDER, student.gender);
        values.put(KEY_AGE, student.age);
        return db.update(DB_TABLE, values, KEY_ID + "=" + id, null);
    }

    @SuppressLint("Range")
    public List<Student> convertToStudent(Cursor cursor){
        int count = cursor.getCount();
        if(count == 0 || !cursor.moveToFirst()) return null;
        List<Student> studentList = new ArrayList<>();
        for(int i = 0; i<count; i++){
            Student student = new Student();
            student.id = cursor.getInt(0);
            student.name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            student.gender = cursor.getString(cursor.getColumnIndex(KEY_GENDER));
            student.age = cursor.getInt(cursor.getColumnIndex(KEY_AGE));
            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }

    public List<Student> getOneData(long id){
        Cursor cursor = db.query(DB_TABLE,new String[]{KEY_ID,KEY_NAME,KEY_GENDER,KEY_AGE},KEY_ID+"="+id,null,null,null,null);
        return convertToStudent(cursor);
    }

    public List<Student> getAllData(){
        Cursor cursor = db.query(DB_TABLE,new String[]{"*"},null,null,null,null,null);
        return convertToStudent(cursor);
    }
}
