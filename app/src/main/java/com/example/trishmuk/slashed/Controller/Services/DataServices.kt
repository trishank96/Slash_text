package com.example.trishmuk.slashed.Controller.Services

import android.graphics.Color
import com.example.trishmuk.slashed.Controller.App
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


        App.preference.authToken = ""
        App.preference.userEmail = ""
        App.preference.isLoggedin = false
        MessageService.clearMessage()
        MessageService.clearChannels()
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