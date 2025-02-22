package com.example.tanksbattle_dolphin.models

import com.example.tanksbattle_dolphin.enums.Material

data class Element(
    val viewId: Int,
    val material: Material,
    val coordinate: Coordinate
) {
}