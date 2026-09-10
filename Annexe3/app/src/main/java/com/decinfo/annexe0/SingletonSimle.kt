package com.decinfo.annexe0

import android.content.Context
import android.content.Context.MODE_PRIVATE
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object SingletonSimle {
    var listMemo = ArrayList<Memo>()

    fun recupérerList():ArrayList<Memo>{
        return listMemo
    }
    fun ajouterList(memo: Memo){
        listMemo.add(memo)
    }

    fun serializerListe(context: Context) {
        try {
            val fos : FileOutputStream = context.openFileOutput("fichier.ser", MODE_PRIVATE)
            val oos = ObjectOutputStream(fos) // buffer special pour écrire des objects and le flux de données binaire
            oos.use {
                oos.writeObject(listMemo)
            }
        } catch (e: Exception) {
            println("probleme")
        }
    }

    // recuperer la liste du fichier de la serialisation et retourner une copie de la liste
    fun deserializerListe(context: Context) : ArrayList<Memo> {
        if (listMemo.isEmpty()) { // pour rviter d'utiliser le fichier de serialisation sans en aboir vraiment besoin
            val fis: FileInputStream = context.openFileInput("fichier.ser")
            val ois = ObjectInputStream(fis)
            ois.use {
                listMemo = ois.readObject() as ArrayList<Memo>
            }
        }
        return ArrayList(listMemo)
    }
}