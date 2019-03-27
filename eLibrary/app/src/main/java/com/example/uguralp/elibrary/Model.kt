package com.example.uguralp.elibrary

import java.util.*

class User(
        var id:String,
        var fname:String,
        var lname:String,
        var email:String,
        var password:String
)

class Book(
        var id: String,
        var name:String,
        var category:String,
        var pages:String
)

class SignedOutBook(
        var id: Int,
        var userID: String,
        var bookName:String,
        var bookCategory:String,
        var bookPages:String,
        var signedOutDate:String
)