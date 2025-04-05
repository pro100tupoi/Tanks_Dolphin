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
import com.example.tanksbattle_dolphin.enums.Direction.UP
import com.example.tanksbattle_dolphin.enums.Direction.DOWN
import com.example.tanksbattle_dolphin.enums.Direction.LEFT
import com.example.tanksbattle_dolphin.enums.Direction.RIGHT
import com.example.tanksbattle_dolphin.databinding.ActivityMainBinding
import com.example.tanksbattle_dolphin.drawers.BulletDrawer
import com.example.tanksbattle_dolphin.drawers.ElementsDrawer
import com.example.tanksbattle_dolphin.drawers.GridDrawer
import com.example.tanksbattle_dolphin.drawers.TankDrawer
import com.example.tanksbattle_dolphin.enums.Material

const val CELL_SIZE = 50

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var editMode = false
    private val gridDrawer by lazy {
        GridDrawer(binding.container)
    }

    private val elementsDrawer by lazy{
        ElementsDrawer(binding.container)
    }

    private val tankDrawer by lazy{
        TankDrawer(binding.container)
    }

    private val bulletDrawer by lazy{
        BulletDrawer(binding.container)
    }

    private val levelStorage by lazy{
        LevelStorage(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Menu"

        binding.editorClear.setOnClickListener{ elementsDrawer.currentMaterial = Material.EMPTY}
        binding.editorBrick.setOnClickListener{ elementsDrawer.currentMaterial = Material.BRICK}
        binding.materialsContainer.setOnClickListener{
            elementsDrawer.currentMaterial=Material.CONCRETE
        }
        binding.editorGrass.setOnClickListener{ elementsDrawer.currentMaterial = Material.GRASS}
        binding.editorGrass.setOnClickListener{ elementsDrawer.currentMaterial = Material.EAGLE}
        binding.container.setOnTouchListener{ _, event ->
            elementsDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
        elementsDrawer.drawElementsList(levelStorage.loadLevel())
    }

    private fun switchEditMode() {
        if(editMode) {
            gridDrawer.removeGrid()
            binding.materialsContainer.visibility = INVISIBLE
        } else {
            gridDrawer.drawGrid()
            binding.materialsContainer.visibility = VISIBLE
        }
        editMode =!editMode
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
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

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean
    {
        when(keyCode)
        {
            KEYCODE_DPAD_DOWN -> tankDrawer.move(binding.myTank, DOWN, elementsDrawer.elementsOnContaier)
            KEYCODE_DPAD_UP -> tankDrawer.move(binding.myTank, UP, elementsDrawer.elementsOnContaier)
            KEYCODE_DPAD_LEFT -> tankDrawer.move(binding.myTank, LEFT, elementsDrawer.elementsOnContaier)
            KEYCODE_DPAD_RIGHT -> tankDrawer.move(binding.myTank, RIGHT, elementsDrawer.elementsOnContaier)

            KEYCODE_SPACE->bulletDrawer.makeBulletMove(binding.myTank,tankDrawer.currentDirection, elementsDrawer.elementsOnContaier)
        }
        return super.onKeyDown(keyCode, event)
    }
}
// androidx.constraintlayout.widget.ConstraintLayout