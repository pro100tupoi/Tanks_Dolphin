package com.example.tanksbattle_dolphin

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.tanksbattle_dolphin.models.Element
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

const val KEY_LEVEL = "key_level"

class LevelStorage(val context: Context) {
    private val prefs = (context as Activity).getPreferences(MODE_PRIVATE)

    fun saveLevel(elementsOnContainer: List<Element>){
        prefs.edit()
            .putString(KEY_LEVEL, Gson().toJson(elementsOnContainer))
            .apply()
    }

    fun loadLevel():List<Element>? {
        val levelFromPrefs = prefs.getString(KEY_LEVEL, null) ?: return null
        val type = object : TypeToken<List<Element>>() {}.type
        val elementsFromStorage: List<Element> = Gson().fromJson(levelFromPrefs, type)// хз хз что то тут ни так
        val elementsWithNewIds = mutableListOf<Element>()
        elementsFromStorage.forEach {
            elementsWithNewIds.add(
                Element(
                    material = it.material,
                    coordinate = it.coordinate
                )
            )
        }
        return elementsWithNewIds
    }
}