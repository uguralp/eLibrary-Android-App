package com.example.uguralp.elibrary

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_addto_card.*
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.itemrow.view.*

class AddtoCardActivity : AppCompatActivity() {

    lateinit var signedOutBookDBHelper: SignedOutBookDBHelper
    var userID = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addto_card)

        signedOutBookDBHelper = SignedOutBookDBHelper(this)

        setTitle("Sign Out")

        userID = intent.getStringExtra("userID")

        try{
            var itemList = signedOutBookDBHelper.readAllSignedOutBooks()

            cardListView.adapter = MyCardAdapter(this, itemList)

            cardListView.setOnItemClickListener { parent, view, position, id ->
                val item = itemList.get(position)
                val intent = Intent(this@AddtoCardActivity, ViewSignedOut::class.java)
                intent.putExtra("bookname", item.bookName)
                intent.putExtra("bookcategory", item.bookCategory)
                intent.putExtra("pages", item.bookPages)
                intent.putExtra("date", item.signedOutDate)
                intent.putExtra("userID", item.userID)
                startActivity(intent)
            }
        }catch (error: Exception){
            Log.d("Sign Out error: ", error.toString())
        }

    }
}

class MyCardAdapter(context: Context, itemsArray:ArrayList<SignedOutBook>): BaseAdapter() {
    val mContext: Context

    var items = itemsArray


    init {
        mContext = context
    }


    override fun getItem(position: Int): Any {
        return items[position]
    }
    override fun getCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var layoutInflater = LayoutInflater.from(mContext)
        var rowMain = layoutInflater.inflate(R.layout.itemrow,parent, false)

        val item = items.get(position)


        rowMain.nameTextView.text = "Book Name: ${item.bookName}"
        rowMain.categoryTextView.text = "Book Category: ${item.bookCategory}"
        return rowMain

    }


}
