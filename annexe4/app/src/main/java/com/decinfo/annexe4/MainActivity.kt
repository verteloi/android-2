package com.decinfo.annexe4

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var bouton: Button
    lateinit var texteSalutations: TextView
    lateinit var lanceur: ActivityResultLauncher<Intent>;

    var util: Utilisateur? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bouton = findViewById(R.id.button)
        texteSalutations = findViewById(R.id.texteSalutations)



        // création du lanceur de boomerang, objet sera appelé au retour du boomerang dans cette classe
        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackUtilisateur()
        )

        bouton.setOnClickListener { lanceur.launch(Intent(this@MainActivity, MainActivity2::class.java)) }

        // en cas d'avoir tourner le telephone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            util = savedInstanceState?.getSerializable("util", Utilisateur::class.java)
        } else {
            util = savedInstanceState?.getSerializable("util") as Utilisateur?
        }
        texteSalutations.text = ("Bonjour ${util?.prenom?:" "} ${util?.nom?:" "}")
    }

    // juste avant l'application se ferme il va aller sauvegarder util
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("util", util)
    }


    inner class CallBackUtilisateur : ActivityResultCallback<ActivityResult> {
        // C'est la que le boomerange reviendra
        override fun onActivityResult(result: ActivityResult) {
            if (result.resultCode == RESULT_OK) {
                val intentRetour = result.data // retourne l'intent
                if (intentRetour != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        util = intentRetour.getSerializableExtra("util", Utilisateur::class.java)
                    } else {
                        util = intentRetour.getSerializableExtra("util") as Utilisateur?
                    }
                    texteSalutations.text = ("Bonjour ${util?.prenom} ${util?.nom}")
                }
            }
        }
    }
}