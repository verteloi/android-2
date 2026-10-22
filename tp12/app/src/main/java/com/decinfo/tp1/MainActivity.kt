package com.decinfo.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest

class MainActivity : AppCompatActivity() {

    //nullabilité : on ne peut plus faire de nullpointerexception
    //donc une variable ne peut être nulle
    //var valeur modifiable, val valeur immuable (constante)

    lateinit var bouton1: Button
    lateinit var bouton2: Button
    lateinit var bouton3: Button

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

    }
    inner class Ecouteur : View.OnClickListener
    {
        override fun onClick(v: View?) {
            if (v == bouton1){
                val i = Intent(this@MainActivity, ListeChansons::class.java)
                startActivity(i)
            } else {
                finish()
            }
        }
    }
}