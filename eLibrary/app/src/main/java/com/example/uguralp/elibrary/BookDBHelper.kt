package com.example.uguralp.elibrary

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper


import java.util.ArrayList

class BookDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun insertBook(book: Book): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.BookEntry.COLUMN_NAME_ID, book.id)
        values.put(DBContract.BookEntry.COLUMN_NAME_NAME, book.name)
        values.put(DBContract.BookEntry.COLUMN_NAME_CATEGORY, book.category)
        values.put(DBContract.BookEntry.COLUMN_NAME_PAGES, book.pages)

        // Insert the new row, returning the primary key value of the new row
        db.insert(DBContract.BookEntry.TABLE_NAME, null, values)

        return true
    }


    @Throws(SQLiteConstraintException::class)
    fun updateBook(book: Book): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.BookEntry.COLUMN_NAME_ID, book.id)
        values.put(DBContract.BookEntry.COLUMN_NAME_NAME, book.name)
        values.put(DBContract.BookEntry.COLUMN_NAME_CATEGORY, book.category)
        values.put(DBContract.BookEntry.COLUMN_NAME_PAGES, book.pages)

        val selection = DBContract.BookEntry.COLUMN_NAME_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(book.name)

        db.update(DBContract.BookEntry.TABLE_NAME, values, selection, selectionArgs)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteBook(bookID: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.BookEntry.COLUMN_NAME_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(bookID)
        // Issue SQL statement.
        db.delete(DBContract.SignedOutBookEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readAllBooks(): ArrayList<Book> {
        val books = ArrayList<Book>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.BookEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var bookID: String
        var name: String
        var category: String
        var pages: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                bookID = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_NAME_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_NAME_NAME))
                category = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_NAME_CATEGORY))
                pages = cursor.getString(cursor.getColumnIndex(DBContract.BookEntry.COLUMN_NAME_PAGES))

                books.add(Book(bookID, name, category, pages))
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
                "CREATE TABLE " + DBContract.BookEntry.TABLE_NAME + " (" +
                        DBContract.BookEntry.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                        DBContract.BookEntry.COLUMN_NAME_NAME + " TEXT," +
                        DBContract.BookEntry.COLUMN_NAME_CATEGORY + " TEXT," +
                        DBContract.BookEntry.COLUMN_NAME_PAGES + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.BookEntry.TABLE_NAME


    }
}
