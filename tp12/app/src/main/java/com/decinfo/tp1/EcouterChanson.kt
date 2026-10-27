package com.decinfo.tp1

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide


class EcouterChanson : AppCompatActivity() {
    lateinit var vue: PlayerView
    var player: ExoPlayer?= null
    lateinit var mediaItem: MediaItem
    lateinit var coverUrl: String
    lateinit var songImage: ImageView
    lateinit var pausePlayButton: ImageView
    lateinit var skipSongButton: ImageView
    lateinit var skip10Button: ImageView
    lateinit var back10Button: ImageView
    lateinit var backSongButton: ImageView
    lateinit var seekBar: SeekBar

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
        seekBar = findViewById(R.id.seekBar)

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

        var position: Int = intent.getIntExtra("chansonChoisi", 0)

        mediaItem = MediaItem.fromUri(Modele.v.get(position).get("previewUrl").toString())
        coverUrl = MediaItem.fromUri(Modele.v.get(position).get("artworkUrl100").toString()).toString()

        Glide.with(this)
            .load(coverUrl)
            .into(songImage)

        vue = findViewById(R.id.vue)

        player = ExoPlayer.Builder(this).build()
        // vue.setUseController(false);

        val m = MonTimer()
        seekBar.max = 30
        seekBar.progress = 0

        m.start()
    }

    override fun onStart() {
        super.onStart()
        vue.player = player
        player?.addMediaItem(mediaItem)
        player?.prepare()

        player?.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, @Player.MediaItemTransitionReason reason: Int) {

            }

            override fun onPlaybackStateChanged(@Player.State playbackState: Int) {

            }
        })

        player?.play()
    }

    inner class Ecouteur : View.OnClickListener
    {
        override fun onClick(v: View?) {
            if (v == pausePlayButton){
                player!!.pause()

            } else if (v == backSongButton){

            }  else if (v == back10Button){

            } else if (v == skip10Button){

            } else if (v == skipSongButton){

            } else {
                finish()
            }
        }
    }

    inner class MonTimer : CountDownTimer(30000, 200) {
        override fun onTick(mili: Long) {
            seekBar.progress = (player!!.currentPosition / 1000).toInt()
        }

        override fun onFinish() {
            seekBar.progress = 30
        }
    }
}

