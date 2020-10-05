package ch.bildspur.tool.renderer

import ch.bildspur.timer.TimerTask

interface IRenderer {
    val timerTask: TimerTask

    fun setup()

    fun render()

    fun dispose()
}