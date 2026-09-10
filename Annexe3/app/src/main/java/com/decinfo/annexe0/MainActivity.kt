package com.decinfo.annexe0

import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var buttonAjouter: Button
    lateinit var buttonAfficher: Button
    lateinit var buttonQuitter: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buttonAjouter = findViewById(R.id.button)
        buttonAfficher = findViewById(R.id.button2)
        buttonQuitter  = findViewById(R.id.button3)

        //1 etape
        val ec = Ecouteur()
        //2 etape
        buttonAjouter.setOnClickListener(ec)
        buttonAfficher.setOnClickListener(ec)
        buttonQuitter.setOnClickListener(ec)
        //--> autre facon de le faire en une ligne
        //interface fonctionnelle : interface qui n'a qu'une méthode
        //une  interface fonctionnelle peut être remplacée par une expression lamda
        //buttonQuitter.setOnclickListener{v -> finish()}
        //buttonQuitter.setOnclickListener{finish()}
        //pourquoi il n'y a plus de parenthese ? à cause de la règle du "lambda trailing"

        try {
            SingletonSimle.deserializerListe(applicationContext)
        } catch (e: Exception) {
            Toast.makeText(this, "Bienvenue c'est votre 1ère utilisation", LENGTH_LONG)
        }
    }

    inner class Ecouteur : View.OnClickListener{
        override fun onClick(v: View?) { //type est view ou null
            if(v == buttonQuitter){
                finish()
            }
            else if(v == buttonAfficher){
                val i = Intent(this@MainActivity, ListerActivity2::class.java)
                startActivity(i)
            }else{
                val i = Intent(this@MainActivity, AjouterActivity::class.java)
                startActivity(i)
            }
        }
    }

}