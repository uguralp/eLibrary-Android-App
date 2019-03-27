package com.example.uguralp.elibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_payment_completed.*

class PaymentCompletedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_completed)

        completedTextEdit.text = "Payment completed! You can download your receipt."

        receiptDownloadBtn.setOnClickListener(){
            Toast.makeText(this, "Receipt downloaded!", Toast.LENGTH_LONG).show()
        }
    }
}
