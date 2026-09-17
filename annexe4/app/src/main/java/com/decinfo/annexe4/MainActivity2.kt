package com.decinfo.annexe4

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    lateinit var bouton2: Button
    lateinit var prenom: TextView
    lateinit var nom: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        nom = findViewById(R.id.champNom)
        prenom = findViewById(R.id.champPrenom)
        bouton2 = findViewById(R.id.button2)

        bouton2.setOnClickListener {
            val retour = Intent() // intent de retour
            var u = Utilisateur(prenom.text.toString(), nom.text.toString())
            retour.putExtra("util", u)
            setResult(RESULT_OK, retour)
            finish()
        }
    }
}