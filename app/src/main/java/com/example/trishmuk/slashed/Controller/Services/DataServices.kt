package com.example.trishmuk.slashed.Controller.Services

import android.graphics.Color
import java.util.*

object DataServices {
    var name = ""
    var email = ""
    var avatarName = ""
    var avatarColor = ""
    var id = ""

    fun logout(){
        id = ""
        name = ""
        email = ""
        avatarName = ""
        avatarColor = ""

        AuthService.authToken = ""
        AuthService.Email = ""
        AuthService.isloggedin = false
    }

    fun getBgColor(components: String): Int{
        val simple_color = components.replace("[", "").replace("]", "").replace(",", "")

        var r = 0
        var g = 0
        var b = 0

        val scan = Scanner(simple_color)

        if (scan.hasNext()){
            r = (scan.nextDouble() * 255).toInt()
            g = (scan.nextDouble() * 255).toInt()
            b = (scan.nextDouble() * 255).toInt()
        }
        return Color.rgb(r,g,b)
    }
}