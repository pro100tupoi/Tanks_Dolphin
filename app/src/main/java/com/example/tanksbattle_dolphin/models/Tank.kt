package com.example.tanksbattle_dolphin.models

import android.view.View
import android.widget.FrameLayout
import com.example.tanksbattle_dolphin.CELL_SIZE
import com.example.tanksbattle_dolphin.Utils.*
import com.example.tanksbattle_dolphin.binding
import com.example.tanksbattle_dolphin.drawers.BulletDrawer
import com.example.tanksbattle_dolphin.enums.Direction
import com.example.tanksbattle_dolphin.enums.Material
import kotlin.random.Random

class Tank constructor(
    var element: Element,
    var direction: Direction,
    val bulletDrawer: BulletDrawer
) {
    fun move(
        direction: Direction,
        container: FrameLayout,
        elementsOnContainer: List<Element>
    ){
        val view = container.findViewById<View>(element.viewId) ?: return

        val currentCoordinate = getTankCurrentCoordinate(view)
        this.direction = direction
        view.rotation = direction.rotation
        val nextCoordinate = getTankNextCoordinate(view)
        if (view.checkViewCanMoveThrounghBorder(nextCoordinate)
            && element.checkTankCanMoveThrounghMaterial(nextCoordinate, elementsOnContainer)
        ) {
            emulateViewMoving(container, view)
            element.coordinate = nextCoordinate

        } else {
            element.coordinate = currentCoordinate
            (view.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (view.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
            changeDirectionForEnemyTank()
        }
    }

    private fun generateRandomDirectionForEnemyTank() {
        if (element.material != Material.ENEMY_TANK) {
            return
        }
        if (checkIfChanceBiggerThanRandom(10)) {
            changeDirectionForEnemyTank()
        }
    }

    private fun changeDirectionForEnemyTank() {
        if (element.material == Material.ENEMY_TANK) {
            val randomDirection = Direction.values()[Random.nextInt(Direction.values().size)] //сомнительно но ОК
            this.direction = randomDirection
        }
    }

    private fun emulateViewMoving(container: FrameLayout, view: View) {
        container.runOnUiThread {
            binding.container.removeView(view)
            binding.container.addView(view, 0)
        }
    }

    private fun getTankCurrentCoordinate(tank: View): Coordinate {
        return Coordinate(
            (tank.layoutParams as FrameLayout.LayoutParams).topMargin,
            (tank.layoutParams as FrameLayout.LayoutParams).leftMargin
        )
    }

    private fun getTankNextCoordinate (view: View): Coordinate{
        val layoutParams = view.layoutParams as FrameLayout.LayoutParams
        when(direction){
            Direction.UP ->{
                (view.layoutParams as FrameLayout.LayoutParams).topMargin += -CELL_SIZE
            }
            Direction.DOWN ->{
                (view.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE
            }
            Direction.LEFT ->{
                (view.layoutParams as FrameLayout.LayoutParams).leftMargin -= CELL_SIZE
            }
            Direction.RIGHT ->{
                (view.layoutParams as FrameLayout.LayoutParams).leftMargin += CELL_SIZE
            }
        }
        return Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
    }

    private fun Element.checkTankCanMoveThrounghMaterial(
        coordinate: Coordinate,
        elementsOnContainer: List<Element>
    ): Boolean {
        for (anyCoordinate in getTankCoordinates(coordinate)) {
            var element = getElementByCoordinates(anyCoordinate, elementsOnContainer)
            if (element == null) {
                element = getTankByCoordinates(anyCoordinate, bulletDrawer.enemyDrawer.tanks)
            }
            if (element!=null && !element.material.tankConGoThrough){
                if (this == element){
                    continue
                }
                return false
            }
        }
        return true
    }

    private fun getTankCoordinates(topLeftCoordinate: Coordinate): List<Coordinate>{
        val coordinateList = mutableListOf<Coordinate>()
        coordinateList.add(topLeftCoordinate)
        coordinateList.add(Coordinate(topLeftCoordinate.top + CELL_SIZE, topLeftCoordinate.left))
        coordinateList.add(Coordinate(topLeftCoordinate.top, topLeftCoordinate.left + CELL_SIZE))
        coordinateList.add(
            Coordinate(
                topLeftCoordinate.top + CELL_SIZE,
                topLeftCoordinate.left + CELL_SIZE
            )
        )
        return coordinateList
    }
}