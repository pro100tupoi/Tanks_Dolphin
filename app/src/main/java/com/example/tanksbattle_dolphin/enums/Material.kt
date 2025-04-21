package com.example.tanksbattle_dolphin.enums

import com.example.tanksbattle_dolphin.R

const val  CELLS_SIMPLE_ELEMENT = 1
const val  CELLS_EAGLE_WIDTH = 4
const val CELL_EAGLE_HEIGHT = 3
const val CELLS_TANKS_SIZE = 2

enum class Material(
    val tankConGoThrough: Boolean,
    val bulletCanGoThrough: Boolean,
    val simpleBulletCanDestroy: Boolean,
    val elementsAmountOnScreen: Int,
    val width: Int,
    val height: Int,
    val image: Int?
    ) {
    EMPTY(true, true, true, 0, 0, 0, null),
    BRICK(false, false, true, 0, CELLS_SIMPLE_ELEMENT, CELLS_SIMPLE_ELEMENT, R.drawable.brick),
    CONCRETE(false, false, false, 0, CELLS_SIMPLE_ELEMENT, CELLS_SIMPLE_ELEMENT, R.drawable.concrete),
    GRASS(true, true, false, 0, CELLS_SIMPLE_ELEMENT, CELLS_SIMPLE_ELEMENT, R.drawable.grass),
    EAGLE(false, false, true, 1, CELLS_EAGLE_WIDTH, CELL_EAGLE_HEIGHT, R.drawable.eagle),
    ENEMY_TANK(false, false, true, 0, CELLS_TANKS_SIZE, CELLS_TANKS_SIZE, R.drawable.enemy_tank),
    PLAYER_TANK(false, false, true, 1, CELLS_TANKS_SIZE, CELLS_TANKS_SIZE, R.drawable.tank),
}