package com.decinfo.tp1

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListeChansons : AppCompatActivity() {
    lateinit var liste: ListView
    val v = ArrayList<HashMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_liste_chansons)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.listeChansons)

        val adapteur = SimpleAdapter(this, remplirArrayList(), R.layout.un_item, arrayOf("position", "nom", "date", "image"), intArrayOf(R.id.textePosition, R.id.texteTitre, R.id.texteDate, R.id.imageAlbum))

        liste.adapter = adapteur

        liste.setOnItemClickListener{_,_,position,_ -> Toast.makeText(this, v.get(position).get("nom").toString(), LENGTH_LONG).show()}
    }


    fun remplirArrayList() : ArrayList<HashMap<String, Any>> {
        var h = HashMap<String, Any>()
        h.put("position", 3)
        h.put("nom", "Touch Me")
        h.put("date", "22/03/86")
        h.put("image", R.drawable.touchme)

        v.add(h)

        return v;
    }
}