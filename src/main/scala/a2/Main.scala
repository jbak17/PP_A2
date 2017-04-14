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
  * Created by jeva on 5/04/17.
  */
class Main extends Application {

  // -------  CONTROLS --------
  val iterations: Int = 200
  val size_of_swarm: Int = 10


  override def start(primaryStage:Stage) = {

    /*
     * The canvas will show our swarm results
     */
    val height: Int = 500
    val width: Int = 500
    val c:Canvas = new Canvas(height, width)

    /*
     * We're going to have showing the height of each insect.
     */

    // An axis for the insect number
    val InsectAxis = new CategoryAxis()
    InsectAxis.setAnimated(false)
    InsectAxis.setTickLabelsVisible(false)


    // An axis for the height
    val heightAxis = new NumberAxis(-2, 2, 0.1)
    heightAxis.setAnimated(true)


    val HeightChart = new BarChart(InsectAxis, heightAxis)
    HeightChart.setLegendVisible(true)
    HeightChart.setTitle("Insect heights")
    HeightChart.setVerticalGridLinesVisible(false)
    HeightChart.setHorizontalGridLinesVisible(false)
    HeightChart.setBarGap(2.0)

    // These series hold the actual data points. Note that it is "Number" because it's a NumberAxis
    val heightSeries = new Series[String, Number]()

    /**
      * The game state. This is just about the only var in the program!
      */
    var searchSwarm = Simulator(size_of_swarm, iterations)


    /*
     * Initialise the chart with values. We have to add them to the series
     */
    for {
      (insect, index) <- searchSwarm.swarm.zipWithIndex
      }
      {
      val (height) = insect.height
      heightSeries.getData.add(new XYChart.Data(index.toString, height))
      }

    /*
     * And then we mustn't forget to add the series into the charts!
     */
    HeightChart.getData.add(heightSeries)

    /*
     * A panel to layout our UI components
     */
    val pane = new GridPane()
    pane.add(c, 0, 0)
    pane.add(HeightChart, 0, 1)

    /*
     * And then put that into a Scene on a Stage
     */
    val scene = new Scene(pane)
    primaryStage.setScene(scene)
    primaryStage.setOnCloseRequest((e) => System.exit(0))
    primaryStage.show()



    /*
     * Rather than a Java Timer, I'm using an AnimationTimer. This is called once per frame by JavaFX. It'll
     * usually put in a frame-rate cap of 60 frames per second.
     *
     * This has the advantage that it's called on the UI thread. As the calculations I'm doing are fairly quick
     * there's little noticeable lag.
     */
    new AnimationTimer() {

      // This keeps track of the timestamp of the previous frame
      var last:Long = 0

      /**
        * Called every time JavaFX wants to render another frame.
        */
      override def handle(now: Long): Unit = {

        /*
         * AnimationTimer receives the time in nanoseconds. This means we need to be careful about division -- if we
         * divide by 1 billion using a Long, we will get zero. We need to make sure our division uses a Double.
         */
        val time = if (last > 0) (now - last) / 1000000000.0 else 1
        last = now

        // Update the game state
        Simulator.run(searchSwarm)

        // Draw the insects on the canvas
        releaseSwarm(searchSwarm, c)


        // Update the data series
        // JavaFX charts use "ObservableLists".
        // getData gets the list, and then because JavaFX works using mutable data, we can update the datapoint
        for {
          (insect, i) <- searchSwarm.swarm.zipWithIndex
        } {
          heightSeries.getData.get(i).setYValue(insect.height)
          }

        Thread.sleep(500)

      }
    }.start()

  }

  /*
   * Our code for drawing the asteroids on the canvas
   */
  def releaseSwarm(state:Simulator, c:Canvas):Unit = {

    import Insect._

    // Get the "graphics context" that holds the drawing commands
    val g2d = c.getGraphicsContext2D

    // Blank the canvas
    g2d.setFill(Color.BLACK)
    g2d.fillRect(0, 0, c.getWidth, c.getHeight)

    // Render the items
    for (gameItem <- state.swarm)
     {
      gameItem match {
        case Insect(position, velocity, id) =>
          // Note that we have to set the fill colour before we fill
          val size: Int = 10
          g2d.setFill(Color.YELLOW)
          val (x, y) = position
          //g2d.fillOval(x*(c.getHeight/Insect.max_limit), y*(c.getHeight/Insect.max_limit), size, size)
          g2d.fillOval(x, y, size, size)
      }

     }
    //mark highest position seen
    g2d.setFill(Color.RED)
    //g2d.fillOval(Insect.global_max_position._1*(c.getHeight/Insect.max_limit), Insect.global_max_position._2*(c.getHeight/Insect.max_limit), 10, 10)
    g2d.fillOval(Insect.global_max_position._1, Insect.global_max_position._2, 10, 10)

  }



}

object  Main {

  def main(args: Array[String]): Unit ={
    Application.launch(classOf[Main], args: _*)
  }

}