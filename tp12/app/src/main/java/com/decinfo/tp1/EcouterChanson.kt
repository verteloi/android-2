package com.decinfo.tp1

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.BoolRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide
import kotlin.jvm.java

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
    lateinit var songNameText: TextView
    lateinit var artistNameText: TextView
    lateinit var timeText: TextView
    lateinit var credits: TextView
    lateinit var volume: TextView
    lateinit var backSongButton: ImageView
    var position:  Int = 0
    lateinit var seekBar: SeekBar
    var pauseBool: Boolean = true
    var m: MonTimer? = null
    lateinit var lanceur: ActivityResultLauncher<Intent>
    var volumePerm: Float = 0.5f


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
        songNameText = findViewById(R.id.songNameText)
        artistNameText = findViewById(R.id.artistNameText)
        credits = findViewById(R.id.credits)
        volume = findViewById(R.id.volume)
        timeText = findViewById(R.id.timeText)

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
        credits.setOnClickListener(ec)
        volume.setOnClickListener(ec)

        position = intent.getIntExtra("chansonChoisi", 0)

        mediaItem = MediaItem.fromUri(Modele.v.get(position).get("previewUrl").toString())
        coverUrl = Modele.v.get(position).get("artworkUrl100").toString()

        // afficher l'image de l'album
        Glide.with(this)
            .load(coverUrl)
            .into(songImage)

        // afficher titre + artiste selectionné
        songNameText.text = Modele.v[position]["trackName"].toString()
        artistNameText.text = Modele.v[position]["artistName"].toString()

        vue = findViewById(R.id.vue)

        player = ExoPlayer.Builder(this).build()

        // boomerang
        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackUtilisateur()
        )

    }

    override fun onStart() {
        super.onStart()
        vue.player = player
        player?.addMediaItem(mediaItem)
        player?.prepare()
        player?.volume = volumePerm

        player?.addListener(object : Player.Listener {

            // LORSQU'UNE NOUVELLE CHANSON COMMENCE
            override fun onPlaybackStateChanged(@Player.State playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    position++
                    if (position >= Modele.v.size) {
                        position = 0
                    }

                    // configurer le player pour la nouvelle chanson
                    player?.stop()
                    player?.clearMediaItems()
                    mediaItem = MediaItem.fromUri(Modele.v[position]["previewUrl"].toString())
                    player?.addMediaItem(mediaItem)
                    player?.prepare()
                    player?.play()
                    player?.volume = volumePerm

                    // changer l'image pour la nouvelle
                    Glide.with(this@EcouterChanson)
                        .load(Modele.v[position]["artworkUrl100"].toString())
                        .into(songImage)


                    pauseBool = true
                    pausePlayButton.setImageResource(R.drawable.pause)

                    // afficher titre + artiste de la nouvelle chanson
                    songNameText.text = Modele.v[position]["trackName"].toString()
                    artistNameText.text = Modele.v[position]["artistName"].toString()

                    // créer un nouveau timer
                    m?.cancel()
                    seekBar.progress = 0
                    m = MonTimer(30000)
                    m?.start()
                }
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
                    // PAUSE CLIQUÉ
                    player!!.pause()
                    pauseBool = false

                    m?.cancel()

                    pausePlayButton.setImageResource(R.drawable.play)
                }
                else {
                    // PLAY CLIQUÉ
                    player?.play()
                    pauseBool = true

                    val tempsRestant = 30000 - player!!.currentPosition
                    m = MonTimer(tempsRestant)
                    m?.start()

                    pausePlayButton.setImageResource(R.drawable.pause)
                }

            } else if (v == backSongButton){
                // RETOURNER A LA CHANSON PRECEDENTE

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

                // changer cover image
                Glide.with(this@EcouterChanson)
                    .load(Modele.v[position]["artworkUrl100"].toString())
                    .into(songImage)

                pauseBool = true
                pausePlayButton.setImageResource(R.drawable.pause)

                songNameText.text = Modele.v[position]["trackName"].toString()
                artistNameText.text = Modele.v[position]["artistName"].toString()

                m?.cancel()
                seekBar.progress = 0
                m = MonTimer(30000)
                m?.start()

            } else if (v == back10Button) {
                // REVENIR EN ARRIERE DE 10 SECONDES

                seekBar.progress -= 10000
                seekBar.progress = if (seekBar.progress > 0) seekBar.progress else 0

                if (player!!.currentPosition < 10000) {
                    player!!.seekTo(0)
                } else {
                    player!!.seekTo(player!!.currentPosition-10000)
                }

                timeText.text = (player!!.currentPosition / 1000).toString() + " sec"


            } else if (v == skip10Button) {
                // AVANCER DE 10 SECONDES

                player!!.seekTo(player!!.currentPosition+10000)
                seekBar.progress = player!!.currentPosition.toInt()

                timeText.text = (player!!.currentPosition / 1000).toString() + " sec"

            } else if (v == skipSongButton) {
                // SKIP SONG BUTTON

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

                // changer cover image
                Glide.with(this@EcouterChanson)
                    .load(Modele.v[position]["artworkUrl100"].toString())
                    .into(songImage)

                songNameText.text = Modele.v[position]["trackName"].toString()
                artistNameText.text = Modele.v[position]["artistName"].toString()

                pauseBool = true
                pausePlayButton.setImageResource(R.drawable.pause)

                m?.cancel()
                seekBar.progress = 0
                m = MonTimer(30000)
                m?.start()

            } else if (v!!.id == R.id.credits) {
                // LORSQUE CREDITS EST CLIQUÉ

                if (artistNameText.text.toString().lowercase() == "miles nelson") {
                    val i = Intent (Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/intl-fr/artist/5JU2RF8iLDZB1XQRHOqHys"))
                    startActivity(i)
                } else if (artistNameText.text.toString().lowercase() == "meltawaylikematter") {
                    val i = Intent (Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/intl-fr/artist/6QaU8JPCMU6ezFnZryTUsK"))
                    startActivity(i)
                } else if (artistNameText.text.toString().lowercase() == "bbpue") {
                    val i = Intent (Intent.ACTION_VIEW, Uri.parse("https://open.spotify.com/intl-fr/artist/57U4dmjpNdE0CBF1vEOQEj"))
                    startActivity(i)
                }
            } else if (v!!.id == R.id.volume) {
                // LORSQUE LE TEXTE VOLUME EST CLIQUÉ

                val intentVolume = Intent(this@EcouterChanson, VolumeActivity::class.java)
                Log.d("TAG2", player?.volume.toString())
                val volumeActuel = player?.volume
                Log.d("TAG3", player?.volume.toString())

                intentVolume.putExtra("volumeActuel", volumeActuel)
                lanceur.launch(intentVolume)
            }
        }
    }

    override fun onStop() {
        super.onStop()


    }

    // quand l'app est fermé
    override fun onDestroy() {
        super.onDestroy()

        // stop le timer
        m?.cancel()
        m = null

        // stop la lecture de la musique
        player?.stop()
        player?.release()
        player = null
    }


    // timer utilisé pour refresh seekbar + texte secondes
    inner class MonTimer(tempsRestant: Long) : CountDownTimer(tempsRestant, 50) {
        override fun onTick(mili: Long) {
            seekBar.progress = (player!!.currentPosition).toInt()
            timeText.text = (player!!.currentPosition / 1000).toString() + " sec"
        }
        override fun onFinish() {
            seekBar.progress = 30000
        }
    }

    // C'est la que le boomerang reviendra
    inner class CallBackUtilisateur : ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result: ActivityResult) {
            if (result.resultCode == RESULT_OK) {
                val intentRetour = result.data
                // defini le volume par ce qu'on a recu
                Log.d("recu", intentRetour!!.getFloatExtra("volume", 0f).toString())
                volumePerm = intentRetour!!.getFloatExtra("volume", 0f)
                player?.volume = volumePerm
            }
        }
    }
}

