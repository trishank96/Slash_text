package com.example.trishmuk.slashed

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
