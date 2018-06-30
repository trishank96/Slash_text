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
        AuthService.registerUser(this, "mukherjeetrish005@gmail.com", "trish789"){
            if (it === null){
                Toast.makeText(this,"Please Input a value", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onBgColor(view: View){
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b= random.nextInt(255)

        userAvatar.setBackgroundColor(Color.rgb(r,g,b))

        

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
