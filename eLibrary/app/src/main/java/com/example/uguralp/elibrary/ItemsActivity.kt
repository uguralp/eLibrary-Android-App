package com.example.uguralp.elibrary

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.itemrow.view.*


class ItemsActivity : AppCompatActivity() {

    lateinit var bookDBHelper: BookDBHelper
    var userID = ""
    public override fun onResume() {  // After a pause OR at startup
        super.onResume()
        //Refresh your stuff here

        var bookItems = bookDBHelper.readAllBooks()

        itemListView.adapter = MyCustomAdapter(this, bookItems)
        userID = intent.getStringExtra("userID")


        itemListView.setOnItemClickListener { parent, view, position, id ->
            val item:Book = bookItems.get(position)
            val intent = Intent(this@ItemsActivity, ViewBookActivity::class.java)
            intent.putExtra("bookname", item.name )
            intent.putExtra("bookcategory", item.category)
            intent.putExtra("pages", item.pages)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)

        bookDBHelper = BookDBHelper(this)

        setTitle("Books")

        userID = intent.getStringExtra("userID")

        //Toast.makeText(this,"Welcome $usernameText", Toast.LENGTH_LONG).show()

        var bookItems = bookDBHelper.readAllBooks()
        //Toast.makeText(this, bookItems.count().toString(), Toast.LENGTH_LONG).show()

        itemListView.adapter = MyCustomAdapter(this, bookItems)

        itemListView.setOnItemClickListener { parent, view, position, id ->
            val item:Book = bookItems.get(position)
            val intent = Intent(this@ItemsActivity, ViewBookActivity::class.java)
            intent.putExtra("bookname", item.name )
            intent.putExtra("bookcategory", item.category)
            intent.putExtra("pages", item.pages)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.item1 ->{
                val intent = Intent(this@ItemsActivity, AddtoCardActivity::class.java)
                intent.putExtra("userID", userID)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            R.id.item2 ->{
                val intent = Intent(this@ItemsActivity, SettingsActivity::class.java)
                intent.putExtra("userID", userID)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }
            R.id.item3 ->{
                val intent = Intent(this@ItemsActivity, MainActivity::class.java)
                startActivity(intent)
                return super.onOptionsItemSelected(item)
            }else -> return super.onOptionsItemSelected(item)
        }
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

 class MyCustomAdapter(context: Context, itemArray:ArrayList<Book>): BaseAdapter() {
     val mContext:Context

     var items = itemArray


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

         val item:Book = items.get(position)


         rowMain.nameTextView.text = "Book Name: ${item.name}"
         rowMain.categoryTextView.text = "Book Category: " + item.category

         return rowMain

     }


}
