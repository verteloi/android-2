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

object Modele {
    val v = ArrayList<HashMap<String, Any>>()
    var listeChansons = ListeChansons()
    var artisteSelectionne = ""

    fun getListeChansons(c: Context, observateur: Observateur, choix: Int) {
        val queue = Volley.newRequestQueue(c)
        val artiste1 = "https://itunes.apple.com/lookup?id=1702217937&entity=song"
        val artiste2 = "https://itunes.apple.com/lookup?id=1767190209&entity=song"
        val artiste3 = "https://itunes.apple.com/lookup?id=1867363737&entity=song"

        if (choix == 1) {
            artisteSelectionne = artiste1
        } else if (choix == 2) {
            artisteSelectionne = artiste2
        } else {
            artisteSelectionne = artiste3
        }

        val stringRequest = StringRequest(Request.Method.GET, artisteSelectionne,
            { reponse -> decomposerReponse(reponse, observateur)},
            { erreur -> Log.e("log", erreur.toString()) }
        )
        queue.add(stringRequest)
    }

    fun decomposerReponse (reponse: String, observateur: Observateur) {
        //transforme txt json a kotlin
        listeChansons = Klaxon().parse<ListeChansons>(reponse) ?: ListeChansons()

        for (chanson in listeChansons.results) {
            val temp = HashMap<String, Any>()
            temp.put("artistName", chanson.artistName)
            temp.put("trackName", chanson.trackName)
            temp.put("previewUrl", chanson.previewUrl)
            temp.put("artworkUrl100", chanson.artworkUrl100)

            v.add(temp)
        }
        observateur.listePrete()
    }
}