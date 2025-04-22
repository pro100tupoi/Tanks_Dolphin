package com.example.tanksbattle_dolphin.models

import android.view.View
import com.example.tanksbattle_dolphin.enums.Direction


data class Bullet (
    val view: View,
    val direction: Direction,
    val tank: Tank,
    var canMoveFurther: Boolean = true
    )

