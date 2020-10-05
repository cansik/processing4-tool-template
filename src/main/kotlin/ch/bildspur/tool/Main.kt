package ch.bildspur.tool

import com.jogamp.newt.javafx.NewtCanvasJFX
import com.jogamp.newt.opengl.GLWindow
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage
import processing.javafx.PSurfaceFX.PApplicationFX.surface
import kotlin.concurrent.thread
import kotlin.system.exitProcess


/**
 * Created by cansik on 04.02.17.
 */
class Main : Application() {

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val box = HBox()

        primaryStage.title = "Tool"
        primaryStage.scene = Scene(box)
        primaryStage.width = 600.0
        primaryStage.height = 400.0

        // setup on shown event
        primaryStage.setOnShowing {
            val sketch = Sketch()
            sketch.run()

            thread {
                sketch.runningMutex.acquire()
                Platform.runLater {
                    val newtCanvas = sketch.surface.native as GLWindow

                    // include jfx panel
                    val glPanel = NewtCanvasJFX(newtCanvas)
                    glPanel.width = sketch.width.toDouble()
                    glPanel.height = sketch.height.toDouble()
                    box.children.add(glPanel)
                }
            }
        }
        primaryStage.setOnCloseRequest {
            exitProcess(0)
        }

        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Main::class.java)
        }
    }
}