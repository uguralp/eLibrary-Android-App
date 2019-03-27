package com.example.uguralp.elibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_signed_out.*
import java.time.LocalDateTime

class ViewSignedOut : AppCompatActivity() {
    lateinit var signedOutBookDBHelper: SignedOutBookDBHelper
    var userID = ""
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_signed_out)

        signedOutBookDBHelper = SignedOutBookDBHelper(this)

        userID = intent.getStringExtra("userID")
        val  bookName = intent.getStringExtra("bookname")
        val  bookCategory = intent.getStringExtra("bookcategory")
        val bookPages = intent.getStringExtra("pages")
        val bookdSignedOutDate = intent.getStringExtra("date")

        bookNameTV.text = bookName
        bookCategoryTV.text = bookCategory
        bookPagesTV.text = bookPages
        dateTV.text = bookdSignedOutDate


        extendSignOutAction.setOnClickListener(){
            try {
                addExtension()
            }catch (e:Exception){
                Log.d("Extension error: ", e.toString())
            }
        }

    }

    fun addExtension(){
      if(count == 0){
          try{
              var bookName = bookNameTV.text.toString()
              var bookCategory = bookCategoryTV.text.toString()
              var bookPages = bookPagesTV.text.toString()
              var dateNow = LocalDateTime.now().toString()

              var result = signedOutBookDBHelper.updateSignedOutBook(
                      SignedOutBook(0,userID, bookName, bookCategory, bookPages, dateNow)
              )
          }catch (error:Exception){
              Log.d("Update error: ", error.toString())
          }
          Toast.makeText(this, "Extension approved!", Toast.LENGTH_LONG).show()
          count++
      }else{
          Toast.makeText(this, "You can make one extension at a time.", Toast.LENGTH_LONG).show()
      }
    }
}
