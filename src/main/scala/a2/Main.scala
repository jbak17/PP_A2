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
  val size_of_swarm: Int = 500
  //NOTE: only 60fps are calculated: the time required for this simulation will be (iterations/60) seconds.
  val iterations: Int = 7200


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
    InsectAxis.setAutoRanging(false)
    InsectAxis.setLabel("Insect Number")

    // An axis for the height
    val heightAxis = new NumberAxis(-2.0, 2.0, 0.1)
    heightAxis.setAnimated(false)
    heightAxis.setLabel("Height")


    val HeightChart = new BarChart(InsectAxis, heightAxis)
    HeightChart.setLegendVisible(true)
    HeightChart.setAnimated(true)
    HeightChart.setTitle("Insect heights")
    HeightChart.setVerticalGridLinesVisible(false)
    HeightChart.setHorizontalGridLinesVisible(false)
    HeightChart.setStyle("-fx-bar-fill: red;")

    // These series hold the actual data points. Note that it is "Number" because it's a NumberAxis
    val heightSeries = new Series[String, Number]()
    heightSeries.setName("Insects")

    /**
      * The game state. This is just about the only var in the program!
      */
    var searchSwarm = Simulator(size_of_swarm)


    /*
     * Initialise the chart with values. We have to add them to the series
     */
    for ( insect <- searchSwarm.swarm)
    {
      heightSeries.getData.add(new XYChart.Data(insect.insect_ID.toString, insect.height))
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

      // This keeps track of how many iterations have been completed.
      var iter_counter: Int = 0

      /**
        * Called every time JavaFX wants to render another frame.
        */
      override def handle(now: Long): Unit = {

        // Update the game state
        Simulator.run(searchSwarm)

        // Draw the insects on the canvas
        releaseSwarm(searchSwarm, c)


        // Update the data series
        // JavaFX charts use "ObservableLists".
        // getData gets the list, and then because JavaFX works using mutable data, we can update the datapoint
        for (insect <- searchSwarm.swarm)
        {
          heightSeries.getData.get(insect.insect_ID -1 ).setYValue(insect.height)
        }

        //end simulation after requested number of simulations
        iter_counter += 1
        if (iter_counter == iterations) {
          this.stop()
          primaryStage.close()
          println("Simulation completed " + iterations + " iterations using " + size_of_swarm +".\n The highest position found was " + Insect.global_max_value)
        }

      }
    }.start()

  }

  /*
   * Our code for drawing the asteroids on the canvas
   */
  def releaseSwarm(state:Simulator, c:Canvas):Unit = {

    // Get the "graphics context" that holds the drawing commands
    val g2d = c.getGraphicsContext2D

    // Blank the canvas
    g2d.setFill(Color.WHEAT)
    g2d.fillRect(0, 0, c.getWidth, c.getHeight)

    // Render the items
    for (gameItem <- state.swarm)
    {
      gameItem match {
        case Insect(position, velocity, id) =>
          // Note that we have to set the fill colour before we fill
          val size: Int = 10
          g2d.setFill(Color.BLACK)
          //tranlate for canvas
          val (x, y) = position

          g2d.fillOval(x, y, size, size)
      }

    }
    //mark highest position seen
    g2d.setFill(Color.RED)
    g2d.fillOval(Insect.global_max_position._1, Insect.global_max_position._2, 10, 10)

  }

}

object  Main {

  def main(args: Array[String]): Unit ={
    Application.launch(classOf[Main], args: _*)
  }
}
