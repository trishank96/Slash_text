package com.example.trishmuk.slashed.Controller.Services

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.trishmuk.slashed.Controller.Utilities.*
import org.json.JSONException
import org.json.JSONObject

object AuthService {
    var authToken = ""
    var isloggedin = false
    var Email = ""

    fun registerUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit){
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()

        val registerRequest = object : StringRequest(Method.POST, URL_REGISTER, Response.Listener { response->
            println(response)
            complete(true)
        }, Response.ErrorListener { error ->
            Log.d("error", "Can't access to the requested url: $error")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(registerRequest)
    }


    fun loginUser(context: Context, email: String, password: String, complete: (Boolean) -> Unit){
        val jsonBody = JSONObject()
        jsonBody.put("email", email)
        jsonBody.put("password", password)
        val requestBody = jsonBody.toString()


        val loginRequest = object : JsonObjectRequest(Method.POST, URL_LOGIN, null, Response.Listener { response ->
           try {
               authToken = response.getString("token")
               Email = response.getString("user")
               isloggedin = true
               complete(true)
           } catch (e: JSONException){
               Log.d("EXC", "EXC:"+e.localizedMessage)
           }

        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Can't access to the requested url: $error")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
        }

        Volley.newRequestQueue(context).add(loginRequest)
    }

    fun createUser(context: Context, name: String, email: String, avatarName: String, avatarColor: String, complete: (Boolean) -> Unit){
        val jsonBody = JSONObject()
        jsonBody.put("name", name)
        jsonBody.put("email", email)
        jsonBody.put("avatarName", avatarName)
        jsonBody.put("avatarColor", avatarColor)
        val requestBody = jsonBody.toString()

        val createRequest = object : JsonObjectRequest(Method.POST, URL_CREATEUSER, null, Response.Listener { response ->
            try {
                DataServices.name = response.getString("name")
                DataServices.email = response.getString("email")
                DataServices.avatarName = response.getString("avatarName")
                DataServices.avatarColor = response.getString("avatarColor")
                DataServices.id = response.getString("_id")
                complete(true)
            }catch (e: JSONException){
                Log.d("Json Error", "EXC:"+e.localizedMessage)
                complete(false)
            }
        }, Response.ErrorListener { error ->
            Log.d("Error", "Could not access data: $error")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $authToken")
                return headers
            }
        }
        Volley.newRequestQueue(context).add(createRequest)
    }

    fun findUser (context: Context, complete: (Boolean) -> Unit){
        val findRequest = object: JsonObjectRequest(Method.GET, "$URL_GETUSER${Email}", null, Response.Listener { response ->
            try {
                DataServices.name = response.getString("name")
                DataServices.email = response.getString("email")
                DataServices.avatarName = response.getString("avatarName")
                DataServices.avatarColor = response.getString("avatarColor")
                DataServices.id = response.getString("_id")

                val userDef = Intent(SIGNUP_BROADCAST)
                LocalBroadcastManager.getInstance(context).sendBroadcast(userDef)
                complete(true)
            }catch (e: JSONException){
                Log.d("Json Error", "EXC:"+e.localizedMessage)
                complete(false)
            }
        }, Response.ErrorListener {error ->
            Log.d("ERROR", "Could'nt find the user")
            complete(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer $authToken")
                return headers
            }
        }

        Volley.newRequestQueue(context).add(findRequest)
    }
}