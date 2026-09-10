package com.decinfo.annexe0

object SingletonSimle {
    var listMemo = ArrayList<Memo>()

    fun recupérerList():ArrayList<Memo>{
        return listMemo
    }
    fun ajouterList(memo: Memo){
        listMemo.add(memo)
    }
}