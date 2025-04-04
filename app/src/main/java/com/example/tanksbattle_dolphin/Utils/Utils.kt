package com.example.tanksbattle_dolphin.Utils

import android.view.View
import com.example.tanksbattle_dolphin.CELL_SIZE
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
    coordinate: Coordinate,
    elementsOnContaier: List<Element>
): Element? {
    for (element in elementsOnContaier) {
        for (height in 0 until element.height) {
            for (width in 0 until element.width) {
                val searchingCoordinate = Coordinate(
                    top = element.coordinate.top + height * CELL_SIZE,
                    left = element.coordinate.left + width * CELL_SIZE
                )
                if (coordinate == searchingCoordinate) {
                    return element
                }
            }
        }
    }
    return null
 }
