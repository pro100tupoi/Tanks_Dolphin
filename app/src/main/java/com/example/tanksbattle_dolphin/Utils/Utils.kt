package com.example.tanksbattle_dolphin.Utils

import android.view.View
import com.example.tanksbattle_dolphin.binding
import com.example.tanksbattle_dolphin.models.Coordinate
import com.example.tanksbattle_dolphin.models.Element

fun View.checkViewCanMoveThrounghBorder(coordinate: Coordinate): Boolean {
        return coordinate.top >= 0 &&
                coordinate.top + this.height <= binding.container.height &&
                coordinate.left >= 0 &&
                coordinate.left + this.width <= binding.container.width
    }

fun getElementByCoordinates(
    coordinate: Coordinate, elementsOnContaier: List<Element>
) =
    elementsOnContaier.firstOrNull{ it.coordinate == coordinate }
