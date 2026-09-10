package com.decinfo.annexe0

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AjouterActivity : AppCompatActivity() {
    lateinit var boutonAjouterMemo : Button
    lateinit var buttonDate: Button
    lateinit var champMemo : TextView
    lateinit var champDate : TextView
    var dateChoisie = LocalDate.now().plusDays(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        boutonAjouterMemo = findViewById(R.id.button4)
        buttonDate = findViewById(R.id.echeance)
        champMemo = findViewById(R.id.memo)
        champDate =  findViewById(R.id.date)

        boutonAjouterMemo.setOnClickListener {
            var text = champMemo.text.toString()
            SingletonSimle.ajouterList(Memo(text,dateChoisie))
            finish()
        }
        buttonDate.setOnClickListener {
            val d = DatePickerDialog(this)
            d.setOnDateSetListener { d, annee, mois, jour ->
                dateChoisie = LocalDate.of(annee, mois + 1, jour)
                val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
                val text: String? = dateChoisie.format(formatter)
                champDate.setText(text)
            }
            d.show()
            
        }

    //interface fonctionnelle -> classe qui possède une methode dedans
    }


    override fun onStop() {
        super.onStop()
        try {
            SingletonSimle.serializerListe(applicationContext) // pluto que this pcq notre singleton est visible dans toute l'application
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}