package com.example.tanksbattle_dolphin.drawers

import android.icu.lang.UProperty
import android.widget.FrameLayout
import com.example.tanksbattle_dolphin.CELL_SIZE
import com.example.tanksbattle_dolphin.Utils.drawElement
import com.example.tanksbattle_dolphin.enums.CELLS_TANKS_SIZE
import com.example.tanksbattle_dolphin.enums.Material
import com.example.tanksbattle_dolphin.models.Coordinate
import com.example.tanksbattle_dolphin.models.Element

private const val MAX_ENEMY_AMOUNT = 20

class EnemyDrawer(private val container: FrameLayout,
    private val elements: MutableList<Element>
    ) {
    private val respawnList: List<Coordinate>
    private var enemyAmount = 0
    private var currentCoordinate:Coordinate


    init{
        respawnList = getRespawnList()
        currentCoordinate = respawnList[0]
    }

    private fun getRespawnList(): List<Coordinate> {
        val respawnList = mutableListOf<Coordinate>()
        respawnList.add(Coordinate(0,0))
        respawnList.add(
            Coordinate(
                0,
                ((container.width - container.width % CELL_SIZE) / CELL_SIZE -
                        (container.width - container.width % CELL_SIZE) / CELL_SIZE % 2 ) *
                        CELL_SIZE / 2 - CELL_SIZE * CELLS_TANKS_SIZE
            )
        )
        respawnList.add(
                        Coordinate(
                            0,
                            (container.width -  container.width % CELL_SIZE) - CELL_SIZE * CELLS_TANKS_SIZE
                        )
        )
        return respawnList
    }

    private fun drawEnemy() {
        var index = respawnList.indexOf(currentCoordinate) + 1
        if (index == respawnList.size) {
            index = 0
        }
        currentCoordinate = respawnList[index]
        val enemyTankElement = Tank(
            Element(
                material = Material.ENEMY_TANK, //ну пиздец
                coordinate  = currentCoordinate,
                width = Material.ENEMY_TANK.width,
                height = Material.ENEMY_TANK.height
            ), UP
        )
        enemyTankElement.element.drawElement(container)
        elements.add(enemyTank.element)
    }

    fun startEnemyCreating() {
        Thread(Runnable {
            while (enemyAmount < MAX_ENEMY_AMOUNT) {
                drawEnemy()
                enemyAmount++
                Thread.sleep(3000)
            }
        }).start()
    }
}