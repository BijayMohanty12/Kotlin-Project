package com.example.information_database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "Student.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME="student_table"
        private const val  COL_1="ID"
        private const val  COL_2="NAME"
        private const val  COL_3="CLASS"
        private const val  COL_4="MARKS"


    }
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_2 + " TEXT,"
                + COL_3 + " TEXT,"
                + COL_4 + " INTEGER" + ")")
        db.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop the old table if it exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME");
        // Recreate the table
        onCreate(db);
    }
    fun insertData(name:String, room:String,marks:String):Boolean
    {
        val db= this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(COL_2,name)
        contentValues.put(COL_3,room)
        contentValues.put(COL_4,marks)
       val result= db.insert(TABLE_NAME,null,contentValues)
        return result.toInt() != -1

    }
    fun viewData(): Cursor
    {
        val db = this.readableDatabase
        return db.query(TABLE_NAME,null,null,null,null,null,null,null)


    }
    fun updateData (id:String,name:String,room:String,marks:String) {
        val db= this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put(COL_1,id)
        contentValues.put(COL_2,name)
        contentValues.put(COL_3,room)
        contentValues.put(COL_4,marks)
        val selection = "$COL_1 = ?"
        val selectionArgs = arrayOf(id)
        db.update(TABLE_NAME,contentValues,selection,selectionArgs);
    }
    fun deleteData(id:String)
    {
        val db=this.writableDatabase
        val selection = "$COL_1 = ?"
        val selectionArgs = arrayOf(id)
        db.delete(TABLE_NAME,selection,selectionArgs)
    }

}