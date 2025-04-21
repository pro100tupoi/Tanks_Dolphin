package com.example.tanksbattle_dolphin.Utils

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.tanksbattle_dolphin.CELL_SIZE
import com.example.tanksbattle_dolphin.binding
import com.example.tanksbattle_dolphin.enums.Direction
import com.example.tanksbattle_dolphin.models.Coordinate
import com.example.tanksbattle_dolphin.models.Element
import com.example.tanksbattle_dolphin.models.Tank

const val TOTAL_PERCENT = 100

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

fun getTankByCoordinates(coordinate: Coordinate, tankList: List<Tank>): Element? {
    return getElementByCoordinates(coordinate, tankList.map { it.element })
}

fun Element.drawElement(container: FrameLayout) {
    val view = ImageView(container.context)
    val layoutParams = FrameLayout.LayoutParams(
        this.material.width * CELL_SIZE,
        this.material.height * CELL_SIZE
    )
    this.material.image?.let { view.setImageResource(it) }
    layoutParams.topMargin = this.coordinate.top
    layoutParams.leftMargin = this.coordinate.left
    view.id = this.viewId
    view.layoutParams = layoutParams
    view.scaleType = ImageView.ScaleType.FIT_XY
    container.runOnUiThread {
        container.addView(view)
    }
}

fun FrameLayout.runOnUiThread(block: () -> Unit) {
    (this.context as Activity).runOnUiThread {
        block()
    }
}

fun checkIfChanceBiggerThanRandom(percentChance: Int): Boolean {
    return kotlin.random.Random.nextInt(TOTAL_PERCENT) <= percentChance //снова что то не то
}