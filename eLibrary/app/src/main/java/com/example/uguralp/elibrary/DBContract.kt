package com.example.uguralp.elibrary

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class BookEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "Book"
            val COLUMN_NAME_ID = "id"
            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_CATEGORY = "category"
            val COLUMN_NAME_PAGES = "pages"
        }
    }

    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "User"
            val COLUMN_NAME_ID = "id"
            val COLUMN_NAME_FNAME = "fname"
            val COLUMN_NAME_LNAME = "lname"
            val COLUMN_NAME_EMAIL = "email"
            val COLUMN_NAME_PASSWORD = "password"

        }
    }

    class SignedOutBookEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "SignedOutBook"
            val COLUMN_NAME_ID = "id"
            val COLUMN_NAME_USERID = "userId"
            val COLUMN_NAME_BOOKNAME = "bookName"
            val COLUMN_NAME_BOOKCATEGORY = "bookCategory"
            val COLUMN_NAME_BOOKPAGES = "bookPages"
            val COLUMN_NAME_SIGNEDOUTDATE = "signedOutDate"

        }
    }
}