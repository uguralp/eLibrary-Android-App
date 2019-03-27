package com.example.uguralp.elibrary

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_pay_item.*

class PayItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_item)

        setTitle("Pay Item")

        val  bookName = intent.getStringExtra("bookname")
        val  bookCategory = intent.getStringExtra("bookcategory")

        booknameTV.text = "Book Name: $bookName"
        bookcategoryTV.text = "Book Category: $bookCategory"

        completePaymentBtn.setOnClickListener(){
            val intent = Intent(this@PayItemActivity, PaymentCompletedActivity::class.java)
            startActivity(intent)
        }
    }
}
