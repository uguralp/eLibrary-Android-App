package com.example.uguralp.elibrary

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class SignedOutBookDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertSignedOutBook(signedOutBook: SignedOutBook): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_USERID, signedOutBook.userID)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKNAME, signedOutBook.bookName)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKCATEGORY, signedOutBook.bookCategory)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKPAGES, signedOutBook.bookPages)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_SIGNEDOUTDATE, signedOutBook.signedOutDate)

        // Insert the new row, returning the primary key value of the new row
        db.insert(DBContract.SignedOutBookEntry.TABLE_NAME, null, values)

        return true
    }


    @Throws(SQLiteConstraintException::class)
    fun updateSignedOutBook(signedOutBook: SignedOutBook): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_ID, signedOutBook.id)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_USERID, signedOutBook.userID)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKNAME, signedOutBook.bookName)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKCATEGORY, signedOutBook.bookCategory)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKPAGES, signedOutBook.bookPages)
        values.put(DBContract.SignedOutBookEntry.COLUMN_NAME_SIGNEDOUTDATE, signedOutBook.signedOutDate)

        val selection = DBContract.SignedOutBookEntry.COLUMN_NAME_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(signedOutBook.id.toString())

        db.update(DBContract.SignedOutBookEntry.TABLE_NAME, values, selection, selectionArgs)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteSignedOutBook(userID: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.SignedOutBookEntry.COLUMN_NAME_USERID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(userID)
        // Issue SQL statement.
        db.delete(DBContract.SignedOutBookEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readUser(userID: String): ArrayList<SignedOutBook> {
        val so = ArrayList<SignedOutBook>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.SignedOutBookEntry.TABLE_NAME + " WHERE " + DBContract.SignedOutBookEntry.COLUMN_NAME_USERID + "='" + userID + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SignedOutBookDBHelper.SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var userID: String
        var bookName: String
        var bookCategory: String
        var bookPages: String
        var signedOutDate: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                userID = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_USERID))
                bookName = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKNAME))
                bookCategory = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKCATEGORY))
                bookPages = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKPAGES))
                signedOutDate = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_SIGNEDOUTDATE))

                so.add(SignedOutBook(0,userID, bookName, bookCategory, bookPages, signedOutDate))
                cursor.moveToNext()
            }
        }
        return so
    }

    fun readAllSignedOutBooks(): ArrayList<SignedOutBook> {
        val books = ArrayList<SignedOutBook>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.SignedOutBookEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var userID: String
        var bookName: String
        var bookCategory: String
        var bookPages: String
        var signedOutDate: String


        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                userID = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_USERID))
                bookName = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKNAME))
                bookCategory = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKCATEGORY))
                bookPages = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKPAGES))
                signedOutDate = cursor.getString(cursor.getColumnIndex(DBContract.SignedOutBookEntry.COLUMN_NAME_SIGNEDOUTDATE))

                books.add(SignedOutBook(0,userID, bookName, bookCategory, bookPages, signedOutDate))
                cursor.moveToNext()
            }
        }
        return books
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Library.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.SignedOutBookEntry.TABLE_NAME + " (" +
                        DBContract.SignedOutBookEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        DBContract.SignedOutBookEntry.COLUMN_NAME_USERID + " TEXT," +
                        DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKNAME + " TEXT," +
                        DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKCATEGORY + " TEXT," +
                        DBContract.SignedOutBookEntry.COLUMN_NAME_BOOKPAGES + " TEXT," +
                        DBContract.SignedOutBookEntry.COLUMN_NAME_SIGNEDOUTDATE + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.SignedOutBookEntry.TABLE_NAME


    }

}