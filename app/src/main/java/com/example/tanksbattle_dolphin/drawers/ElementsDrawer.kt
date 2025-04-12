package com.example.tanksbattle_dolphin.drawers

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.tanksbattle_dolphin.CELL_SIZE
import com.example.tanksbattle_dolphin.R
import com.example.tanksbattle_dolphin.Utils.getElementByCoordinates
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

    //fun changeElementsVisibility(editMode: Boolean){
    //    elementsOnContaier
    //        .filter { it.material.visibleInEditableModel }
    //        .forEach { setViewIdVisibility(it.viewId, editMode) }
    //}

    //private fun setViewIdVisibility(viewId:Int,editMode: Boolean){
    //    val view = container.findViewById<View>(viewId)
    //    if(editMode){
    //        view.visibility = View.VISIBLE
    //    } else {
    //        view.visibility = View.GONE
    //    }
    //}

    private fun drawOrReplaceView(coordinate: Coordinate) {
        val viewOnCoordinate = getElementByCoordinates(coordinate, elementsOnContaier)
        if (viewOnCoordinate == null){
            drawView(coordinate)
            return
        }
        if(viewOnCoordinate.material !=currentMaterial){
            replaceView(coordinate)
        }
    }

    fun drawElementsList(elements: List<Element>?) {
        if (elements == null){
            return
        }
        for(element in elements){
            currentMaterial = element.material
            drawView((element.coordinate))
        }
    }

    private fun replaceView(coordinate: Coordinate) {
        eraseView(coordinate)
        drawView(coordinate)
    }

    private fun eraseView(coordinate: Coordinate) {
        removeElement(getElementByCoordinates(coordinate, elementsOnContaier))
        for (element in getElementsUnderCurrentCoordinate(coordinate)) {
            removeElement(element)
        }
    }

    private fun removeElement(element: Element?) {
        if (element != null){
            val erasingView = container.findViewById<View>(element.viewId)
            container.removeView(erasingView)
            elementsOnContaier.remove(element)
        }
    }

    private fun getElementsUnderCurrentCoordinate(coordinate: Coordinate): List<Element>{
        val elements = mutableListOf<Element>()
        for (element in elementsOnContaier){
            for (height in 0 until currentMaterial.height) {
                for (width in 0 until currentMaterial.width) {
                    if (element.coordinate == Coordinate(
                            coordinate.top + height * CELL_SIZE,
                            coordinate.left + width * CELL_SIZE
                        )
                    ) {
                        elements.add(element)
                    }
                }
            }
        }
        return elements
    }

    //private fun removeIfSingleInstance() {
    //    if (currentMaterial.canExistOnlyOne){
    //       elementsOnContaier.firstOrNull { it.material == currentMaterial }?.coordinate?.let {
    //            eraseView(it)
    //        }
    //    }
    //}

    private fun removeUnwantedInstances(){
        if (currentMaterial.elementsAmountOnScreen != 0) {
            val erasingElements = elementsOnContaier.filter { it.material == currentMaterial}
            if (erasingElements.size >= currentMaterial.elementsAmountOnScreen) {
                eraseView(erasingElements[0].coordinate)
            }
        }
    }

    private fun drawView(coordinate: Coordinate) {
        //removeIfSingleInstance()
        removeUnwantedInstances()
        val view = ImageView(container.context)
        val layoutParams = FrameLayout.LayoutParams(
            currentMaterial.width * CELL_SIZE,
            currentMaterial.height * CELL_SIZE
        )
        view.setImageResource(currentMaterial.image)
        layoutParams.topMargin = coordinate.top
        layoutParams.leftMargin = coordinate.left
        val element = Element(
         material = currentMaterial,
         coordinate = coordinate,
         width = currentMaterial.width,
         height = currentMaterial.height
        )
        view.id = element.viewId
        view.layoutParams = layoutParams
        view.scaleType = ImageView.ScaleType.FIT_XY
        container.addView(view)
        elementsOnContaier.add(element)
    }
}