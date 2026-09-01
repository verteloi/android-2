package com.decinfo.pratiqueannexe1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        println(calculerNbLigne())
        println(calculerNbCharacteres())
        println(calculerNbCharacteresC())
    }


    fun calculerNbLigne() : Int {
        var compteur = 0

        val fis = openFileInput("ex.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.use {
            var ligne = br.readLine()
            while (ligne != null) {
                compteur++
                ligne = br.readLine()
            }
        }

        return compteur;
    }

    fun calculerNbCharacteres() : Int {
        var compteur = 0

        val fis = openFileInput("ex.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.use {
            var ligne = br.readLine()
            while (ligne != null) {
                compteur += br.readLine().length
                ligne = br.readLine()
            }
        }

        return compteur;
    }

    fun calculerNbCharacteresC() : Int {
        var compteur = 0

        val fis = openFileInput("ex.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        br.use {
            var ligne = br.readLine()
            while (ligne != null) {
                for (i in 0 until ligne.length) {
                    if (ligne[i] == 'c') {
                        compteur++
                    }
                }
                ligne = br.readLine()
            }
        }

        return compteur;
    }
}