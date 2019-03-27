package com.example.uguralp.elibrary

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    var userID = ""

    lateinit var  userDBHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setTitle("Setings")

        userDBHelper = UserDBHelper(this)

        userID = intent.getStringExtra("userID")

        try {
            var user = userDBHelper.readUser(userID)
            user.forEach {
                fNameTF.setText(it.fname)
                lNameTF.setText(it.lname)
                emailTF.setText(it.email)
            }
        }catch (error:Exception){
            Log.d("Update error: ", error.toString())
        }

        updateBtn.setOnClickListener(){
            var fname = fNameTF.text.toString()
            var lname = lNameTF.text.toString()
            var email = emailTF.text.toString()
            var password = newpasswordTF.text.toString()

            try {
                var result = userDBHelper.updateUser(User(userID,fname, lname, email, password))
                oldpasswordTF.setText("")
                newpasswordTF.setText("")
                Toast.makeText(this, "Update Completed!", Toast.LENGTH_LONG).show()
            }catch (error:Exception){
                Log.d("Update error: ", error.toString())
            }
        }

        addItemBtn.setOnClickListener(){
            val intent = Intent(this@SettingsActivity, AddItemActivity::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }
    }
}
