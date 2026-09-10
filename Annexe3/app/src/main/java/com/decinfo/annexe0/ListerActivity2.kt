package com.decinfo.annexe0

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

class ListerActivity2 : AppCompatActivity() {
    lateinit var liste: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lister)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        liste = findViewById(R.id.list)
        liste.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trierMemos())

        //liste.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,lireMemo())

    }

    fun trierMemos() : ArrayList<String> {
        val triee = ArrayList<String>()
        var listeActuelle:ArrayList<Memo>? = null

        try {
            listeActuelle = SingletonSimle.recupérerList()
            listeActuelle.sortBy{it.date}
            // listeActuelle.forEach { it -> triee.add(it.memo) }

            for (it in listeActuelle) {
                triee.add(it.memo)
            }
        }
        catch (f: FileNotFoundException) {
            Toast.makeText(this, "pas de fichier", LENGTH_LONG). show()
            finish();
        }
        return triee;
    }
}