package com.example.tanksbattle_dolphin.enums

enum class Material(val tankConGoThrough: Boolean) {
    EMPTY(true),
    BRICK(false),
    CONCRETE(false),
    GRASS(true),
}