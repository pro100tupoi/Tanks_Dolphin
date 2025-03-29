package com.example.tanksbattle_dolphin.drawers

import android.view.View
import android.widget.FrameLayout
import com.example.tanksbattle_dolphin.CELL_SIZE
import com.example.tanksbattle_dolphin.binding
import com.example.tanksbattle_dolphin.enums.Direction
import com.example.tanksbattle_dolphin.models.Coordinate
import com.example.tanksbattle_dolphin.models.Element

class TankDrawer(val container: FrameLayout) {
    var currentDirection = Direction.UP

    fun move(myTank: View, direction: Direction, elementsOnContainer: List<Element>){
        val layoutParams = myTank.layoutParams as FrameLayout.LayoutParams
        val currentCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
        currentDirection = direction
        myTank.rotation=direction.rotation
        when(direction){
            Direction.UP ->{
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += -CELL_SIZE
            }
            Direction.DOWN ->{
                (myTank.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE
            }
            Direction.LEFT ->{
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin -= CELL_SIZE
            }
            Direction.RIGHT ->{
                (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin += CELL_SIZE
            }
        }

        val nextCoordinate = Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
        if (checkTankCanMoveThrounghBorder(
                nextCoordinate,
                myTank
            ) && checkTankCanMoveThrounghMaterial(nextCoordinate, elementsOnContainer)
        ) {
            binding.container.removeView(myTank)
            binding.container.addView(myTank)
        } else {
            (myTank.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (myTank.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
        }
    }

    private fun checkTankCanMoveThrounghMaterial(
        coordinate: Coordinate,
        elementsOnContainer: List<Element>
    ): Boolean {
        getTankCoordinates(coordinate).forEach {
            val element = getElementByCoordinates(it, elementsOnContainer)
            if (element!=null && !element.material.tankConGoThrough){
                return false
            }
        }
        return true
    }

   //private fun checkTankCanMoveThrounghMaterial(coordinate: Coordinate): Boolean
   // {
   //     getTankCoordinates(coordinate).forEach{
   //         val element = getElementByCoordinates(it)
   //         if (element != null && !element.material.tankConGoThrough) {
    //            return false
    //        }
    //    }
   //     return true
   // }

    private fun checkTankCanMoveThrounghBorder(coordinate: Coordinate, myTank: View): Boolean
    {
        return coordinate.top >= 0 &&
                coordinate.top + myTank.height <= binding.container.height &&
                coordinate.left >= 0 &&
                coordinate.left + myTank.width <= binding.container.width
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

    private fun getElementByCoordinates(
        coordinate: Coordinate, elementsOnContaier: List<Element>
    ) =
        elementsOnContaier.firstOrNull{ it.coordinate == coordinate }
}