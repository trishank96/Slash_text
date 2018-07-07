package com.example.trishmuk.slashed.Controller

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.trishmuk.slashed.Controller.Services.AuthService
import com.example.trishmuk.slashed.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }

    fun LoginClicked(view: View){
        val email = email_login.text.toString()
        val pass = password_login.text.toString()
        hideKeyboard()

        AuthService.loginUser(email, pass){loginSuccess ->
            if (loginSuccess){
                AuthService.findUser(this){findSuccess ->
                    if (findSuccess){
                        progressBar(true)

                        finish()
                    }
                }
            }else{
                progressBar(false)
                Toast.makeText(this, "User Email and password don't match.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun progressBar(enable: Boolean) {
        if (enable) {
            login_progress.visibility = View.VISIBLE
        } else {
            login_progress.visibility = View.INVISIBLE
        }

        email_sign_in_button.isEnabled = !enable
        sign_up_button.isEnabled = !enable
    }
    fun CreateUser(view: View){
        val signupIntent = Intent(this, SignupActivity::class.java)
        startActivity(signupIntent)
        finish()
    }

    private fun hideKeyboard(){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}
