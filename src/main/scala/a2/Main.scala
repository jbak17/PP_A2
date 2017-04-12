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
  val size_of_swarm: Int = 50


  override def start(primaryStage:Stage) = {

    /*
     * The canvas will show our swarm results
     */
    val c:Canvas = new Canvas(640, 580)

    /*
     * We're going to have two charts showing the x and y velocities of each insect.
     */

    // An axis for the insect number
    val InsectAxis1 = new CategoryAxis()
    InsectAxis1.setAnimated(false)
    InsectAxis1.setTickLabelsVisible(false)

    // An axis for the x velocity
    val vxAxis = new NumberAxis(-50, 50, 10)
    vxAxis.setAnimated(false)

    // Another axis for the insect number -- the second chart will be oriented in the other direction, so
    // we need to keep the axis separate (not reuse the same axis) so it can size itself.
    val InsectAxis2 = new CategoryAxis()
    InsectAxis2.setAnimated(false)
    InsectAxis2.setTickLabelsVisible(false)

    // An axis for the y velocity
    val vyAxis = new NumberAxis(-50, 50, 10)
    vyAxis.setAnimated(false)

    val yVelocityChart = new BarChart(InsectAxis1, vyAxis)
    yVelocityChart.setLegendVisible(false)
    yVelocityChart.setTitle("Y Velocities")

    val xVelocityChart = new BarChart(vxAxis, InsectAxis2)
    xVelocityChart.setLegendVisible(false)
    xVelocityChart.setTitle("X Velocities")

    // These series hold the actual data points. Note that it is "Number" because it's a NumberAxis
    val yVelocitySeries = new Series[String, Number]()
    val xVelocitySeries = new Series[Number, String]()

    /**
      * The game state. This is just about the only var in the program!
      */
    var searchSwarm = Simulator(size_of_swarm, iterations)


    /*
     * Initialise the chart with values. We have to add them to the series
     */
    for {
      (insect, index) <- searchSwarm.swarm.zipWithIndex
    } {
      val (vx, vy) = insect.velocity
      xVelocitySeries.getData.add(new XYChart.Data(vx, index.toString))
      yVelocitySeries.getData.add(new XYChart.Data(index.toString, vy))
    }

    /*
     * And then we mustn't forget to add the series into the charts!
     */
    yVelocityChart.getData.add(yVelocitySeries)
    xVelocityChart.getData.add(xVelocitySeries)

    /*
     * A panel to layout our UI components
     */
    val pane = new GridPane()
    pane.add(c, 0, 0)
    pane.add(yVelocityChart, 0, 1)
    pane.add(xVelocityChart, 1, 0)

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
          (a, i) <- searchSwarm.swarm.zipWithIndex
        } {
          xVelocitySeries.getData.get(i).setXValue(a.velocity._1)
          yVelocitySeries.getData.get(i).setYValue(a.velocity._2)
        }

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
    for {
      gameItem <- state.swarm
    } {
      gameItem match {
        case Insect(position, velocity) =>
          // Note that we have to set the fill colour before we fill
          val size: Int = 10
          g2d.setFill(Color.YELLOW)
          val (x, y) = position
          g2d.fillOval(x, y, size, size)

      }
    }
  }



}

