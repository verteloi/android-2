package com.decinfo.tp1

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.annotation.BoolRes
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
    var position:  Int = 0
    lateinit var seekBar: SeekBar
    var pauseBool: Boolean = true
    var m: MonTimer? = null


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

        position = intent.getIntExtra("chansonChoisi", 0)

        mediaItem = MediaItem.fromUri(Modele.v.get(position).get("previewUrl").toString())
        coverUrl = Modele.v.get(position).get("artworkUrl100").toString()

        Glide.with(this)
            .load(coverUrl)
            .into(songImage)

        vue = findViewById(R.id.vue)

        player = ExoPlayer.Builder(this).build()
        // vue.setUseController(false);

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
        pausePlayButton.setImageResource(R.drawable.pause)

        seekBar.max = 30000
        seekBar.progress = 0

        m = MonTimer(30000)

        m?.start()
    }

    inner class Ecouteur : View.OnClickListener
    {
        override fun onClick(v: View?) {
            if (v == pausePlayButton){
                if (pauseBool) {
                    player!!.pause()
                    pauseBool = false

                    m?.cancel()

                    pausePlayButton.setImageResource(R.drawable.play)
                }
                else {
                    player?.play()
                    pauseBool = true

                    val tempsRestant = 30000 - player!!.currentPosition
                    m = MonTimer(tempsRestant)
                    m?.start()

                    pausePlayButton.setImageResource(R.drawable.pause)
                }

            } else if (v == backSongButton){

                position--
                if (position == 0) {
                    position = Modele.v.size - 1
                }

                player?.stop()
                player?.clearMediaItems()
                mediaItem = MediaItem.fromUri(Modele.v[position]["previewUrl"].toString())
                player?.addMediaItem(mediaItem)
                player?.prepare()
                player?.play()

                Glide.with(this@EcouterChanson)
                    .load(Modele.v[position]["artworkUrl100"].toString())
                    .into(songImage)

                pauseBool = true
                pausePlayButton.setImageResource(R.drawable.pause)

                m?.cancel()
                seekBar.progress = 0
                m = MonTimer(30000)
                m?.start()

            } else if (v == back10Button) {
                seekBar.progress -= 10000
                seekBar.progress = if (seekBar.progress > 0) seekBar.progress else 0

                if (player!!.currentPosition < 10000) {
                    player!!.seekTo(0)
                } else {
                    player!!.seekTo(player!!.currentPosition-10000)
                }

            } else if (v == skip10Button) {
                player!!.seekTo(player!!.currentPosition+10000)
                seekBar.progress = player!!.currentPosition.toInt()

            } else if (v == skipSongButton) {
                position++
                if (position > Modele.v.size) {
                    position = 0
                }

                player?.stop()
                player?.clearMediaItems()
                mediaItem = MediaItem.fromUri(Modele.v[position]["previewUrl"].toString())
                player?.addMediaItem(mediaItem)
                player?.prepare()
                player?.play()

                Glide.with(this@EcouterChanson)
                    .load(Modele.v[position]["artworkUrl100"].toString())
                    .into(songImage)

                pauseBool = true
                pausePlayButton.setImageResource(R.drawable.pause)

                m?.cancel()
                seekBar.progress = 0
                m = MonTimer(30000)
                m?.start()
            } else {
                finish()
            }
        }
    }

    inner class MonTimer(tempsRestant: Long) : CountDownTimer(tempsRestant, 50) {
        override fun onTick(mili: Long) {
            seekBar.progress = (player!!.currentPosition).toInt()
        }

        override fun onFinish() {
            seekBar.progress = 30000
        }
    }
}

