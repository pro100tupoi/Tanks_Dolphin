package com.example.tanksbattle_dolphin.enums

enum class Material(
    val tankConGoThrough: Boolean,
    val bulletCanGoThrough: Boolean,
    val simpleBulletCanDestroy: Boolean
    ) {
    EMPTY(true, true, true),
    BRICK(false, false, true),
    CONCRETE(false, false, false),
    GRASS(true, true, false),
}