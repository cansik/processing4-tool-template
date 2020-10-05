package ch.bildspur.tool

import com.jogamp.newt.javafx.NewtCanvasJFX
import com.jogamp.newt.opengl.GLWindow
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.stage.Stage
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
            Sketch.run()

            thread {
                Sketch.runningMutex.acquire()
                Platform.runLater {
                    val newtCanvas = Sketch.surface.native as GLWindow

                    // include jfx panel
                    val glPanel = NewtCanvasJFX(newtCanvas)
                    glPanel.width = Sketch.width.toDouble()
                    glPanel.height = Sketch.height.toDouble()
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