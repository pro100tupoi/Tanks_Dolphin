package com.example.tanksbattle_dolphin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_LEFT
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.view.KeyEvent.KEYCODE_DPAD_UP
import android.view.KeyEvent.KEYCODE_SPACE
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.content.ContextCompat
import com.example.tanksbattle_dolphin.GameCore.isPlaying
import com.example.tanksbattle_dolphin.GameCore.startOrPauseTheGame
import com.example.tanksbattle_dolphin.Utils.getElementByCoordinates
import com.example.tanksbattle_dolphin.enums.Direction.UP
import com.example.tanksbattle_dolphin.enums.Direction.DOWN
import com.example.tanksbattle_dolphin.enums.Direction.LEFT
import com.example.tanksbattle_dolphin.enums.Direction.RIGHT
import com.example.tanksbattle_dolphin.databinding.ActivityMainBinding
import com.example.tanksbattle_dolphin.drawers.BulletDrawer
import com.example.tanksbattle_dolphin.drawers.ElementsDrawer
import com.example.tanksbattle_dolphin.drawers.EnemyDrawer
import com.example.tanksbattle_dolphin.drawers.GridDrawer
import com.example.tanksbattle_dolphin.enums.Direction
import com.example.tanksbattle_dolphin.enums.Material
import com.example.tanksbattle_dolphin.enums.Material.EAGLE
import com.example.tanksbattle_dolphin.enums.Material.PLAYER_TANK
import com.example.tanksbattle_dolphin.models.Coordinate
import com.example.tanksbattle_dolphin.models.Element
import com.example.tanksbattle_dolphin.models.Tank

const val CELL_SIZE = 50

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var editMode = false
    private lateinit var item: MenuItem

    private lateinit var playerTank: Tank
    private lateinit var eagle: Element

    private val bulletDrawer by lazy {
        BulletDrawer(
            binding.container,
            elementsDrawer.elementsOnContaier,
            enemyDrawer
        )
    }

    private fun createTank(elementWidth: Int, elementHight: Int): Tank {
        playerTank = Tank(
            Element(
                material =  PLAYER_TANK,
                coordinate = getPlayerTankCoordinate(elementWidth, elementHight),
            ), UP ,
            enemyDrawer
        )
        return playerTank
    }


    private fun getPlayerTankCoordinate(width: Int, height: Int) = Coordinate(
        top = (height - height % 2) - (height - height % 2) % CELL_SIZE - PLAYER_TANK.height * CELL_SIZE,
        left = (width - width % (2 * CELL_SIZE)) / 2 - EAGLE.width / 2 * CELL_SIZE - PLAYER_TANK.width * CELL_SIZE
    )

    private fun createEagle(elementWidth: Int, elementHight: Int): Element {
        eagle = Element(
            material = EAGLE,
            coordinate = getEagleCoordinate(elementWidth, elementHight)
        )
        return eagle
    }

    private fun getEagleCoordinate(width: Int, height: Int) = Coordinate(
        top = (height - height % 2) - (height - height % 2) % CELL_SIZE - EAGLE.height * CELL_SIZE,
        left = (width - width % (2 * CELL_SIZE)) / 2 - EAGLE.width / 2 * CELL_SIZE
    )

    private val gridDrawer by lazy {
        GridDrawer(binding.container)
    }

    private val elementsDrawer by lazy{
        ElementsDrawer(binding.container)
    }

    private val levelStorage by lazy{
        LevelStorage(this)
    }

    private val enemyDrawer by lazy{
        EnemyDrawer(binding.container, elementsDrawer.elementsOnContaier)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Menu"

        binding.editorClear.setOnClickListener{ elementsDrawer.currentMaterial = Material.EMPTY}
        binding.editorBrick.setOnClickListener{ elementsDrawer.currentMaterial = Material.BRICK}
        binding.editorConcrete.setOnClickListener{
            elementsDrawer.currentMaterial=Material.CONCRETE
        }
        binding.editorGrass.setOnClickListener{ elementsDrawer.currentMaterial = Material.GRASS}
        binding.container.setOnTouchListener{ _, event ->
            if (!editMode) {
                return@setOnTouchListener true
            }
            elementsDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
        elementsDrawer.drawElementsList(levelStorage.loadLevel())
        hideSettings()
        countWidthHeight()
    }

    private fun countWidthHeight() {
        val frameLayout = binding.container
        frameLayout.viewTreeObserver
            .addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    frameLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val elementWidth = frameLayout.width
                    val elementHight = frameLayout.height

                    playerTank = createTank(elementWidth, elementHight)
                    eagle = createEagle(elementWidth, elementHight)

                    elementsDrawer.drawElementsList(listOf(playerTank.element, eagle))
                    enemyDrawer.bulletDrawer = bulletDrawer
                }
            })
    }

    private fun switchEditMode() {
        editMode =!editMode
        if(editMode) {
            showSettings()
        } else {
            hideSettings()
        }
    }

    private fun showSettings(){
        gridDrawer.drawGrid()
        binding.materialsContainer.visibility = VISIBLE

    }

    private fun hideSettings(){
        gridDrawer.removeGrid()
        binding.materialsContainer.visibility = INVISIBLE

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        item = menu!!.findItem(R.id.menu_play)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_settings->{
                switchEditMode()
                return true
            }

            R.id.menu_save->{
                levelStorage.saveLevel(elementsDrawer.elementsOnContaier)
                return true
            }

            R.id.menu_play -> {
                if (editMode) {
                    return true
                }
                startOrPauseTheGame()
                if (isPlaying()) {
                    startTheGame()
                } else{
                    pauseTheGame()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun pauseTheGame() {
        item.icon = ContextCompat.getDrawable(this, R.drawable.ic_play)
        GameCore.pauseTheGame()
    }

    private fun startTheGame(){
        item.icon = ContextCompat.getDrawable(this, R.drawable.ic_pause)
        enemyDrawer.startEnemyCreation()
    }

    override fun onPause() {
        super.onPause()
        pauseTheGame()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (!isPlaying()) {
            return super.onKeyDown(keyCode, event)
        }
        when(keyCode)
        {
            KEYCODE_DPAD_DOWN -> move(DOWN)
            KEYCODE_DPAD_UP -> move(UP)
            KEYCODE_DPAD_LEFT -> move(LEFT)
            KEYCODE_DPAD_RIGHT -> move(RIGHT)
            KEYCODE_SPACE -> bulletDrawer.addNewBulletForTank(playerTank)
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun move(direction: Direction) {
        playerTank.move(direction, binding.container, elementsDrawer.elementsOnContaier)
    }
}
// androidx.constraintlayout.widget.ConstraintLayout