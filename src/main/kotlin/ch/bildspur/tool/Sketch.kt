package ch.bildspur.tool

import processing.core.PApplet
import processing.core.PConstants
import java.util.concurrent.Semaphore


/**
 * Created by cansik on 04.02.17.
 */
object Sketch : PApplet() {
    val runningMutex = Semaphore(0)

    init {
    }

    fun run() {
        super.runSketch()
    }

    override fun settings() {
        size(300, 300, PConstants.P2D)
    }

    override fun setup() {
        colorMode(HSB, 360f, 100f, 100f)
    }

    override fun draw() {
        if (frameCount == 1) {
            runningMutex.release()
        }

        background(0)
        circle(mouseX.toFloat(), mouseY.toFloat(), 50f)
    }
}