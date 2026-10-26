package com.decinfo.tp1

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlin.math.log

class EcouterChanson : AppCompatActivity() {
    lateinit var vue: PlayerView
    var player: ExoPlayer?= null
    lateinit var mp3url: String
    lateinit var mediaItem: MediaItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ecouter_chanson)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mp3url = intent.getStringExtra("chansonChoisi")!!
        mediaItem = MediaItem.fromUri(mp3url)

        vue = findViewById(R.id.vue)

        player = ExoPlayer.Builder(this).build()
        // vue.setUseController(false);

    }

    override fun onStart() {
        super.onStart()
        vue.player = player
        player?.addMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }
}