package com.decinfo.tp1

import java.io.Serializable

data class Serialisation(
    var choixArtiste: Int,
    var positionChanson: Int,
    var temps: Long
) : Serializable