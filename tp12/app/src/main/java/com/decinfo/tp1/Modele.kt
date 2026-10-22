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
import com.beust.klaxon.Klaxon
import org.json.JSONArray

class Modele {
    val v = ArrayList<HashMap<String, Any>>()

    fun getListeChansons(c: Context) {
        val queue = Volley.newRequestQueue(c)
        val url = "https://itunes.apple.com/search?term=daft+punk&media=music"

        val stringRequest = StringRequest(Request.Method.GET, url,
            { reponse -> decomposerReponse(reponse)


            },
            { erreur -> Log.e("log", erreur.toString()) }
        )

        queue.add(stringRequest)


    }

    fun decomposerReponse(reponse: String) {
        //transform txt json a kotlin
        val reponse: ListeChansons = Klaxon().parse<ListeChansons>(reponse) ?: ListeChansons()
        v.clear()

        for(chanson in reponse.results) {
            val temp = HashMap<String, Any>()


        }


    }

    fun remplirArrayList() : ArrayList<HashMap<String, Any>> {
        var h = HashMap<String, Any>()
        h.put("position", 3)
        h.put("nom", "Touch Me")
        h.put("date", "22/03/86")
        h.put("image", "https://www.cvm.qc.ca/wp-content/uploads/mycelium_EVENEMENT.jpg")

        v.add(h)

        return v;
    }
}