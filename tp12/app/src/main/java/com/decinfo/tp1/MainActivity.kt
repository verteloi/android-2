package com.decinfo.tp1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.ObjectInputStream

class MainActivity : AppCompatActivity() {
    lateinit var bouton1: Button
    lateinit var bouton2: Button
    lateinit var bouton3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sauvegarde = deserializerSauvegarde(this)

        if (sauvegarde != null) {
            val i = Intent(this, ListeChansonsAffichage::class.java)
            i.putExtra("choix", sauvegarde.choixArtiste)
            i.putExtra("positionRestauree", sauvegarde.positionChanson)
            i.putExtra("tempsRestaure", sauvegarde.temps)
            startActivity(i)
        }

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
    }

    // deserializer le fichier
    fun deserializerSauvegarde(context: Context): Serialisation? {
        var obj: Serialisation? = null
        try {
            val fis: FileInputStream = context.openFileInput("sauvegarde.ser")
            val ois = ObjectInputStream(fis)
            ois.use {
                obj = ois.readObject() as Serialisation
            }
        } catch (e: Exception) {
        }
        return obj
    }

    inner class Ecouteur : View.OnClickListener
    {
        // affiche activité ListeChansonsAffichage lorsque bouton cliqué
        override fun onClick(v: View?) {
            val i = Intent(this@MainActivity, ListeChansonsAffichage::class.java)

            if (v == bouton1){
                i.putExtra("choix", 1)
            } else if (v == bouton2){
                i.putExtra("choix", 2)
            } else if (v == bouton3){
                i.putExtra("choix", 3)
            }

            startActivity(i)
        }
    }
}