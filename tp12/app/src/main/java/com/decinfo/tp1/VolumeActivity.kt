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

        val volumeDeBase = intent.getFloatExtra("volumeActuel", 1.0f)

        boutonRetour = findViewById(R.id.boutonRetour)

        volumeSeekbar = findViewById(R.id.volumeSeekbar)
        volumeSeekbar.max = 100
        volumeSeekbar.progress = (volumeDeBase * 100).toInt()

        Log.e("ENVOYE", volumeDeBase.toString())
        Log.e("ENVOYE", volumeDeBase.toString())
        Log.e("ENVOYE", volumeDeBase.toString())


        boutonRetour.setOnClickListener {
            val retour = Intent() // intent de retour
            val volumeConverti = volumeSeekbar.progress / 100f
            retour.putExtra("volume", volumeConverti)
            setResult(RESULT_OK, retour)
            finish()
        }
    }
}