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

class Modele() {
    val v = ArrayList<HashMap<String, Any>>()
    var listeChansons = ListeChansons()

    // doit etre appeler au moment que l'utilisateur selectionne artiste
    fun getListeChansons(c: Context, observateur: Observateur) {
        val queue = Volley.newRequestQueue(c)
        val artiste1 = "https://itunes.apple.com/search?term=kanye&media=music"
        val artiste2 = "https://itunes.apple.com/search?term=drake&media=music"
        val artiste3 = "https://itunes.apple.com/search?term=nirvana&media=music"

        // changer artisteSelectionne pour l'artiste cliqué
        var artisteSelectionne = artiste1

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