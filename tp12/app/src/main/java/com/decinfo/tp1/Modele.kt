package com.decinfo.tp1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest

class Modele {

    fun getJsonFromUrl(c: Context) {
        val queue = Volley.newRequestQueue(c)
        val url = "https://itunes.apple.com/search?term=daft+punk&media=music"

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { reponse ->
                Log.d("TestVolley", "Données reçues : $reponse")
            },
            { erreur ->
                Log.e("TestVolley", "Erreur de connexion : ${erreur.message}")
            }
        )



    }


}