package com.decinfo.tp1

import android.content.Intent
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
                val i = Intent(this@MainActivity, ListeChansonsAffichage::class.java)
                i.putExtra("choix", 1)
                startActivity(i)
            } else if (v == bouton2){
                val i = Intent(this@MainActivity, ListeChansonsAffichage::class.java)
                i.putExtra("choix", 2)
                startActivity(i)
            } else if (v == bouton3){
                val i = Intent(this@MainActivity, ListeChansonsAffichage::class.java)
                i.putExtra("choix", 3)
                startActivity(i)
            }
            else {
                finish()
            }
        }
    }
}