package a2


import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.chart.XYChart.Series
import javafx.scene.chart._
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.stage.Stage


/**
  * Created by jeva on 31/03/17.
  * Drives simulation
  */
object A2{

  type Velocity = (Double, Double)
  type Position = (Double, Double)
  type Swarm = Seq[Insect]

}


