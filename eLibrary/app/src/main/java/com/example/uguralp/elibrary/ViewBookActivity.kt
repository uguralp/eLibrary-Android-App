package com.example.uguralp.elibrary

import android.annotation.TargetApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_book.*
import android.text.method.ScrollingMovementMethod
import android.util.Log
import kotlinx.android.synthetic.main.activity_pay_item.*
import java.time.LocalDate
import java.time.LocalDateTime


class ViewBookActivity : AppCompatActivity() {

    lateinit var signedOutBookDBHelper: SignedOutBookDBHelper
    var userID = ""
    var bookPages = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_book)

        setTitle("Item Details")

        signedOutBookDBHelper = SignedOutBookDBHelper(this)

        userID = intent.getStringExtra("userID")
        nameTextView.text = intent.getStringExtra("bookname")
        categoryTextView.text = intent.getStringExtra("bookcategory")
        bookPages = intent.getStringExtra("pages")

        signOutBtn.setOnClickListener{
            addSignOutItem()
        }

    }

    fun addSignOutItem(){
        try{
            var currentDateTime = LocalDateTime.now().toString()
            var bookName = nameTextView.text.toString()
            var bookCategory = categoryTextView.text.toString()
            userID = (userID.toInt() + 1).toString()

            var result = signedOutBookDBHelper.insertSignedOutBook(
                    SignedOutBook(0,userID,bookName,bookCategory,bookPages,currentDateTime)
            )

            if(result){


                Toast.makeText(this, "You added $bookName to your card.", Toast.LENGTH_LONG).show()
            }
            else{

                Toast.makeText(this, "You added $bookName to your card.", Toast.LENGTH_LONG).show()
            }


        }catch (error:Exception){
            Log.d("adding error: ", error.toString())
        }

    }
}
