package com.example.trishmuk.slashed.Controller

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.trishmuk.slashed.Controller.Models.Channels
import com.example.trishmuk.slashed.Controller.Services.AuthService
import com.example.trishmuk.slashed.Controller.Services.DataServices
import com.example.trishmuk.slashed.Controller.Services.MessageService
import com.example.trishmuk.slashed.Controller.Utilities.SIGNUP_BROADCAST
import com.example.trishmuk.slashed.Controller.Utilities.URL_SOCKET
import com.example.trishmuk.slashed.R
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.add_channel_dialog.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    val socket = IO.socket(URL_SOCKET)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        socket.on("channelCreated", onNewChannel)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()




    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(userData, IntentFilter(SIGNUP_BROADCAST))
        socket.connect()
    }



    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(userData)
        socket.disconnect()
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
        if (AuthService.isloggedin){
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.add_channel_dialog, null, false)
            
            builder.setView(dialogView)
                    .setPositiveButton("Add"){dialog, which ->  
                        val channel_name = dialogView.findViewById<EditText>(R.id.addChannelname)
                        val channel_desc = dialogView.findViewById<EditText>(R.id.addChanneldesc)
                        val name = channel_name.text.toString()
                        val descript = channel_desc.text.toString()

                        socket.emit("newChannel", name, descript)
                    }
                    .setNegativeButton("Cancel"){dialog, which ->

                    }
                    .show()
        }

    }

    private val onNewChannel = Emitter.Listener { args ->
        runOnUiThread {
            val channelName = args[0] as String
            val channelDesc = args[1] as String
            val channelId = args[2] as String

            val newChannel = Channels(channelName, channelDesc, channelId)
            MessageService.channels.add(newChannel)
        }
    }

    fun sendClicked(view: View){
        hideKeyboard()
    }

    private fun hideKeyboard(){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }


}
