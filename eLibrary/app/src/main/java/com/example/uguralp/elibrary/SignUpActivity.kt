package com.example.uguralp.elibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var userDBHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setTitle("Sign Up")

        userDBHelper = UserDBHelper(this)

        signUpActionBtn.setOnClickListener(){
            addUser()
        }
    }

    fun addUser(){
        var fName = fNameTV.text.toString()
        var lName = newpasswordTF.text.toString()
        var email = emailTV.text.toString()
        var password = passwordTV.text.toString()

        var userCount = (userDBHelper.readAllUsers().count() + 1).toString()

        var result = userDBHelper.insertUser(User(userCount,fName, lName, email, password))

        Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()
        this.finish()
    }
}
