package com.example.trishmuk.slashed.Controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.content.Intent
import com.example.trishmuk.slashed.R


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun LoginClicked(view: View){


    }

    fun CreateUser(view: View){
        val signupIntent = Intent(this, SignupActivity::class.java)
        startActivity(signupIntent)
    }
}
