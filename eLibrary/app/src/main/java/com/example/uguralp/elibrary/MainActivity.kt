package com.example.uguralp.elibrary

import android.content.Intent
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var userDBHelper: UserDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Sign In")

        userDBHelper = UserDBHelper(this)

        loginButton.setOnClickListener(){
            try{
                signIn()

            }catch (e:Exception){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }

        signupButton.setOnClickListener(){
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

    }

    fun signIn(){
        var userCheck = userDBHelper.readAllUsers()
        var userID = ""
        if(userCheck.count() != 0){
            userCheck.forEach {
                if ( emailTextField.text.toString() == it.email && passwordTextEdit.text.toString() == it.password){
                    userID = it.id
                    val intent = Intent(this@MainActivity, ItemsActivity::class.java)
                    intent.putExtra("userID", userID)
                    startActivity(intent)
                }
            }



        }else{
            Toast.makeText(this, "Incorrect email address or password!", Toast.LENGTH_LONG).show()
        }
    }
}
