package com.example.tanksbattle_dolphin.sounds

import android.content.Context
import android.media.MediaPlayer
import com.example.tanksbattle_dolphin.R

private const val INTR0_MUSIC_INDEX = 0
private const val BULLET_SHOT_INDEX = 1
private const val BULLET_BURST_INDEX = 2
private const val TANK_MOVE_INDEX = 3

class MainSoundManager(val context: Context) {

    private val sounds = mutableListOf<GameSound>()
    private val soundPool = SoundPoolFactory().createSoundPool()

    fun loadSounds() {
        sounds.add(
            INTR0_MUSIC_INDEX, GameSound(
                resourceInPool = soundPool.load(context, R.raw.tanks_pre_music, 1),
                pool = soundPool
            )
        )
        sounds.add(BULLET_SHOT_INDEX, GameSound(
            resourceInPool = soundPool.load(context, R.raw.bullet_shot, 1),
            pool = soundPool
        ))
        sounds.add(BULLET_BURST_INDEX, GameSound(
            resourceInPool = soundPool.load(context, R.raw.bullet_burst, 1),
            pool = soundPool
        ))
        sounds.add(TANK_MOVE_INDEX, GameSound(
            resourceInPool = soundPool.load(context, R.raw.tank_move_long, 1),
            pool = soundPool
        ))
    }

    fun playIntroMusic() {
        sounds[INTR0_MUSIC_INDEX].startOrResume(isLooping = false)
    }

    fun pauseSounds() {
        pauseSounds(INTR0_MUSIC_INDEX)
        pauseSounds(BULLET_SHOT_INDEX)
        pauseSounds(BULLET_BURST_INDEX)
        pauseSounds(TANK_MOVE_INDEX)
    }

    private fun pauseSounds(index: Int) {
        sounds[index].pause()
    }

    fun bulletShot() {
        sounds[BULLET_SHOT_INDEX].play()
    }

    fun bulletBurst() {
        sounds[BULLET_BURST_INDEX].play()
    }

    fun tankMove() {
        sounds[TANK_MOVE_INDEX].startOrResume(isLooping = true)
    }

    fun tankStop() {
        sounds[TANK_MOVE_INDEX].play()
    }
}