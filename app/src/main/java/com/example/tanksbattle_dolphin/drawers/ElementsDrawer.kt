package com.example.tanksbattle_dolphin.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.tanksbattle_dolphin.CELL_SIZE
import com.example.tanksbattle_dolphin.R
import com.example.tanksbattle_dolphin.Utils.getElementByCoordinates
import com.example.tanksbattle_dolphin.binding
import com.example.tanksbattle_dolphin.enums.Direction
import com.example.tanksbattle_dolphin.enums.Material
import com.example.tanksbattle_dolphin.models.Coordinate
import com.example.tanksbattle_dolphin.models.Element

class ElementsDrawer(val container: FrameLayout) {
    var currentMaterial = Material.EMPTY
    val  elementsOnContaier = mutableListOf<Element>()

    fun onTouchContainer(x: Float, y: Float){
        val topMargin = y.toInt() - (y.toInt()% CELL_SIZE)
        val leftMargin = x.toInt() - (x.toInt()% CELL_SIZE)
        val coordinate = Coordinate(topMargin, leftMargin)
        if(currentMaterial == Material.EMPTY){
            eraseView(coordinate)
        } else{
            drawOrReplaceView(coordinate)
        }
    }

    private fun drawOrReplaceView(coordinate: Coordinate){
        val viewOnCoordinate = getElementByCoordinates(coordinate, elementsOnContaier)
        if (viewOnCoordinate == null){
            drawView(coordinate)
            return
        }
        if(viewOnCoordinate.material !=currentMaterial){
            replaceView(coordinate)
        }
    }

    private fun replaceView(coordinate: Coordinate){
        eraseView(coordinate)
        drawView(coordinate)
    }

    private fun eraseView(coordinate: Coordinate){
        val elementOnCoordinate = getElementByCoordinates(coordinate, elementsOnContaier)
        if (elementOnCoordinate != null){
            val erasingView = container.findViewById<View>(elementOnCoordinate.viewId)
            container.removeView(erasingView)
            elementsOnContaier.remove(elementOnCoordinate)
        }
    }

    fun drawView(coordinate: Coordinate){
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(CELL_SIZE, CELL_SIZE)
        when (currentMaterial){
            Material.EMPTY->{

            }

            Material.BRICK->view.setImageResource(R.drawable.brick)
            Material.CONCRETE->view.setImageResource(R.drawable.concrete)
            Material.GRASS->view.setImageResource(R.drawable.grass)
        }
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val viewId = View.generateViewId()
        view.id=viewId
        view.layoutParams = layoutParams
        container.addView(view)
        elementsOnContaier.add(Element(viewId, currentMaterial, coordinate))
    }
}