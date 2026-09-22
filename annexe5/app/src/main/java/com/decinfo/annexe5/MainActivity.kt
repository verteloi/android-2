package com.decinfo.annexe5

import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var liste: ListView
    val v = ArrayList<HashMap<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        liste = findViewById(R.id.listView)

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

        h = HashMap()
        h.put("position", 8)
        h.put("nom", "Nothing's gonna stop me now")
        h.put("date", "?")
        h.put("image", R.drawable.nothing)

        v.add(h)

        h = HashMap()
        h.put("position", 31)
        h.put("nom", "Santa Maria")
        h.put("date", "28/03/1998")
        h.put("image", R.drawable.santamaria)

        v.add(h)

        h = HashMap()
        h.put("position", 108)
        h.put("nom", "Hot Boy")
        h.put("date", "10/04/2018")
        h.put("image", R.drawable.hotboy)

        v.add(h)

        return v;
    }
}