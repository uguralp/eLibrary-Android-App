package com.example.uguralp.elibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_item.*

class AddItemActivity : AppCompatActivity() {

    lateinit var bookDBHelper: BookDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        setTitle("Add Item")

        bookDBHelper = BookDBHelper(this)

        addnewItemBtn.setOnClickListener(){
            if (fNameTF.text.isNotBlank() && lNameTF.text.isNotBlank()) {
                addBook()
                //Toast.makeText(this, "Item added!", Toast.LENGTH_LONG).show()
            }
        }

    }

    fun addBook(){
        var bookName = fNameTF.text.toString()
        var bookCategory = lNameTF.text.toString()
        var bookPages = bookPagesTextField.text.toString()

        var item = bookDBHelper.readAllBooks()
        var itemCount = (item.count() + 1).toString()

        try{
            var result = bookDBHelper.insertBook(Book(itemCount, bookName, bookCategory, bookPages))
            fNameTF.setText("")
            lNameTF.setText("")
            bookPagesTextField.setText("")
            Toast.makeText(this,result.toString(), Toast.LENGTH_LONG).show()
        }catch (error:Exception){
            Log.d("Insert error: ", error.toString())
        }

    }
}
