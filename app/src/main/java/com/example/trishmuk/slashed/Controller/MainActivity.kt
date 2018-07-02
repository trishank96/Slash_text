package com.example.trishmuk.slashed.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.trishmuk.slashed.Controller.Services.AuthService
import com.example.trishmuk.slashed.Controller.Services.DataServices
import com.example.trishmuk.slashed.Controller.Utilities.SIGNUP_BROADCAST
import com.example.trishmuk.slashed.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        LocalBroadcastManager.getInstance(this).registerReceiver(userData, IntentFilter(SIGNUP_BROADCAST))
    }

    private val userData = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (AuthService.isloggedin){
                userName_nav.text = DataServices.name
                userEmail_nav.text = DataServices.email
                val resourceId = resources.getIdentifier(DataServices.avatarName, "drawable", packageName)

                userIcon_nav.setImageResource(resourceId)
                userIcon_nav.setBackgroundColor(DataServices.getBgColor(DataServices.avatarColor))
                login_buttonNav.text = "Logout"
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun navlogin(view: View){
        if (AuthService.isloggedin){
            DataServices.logout()
            userName_nav.text = "Please Login"
            userEmail_nav.text = ""
            userIcon_nav.setImageResource(R.drawable.profiledefault)
            userIcon_nav.setBackgroundColor(Color.TRANSPARENT)
            login_buttonNav.text = "Login"
        }else {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    fun navAddChannel(view: View){

    }

    fun sendClicked(view: View){

    }


}
