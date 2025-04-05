package com.example.tanksbattle_dolphin.enums

const val  CELLS_SIMPLE_ELEMENT = 1
const val  CELLS_EAGLE_WIDTH = 4
const val CELL_EAGLE_HEIGHT = 3

enum class Material(
    val tankConGoThrough: Boolean,
    val bulletCanGoThrough: Boolean,
    val simpleBulletCanDestroy: Boolean,
    val canExistOnlyOne: Boolean,
    val width: Int,
    val height: Int,
    val image: Int
    ) {
    EMPTY(true, true, true, false, 0, 0, 0),
    BRICK(false, false, true, false, CELLS_SIMPLE_ELEMENT, CELLS_SIMPLE_ELEMENT, R.drawable.brick),
    CONCRETE(false, false, false, false, CELLS_SIMPLE_ELEMENT, CELLS_SIMPLE_ELEMENT, R.drawable.brick),
    GRASS(true, true, false, false, CELLS_SIMPLE_ELEMENT, CELLS_SIMPLE_ELEMENT, R.drawable.brick),
    EAGLE(false, false, true, false, CELLS_SIMPLE_ELEMENT, CELLS_SIMPLE_ELEMENT, R.drawable.brick),
}