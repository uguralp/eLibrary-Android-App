package com.example.uguralp.elibrary

import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith


class ExampleUnitTest {

    //This test shows if the model stores the category correct
    @Test
    fun BookUnderDevelopment_Education_Education(){
        var book = Book("","", "","")
        book.name = "C# Design Patterns"
        book.category = "Education"
        var expected = "Education"
        var actual = book.category

        assertEquals(expected, actual)
    }

    //This test shows if the model stores the category correct
    @Test
    fun SignedOutBookUnderDevelopment_Name_Name(){
        var sob = SignedOutBook(0, "", "","", "", "")
        sob.id = 1
        sob.bookName = "C# Design Patterns"
        var expected = "C# Design Patterns"
        var actual = sob.bookName
        assertEquals(expected, actual)
    }

    //This test shows if the model stores the password correct
    @Test
    fun UserUnderDevelopment_Password_123456(){
        var user = User("","","" ,"", "")
        user.password = "123456"
        var expected = "123456"
        var actual = user.password

        assertEquals(expected, actual)
    }

    //This test shows if the model stores the book id correct
    @Test
    fun BookModelUnderDevelopment_id_id(){
        var book = Book("","","","")
        book.id = 1.toString()
        var expected = "1"
        var actual = book.id

        assertEquals(expected, actual)
    }

    //This test shows the signed out book belongs to the user that he/she did the signed out process
    @Test
    fun UserAndSignedOutModelsUnderDevelopment_UserId_UserId(){
        var user = User("","","" ,"", "")
        var signedOutBook = SignedOutBook(0,"", "","","","")
        signedOutBook.id = 2
        signedOutBook.bookName = "Android Development for the Beginners"
        user.id = "1"
        signedOutBook.userID = user.id

        var expected = user.id
        var actual = signedOutBook.userID

        assertEquals(expected, actual)
    }

    //This test shows if the table name User is correct
    @Test
    fun UserEntryUnderDevelopment_User_User(){
        var userEntry = DBContract.UserEntry
        var expected = "User"
        var actual = userEntry.TABLE_NAME

        assertEquals(expected, actual)
    }

    //This test shows if the table name Book is correct
    @Test
    fun BookEntryUnderDevelopment_TableName(){
        var bookEntry = DBContract.BookEntry
        var expected = "Book"
        var actual = bookEntry.TABLE_NAME

        assertEquals(expected, actual)
    }

    //This test shows if the table name SignedOutBook is correct
    @Test
    fun SignedOutBookUnderDevelopment_TableName(){
        var signedOutBookEntry = DBContract.SignedOutBookEntry
        var expected = "SignedOutBook"
        var actual = signedOutBookEntry.TABLE_NAME
        assertEquals(expected, actual)
    }


}
