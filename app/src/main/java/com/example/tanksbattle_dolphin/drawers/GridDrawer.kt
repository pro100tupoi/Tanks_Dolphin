package com.example.tanksbattle_dolphin.drawers

import android.content.Context
import android.widget.FrameLayout
import android.view.View
import android.graphics.Color
import com.example.tanksbattle_dolphin.activities.CELL_SIZE

//import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY

class GridDrawer(private val container: FrameLayout?) {
    private val allLines = mutableListOf<View>()

    fun removeGrid() {
        //val container = binding.container
        allLines.forEach{
            container?.removeView(it)
        }
    }

    fun  drawGrid() {
        //val container = binding.container
        drawHorizontalLines()
        drawVerticalLines()
    }

    private fun drawHorizontalLines() {
        var topMargin = 0
        while (topMargin <= container!!.height) {
            val horizontalLine = View(container.context)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 1)
            topMargin += CELL_SIZE
            layoutParams.topMargin = topMargin
            horizontalLine.layoutParams = layoutParams
            horizontalLine.setBackgroundColor(Color.WHITE)
            allLines.add(horizontalLine)
            container.addView(horizontalLine)
        }
    }

    private fun drawVerticalLines() {
        var leftMargin = 0
        while (leftMargin <= container!!.width) {
            val verticalLine = View(container.context)
            val layoutParams = FrameLayout.LayoutParams(1, FrameLayout.LayoutParams.MATCH_PARENT)
            leftMargin += CELL_SIZE
            layoutParams.leftMargin = leftMargin
            verticalLine.layoutParams = layoutParams
            verticalLine.setBackgroundColor(Color.WHITE)
            allLines.add(verticalLine)
            container.addView(verticalLine)
        }
    }
}