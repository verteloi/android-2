package com.decinfo.tp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
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
    lateinit var songImage: ImageView
    lateinit var pausePlayButton: ImageView
    lateinit var skipSongButton: ImageView
    lateinit var skip10Button: ImageView
    lateinit var back10Button: ImageView
    lateinit var backSongButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ecouter_chanson)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        songImage = findViewById(R.id.songImage)

        pausePlayButton = findViewById(R.id.pausePlayButton)
        skip10Button = findViewById(R.id.skip10Button)
        back10Button = findViewById(R.id.back10Button)
        skipSongButton = findViewById(R.id.skipSongButton)
        backSongButton = findViewById(R.id.backSongButton)

        val ec = Ecouteur()

        pausePlayButton.setOnClickListener(ec)
        skip10Button.setOnClickListener(ec)
        skipSongButton.setOnClickListener(ec)
        backSongButton.setOnClickListener(ec)
        back10Button.setOnClickListener(ec)


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

    inner class Ecouteur : View.OnClickListener
    {
        override fun onClick(v: View?) {
            if (v == pausePlayButton){

            } else if (v == backSongButton){

            }  else if (v == back10Button){

            } else if (v == skip10Button){

            } else if (v == skipSongButton){

            } else {
                finish()
            }
        }
    }
}