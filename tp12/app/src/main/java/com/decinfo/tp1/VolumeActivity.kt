package com.decinfo.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VolumeActivity : AppCompatActivity() {
    lateinit var volumeSeekbar: SeekBar
    lateinit var boutonRetour: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_volume)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonRetour = findViewById(R.id.boutonRetour)

        // le volume actuel
        val volumeDeBase = intent.getFloatExtra("volumeActuel", 1.0f)

        volumeSeekbar = findViewById(R.id.volumeSeekbar)
        volumeSeekbar.max = 100
        volumeSeekbar.progress = (volumeDeBase * 100).toInt()

        // Retourne a l'activité precedente quand bouton cliqué
        boutonRetour.setOnClickListener {
            val retour = Intent() // intent de retour
            val volumeConverti = volumeSeekbar.progress / 100f

            // retourne le volume selectionné par l'utilisateur
            retour.putExtra("volume", volumeConverti)
            setResult(RESULT_OK, retour)
            finish()
        }
    }
}