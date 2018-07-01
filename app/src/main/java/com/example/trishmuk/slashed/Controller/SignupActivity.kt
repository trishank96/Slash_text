package com.example.trishmuk.slashed.Controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.trishmuk.slashed.Controller.Services.AuthService
import com.example.trishmuk.slashed.R
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*

class SignupActivity : AppCompatActivity() {

    var avatar = "profiledefault"
    var bgcolor = "[0.75, 0.5, 0.75, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun onSignup(view: View){
        val uname = uname_signup.text.toString()
        val email = email_signup.text.toString()
        val password = password_signup.text.toString()
        AuthService.registerUser(this, email, password){registerSuccess ->
            if (registerSuccess){
                AuthService.loginUser(this, email, password){loginSuccess->
                    if (loginSuccess){
                        AuthService.createUser(this, uname, email, avatar, bgcolor){createSuccess ->
                            if (createSuccess){
                                finish()
                            }
                        }
                    }
                }
            }
        }
    }

    fun onBgColor(view: View){
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b= random.nextInt(255)

        userAvatar.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() /255
        val savedB = b.toDouble() /255

        bgcolor = "[$savedR, $savedG, $savedB, 1]"

    }

    fun genAvatar(view: View){
        val random = Random()
        val randomAvatar = random.nextInt(28)
        val color = random.nextInt(2)

        if (color == 0){
            avatar = "light$randomAvatar"
        } else{
            avatar = "dark$randomAvatar"
        }

        val resourceId = resources.getIdentifier(avatar, "drawable", packageName)
        userAvatar.setImageResource(resourceId)
    }
}
