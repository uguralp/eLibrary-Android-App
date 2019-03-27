package com.example.uguralp.elibrary

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class UserDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: User): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_NAME_ID, user.id)
        values.put(DBContract.UserEntry.COLUMN_NAME_FNAME, user.fname)
        values.put(DBContract.UserEntry.COLUMN_NAME_LNAME, user.lname)
        values.put(DBContract.UserEntry.COLUMN_NAME_EMAIL, user.email)
        values.put(DBContract.UserEntry.COLUMN_NAME_PASSWORD, user.password)

        // Insert the new row, returning the primary key value of the new row
        db.insert(DBContract.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun updateUser(user: User): Boolean{
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_NAME_ID, user.id)
        values.put(DBContract.UserEntry.COLUMN_NAME_FNAME, user.fname)
        values.put(DBContract.UserEntry.COLUMN_NAME_LNAME, user.lname)
        values.put(DBContract.UserEntry.COLUMN_NAME_EMAIL, user.email)
        values.put(DBContract.UserEntry.COLUMN_NAME_PASSWORD, user.password)

        val selection = DBContract.UserEntry.COLUMN_NAME_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(user.id)

        db.update(DBContract.UserEntry.TABLE_NAME, values, selection, selectionArgs)
        return  true
    }


    @Throws(SQLiteConstraintException::class)
    fun deleteUser(courseID: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_NAME_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(courseID)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readUser(userID: String): ArrayList<User> {
        val user = ArrayList<User>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_NAME_ID + "='" + userID + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var fName: String
        var lname: String
        var email: String
        var password: String
        var userID: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                userID = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_ID))
                fName = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_FNAME))
                lname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_LNAME))
                email = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_EMAIL))
                password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_PASSWORD))

                user.add(User(userID, fName, lname, email, password))
                cursor.moveToNext()
            }
        }
        return user
    }

    fun readAllUsers(): ArrayList<User> {
        val users = ArrayList<User>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var userID: String
        var fName: String
        var lname: String
        var email: String
        var password: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                userID = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_ID))
                fName = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_FNAME))
                lname = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_LNAME))
                email = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_EMAIL))
                password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME_PASSWORD))

                users.add(User(userID, fName, lname, email, password))
                cursor.moveToNext()
            }
        }
        return users
    }


    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Library.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                        DBContract.UserEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                        DBContract.UserEntry.COLUMN_NAME_FNAME + " TEXT," +
                        DBContract.UserEntry.COLUMN_NAME_LNAME + " TEXT," +
                        DBContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT, " +
                        DBContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }

}