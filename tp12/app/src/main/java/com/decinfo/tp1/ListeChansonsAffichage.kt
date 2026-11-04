package com.decinfo.tp1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListeChansonsAffichage : AppCompatActivity(), Observateur {
    lateinit var liste: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_liste_chansons)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.listeChansons)

        val choix = intent.getIntExtra("choix", 0)

        // crée singleton Modele
        Modele.getListeChansons(this, this, choix)
    }

    // executer lorsque l'observateur envoie le signal
    override fun listePrete() {
        
        // affiche les elements avec images dans listview
        val adapteur = BetterSimpleAdapter(this, Modele.v, R.layout.un_item, arrayOf("trackName", "artistName", "artworkUrl100"), intArrayOf(R.id.trackName, R.id.artistName,R.id.artworkUrl100))
        liste.adapter = adapteur

        val positionRestauree = intent.getIntExtra("positionRestauree", -1)
        val tempsRestaure = intent.getLongExtra("tempsRestaure", -1L)
        val choix = intent.getIntExtra("choix", 0)

        // si on vient de la sauvegarde de MainActivity
        if (positionRestauree != -1) {
            val i = Intent(this@ListeChansonsAffichage, EcouterChanson::class.java)
            i.putExtra("chansonChoisi", positionRestauree)
            i.putExtra("choix", choix)
            i.putExtra("tempsRestaure", tempsRestaure)
            startActivity(i)

            intent.removeExtra("positionRestauree")
        }

        // executé lorsque utilisateur clique sur element du listview
        liste.setOnItemClickListener{_,_,position,_ ->
            val i = Intent(this@ListeChansonsAffichage, EcouterChanson::class.java)
            i.putExtra("chansonChoisi", position)
            i.putExtra("choix", choix)
            startActivity(i)
        }
    }
}
