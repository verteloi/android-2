package com.decinfo.tp1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var bouton1: Button
    lateinit var bouton2: Button
    lateinit var bouton3: Button
    var serialisation: Serialisation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bouton1 = findViewById(R.id.bouton1)
        bouton2 = findViewById(R.id.bouton2)
        bouton3 = findViewById(R.id.bouton3)

        val ec = Ecouteur()

        bouton1.setOnClickListener(ec)
        bouton2.setOnClickListener(ec)
        bouton3.setOnClickListener(ec)

        // en cas d'avoir tourner le telephone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            serialisation = savedInstanceState?.getSerializable("choix", Serialisation::class.java)
        } else {
            serialisation = savedInstanceState?.getSerializable("choix") as Serialisation?
        }


        if (serialisation != null) {
            val i = Intent(this, ListeChansonsAffichage::class.java)
            i.putExtra("choix", serialisation!!.choix)
            startActivity(i)
        }
    }

    // juste avant l'application se ferme il va aller sauvegarder choix
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("choix", serialisation)
    }

    inner class Ecouteur : View.OnClickListener
    {
        // affiche activité ListeChansonsAffichage lorsque bouton cliqué
        override fun onClick(v: View?) {
            val i = Intent(this@MainActivity, ListeChansonsAffichage::class.java)

            // Envoie le choix de l'utilisateur
            if (v == bouton1){
                serialisation = Serialisation(1)
                i.putExtra("choix", 1)

            } else if (v == bouton2){
                serialisation = Serialisation(2)
                i.putExtra("choix", 2)

            } else if (v == bouton3){
                serialisation = Serialisation(3)
                i.putExtra("choix", 3)
            }

            startActivity(i)
        }
    }
}