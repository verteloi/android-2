package com.decinfo.tp1

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListeChansonsAffichage : AppCompatActivity(), Observateur {
    lateinit var liste: ListView
    lateinit var modele: Modele

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_liste_chansons)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        modele = Modele()
        liste = findViewById(R.id.listeChansons)

    }

    override fun listePrete() {
        val adapteur = BetterSimpleAdapter(this, modele.v, R.layout.un_item, arrayOf("trackName", "artistName", "artworkUrl100"), intArrayOf(R.id.trackName, R.id.artistName,R.id.artworkUrl100))
        liste.adapter = adapteur
        liste.setOnItemClickListener{_,_,position,_ -> Toast.makeText(this, modele.v.get(position).get("nom").toString(), LENGTH_LONG).show()}

    }
}