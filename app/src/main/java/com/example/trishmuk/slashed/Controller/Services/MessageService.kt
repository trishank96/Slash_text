package com.example.trishmuk.slashed.Controller.Services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.trishmuk.slashed.Controller.App
import com.example.trishmuk.slashed.Controller.Models.Channels
import com.example.trishmuk.slashed.Controller.Models.Messages
import com.example.trishmuk.slashed.Controller.Utilities.Prefers
import com.example.trishmuk.slashed.Controller.Utilities.URL_GETCHANNEL
import com.example.trishmuk.slashed.Controller.Utilities.URL_GETMESSAGE
import org.json.JSONException
import org.json.JSONObject

object MessageService {
    val channels = ArrayList<Channels>()
    val messages = ArrayList<Messages>()

    fun getChannels(complete: (Boolean) -> Unit){
        val channelRequest = object : JsonArrayRequest(Method.GET, URL_GETCHANNEL, null, Response.Listener { response ->
            try {
                for (x in 0 until response.length()){
                    val channelObj = response.getJSONObject(x)
                    val name = channelObj.getString("name")
                    val desc = channelObj.getString("description")
                    val id = channelObj.getString("_id")

                    val newChannel = Channels(name, desc, id)
                    this.channels.add(newChannel)
                }
                complete(true)
            }catch (e: JSONException){
                Log.d("JSON","EXC:"+e.localizedMessage)
                complete(false)
            }
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Can't recieve the channel")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.preference.authToken}")
                return headers
            }
        }
        App.preference.request.add(channelRequest)
    }

    fun getMessages(channelId: String, complete: (Boolean) -> Unit){
        val url = "${URL_GETMESSAGE}$channelId"
        val messageRequest = object: JsonArrayRequest(Method.GET, url, null, Response.Listener { response ->
            clearMessage()
            try {
                for (x in 0 until response.length()){
                    val messageObj = response.getJSONObject(x)
                    val messageBody = messageObj.getString("messageBody")
                    val channelId = messageObj.getString("channelId")
                    val id = messageObj.getString("_id")
                    val userName = messageObj.getString("userName")
                    val user_Avatar = messageObj.getString("userAvatar")
                    val avatarColor = messageObj.getString("userAvatarColor")
                    val timestamp = messageObj.getString("timeStamp")

                    val newMessage = Messages(messageBody, userName, channelId, user_Avatar, avatarColor, id, timestamp)
                    this.messages.add(newMessage)
                }
                complete(true)
            }catch (e: JSONException){
                Log.d("JSON","EXC:"+e.localizedMessage)
                complete(false)
            }
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Can't recieve the messages")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.preference.authToken}")
                return headers
            }
        }
        App.preference.request.add(messageRequest)
    }

    fun clearMessage(){
        messages.clear()
    }

    fun clearChannels(){
        channels.clear()
    }

}