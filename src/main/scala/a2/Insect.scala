package a2

import a2.A2.{Position, Velocity}
import com.sun.xml.internal.bind.v2.runtime.Coordinator
import com.sun.xml.internal.messaging.saaj.soap.impl.HeaderImpl

import scala.collection.mutable.ArrayBuffer

/**
  * Created by jeva on 31/03/17.
  *
  * 	- Insect
  * Attributes:
  *- local_max_height = highest point seen by this particle
   - local_max_position = location of highest point seen
  *- current_position
  *- current_velocity
  **
  *Methods:
  *- tick: updates position, velocity and height
  *- update_velocity
  *- update_position
  *- get_height
  *- spawn - create new insects
   - create_random_position and create_random_velocity - helpers for spawn
  */


case class Insect(position: Position, velocity: Velocity, id:Int) {
  val insect_ID: Int = id
  var local_max_position: Position = position
  var local_max_height: Double = Insect.update_height(this)
  var height: Double = Insect.height(position._1, position._2) //keep height in variable as
  //it is used several times per iteration.

}

object Insect {
  val r = scala.util.Random
  var id_counter = 0

  //********* SETTINGS *************
  var global_max_position: Position = (0, 0)
  //initialised well below expect value so that the first
  // iteration will likely find a higher value
  var global_max_value: Double = -1000000.0

  //position limits for terrain
  val min_limit: Int = 0
  val max_limit: Int = 500

  //********* CREATE NEW INSECTS *************
  def spawn(): Insect = {
    id_counter += 1
    //first insect should initialise max height
    val new_insect: Insect = Insect(create_random_position(), create_random_velocity(), id_counter)
    new_insect.local_max_position = new_insect.position
    new_insect.local_max_height = height(new_insect.position._1, new_insect.position._2)
    if (new_insect.local_max_height > Insect.global_max_value){
      Insect.global_max_position = new_insect.position
      Insect.global_max_value = new_insect.height
    }
    new_insect
  }

  //creates a random position (x,y) within boundaries
  def create_random_position(): Position = (r.nextDouble()*max_limit, r.nextDouble()*max_limit)

  //creates random velocity in range (-1.0 to 1.0)
  def create_random_velocity(): Velocity = {
    val velocity = Seq.fill(2)(r.nextDouble()).map(x => if (r.nextBoolean()) x*(-1) else x)

    (velocity(0), velocity(1))
  }

  //********* UPDATES EXISTING INSECTS *************
  //creates a new insect based upon an older one with an updated velocity, height and position
  def tick(insect: Insect): Insect = {

    val new_insect: Insect = insect.copy(
      position = update_position(insect),
      velocity = update_velocity(insect))

    //update height based on new position
    new_insect.height = height(new_insect.position._1, new_insect.position._2)

    //update local maximums if necessary
    if (new_insect.height > new_insect.local_max_height) {
      new_insect.local_max_height = new_insect.height
      new_insect.local_max_position = new_insect.position
    }

    new_insect
  }

  def update_height(insect: Insect): Double = height(insect.position._1, insect.position._2)

  def update_velocity(insect: Insect): Velocity = {
  /*
  updates velocity of insect based on the following formulas
  vx ← ( 0.5 vx ) + 2 r1 ( lx - x ) + 2 r2 ( gx - x )
  vy ← ( 0.5 vy ) + 2 r3 ( ly - y ) + 2 r4 ( gy - y )
  */

    //velocity clamp: checks that velocity does not become too large.
    def speed_check(velocity: Double, min: Double = -1.0, max: Double = 1.0): Double = {
      if (velocity < min)
        {min}
      else if (velocity > max)
        {max}
      else velocity
    }

    //calculates current height for use in formulas
    val x: Double = speed_check((0.5 * insect.velocity._1)+(2*r.nextFloat()*(insect.local_max_position._1 -  insect.position._1))+(2*r.nextFloat()*(global_max_position._1 - insect.position._1)))

    val y: Double = speed_check((0.5 * insect.velocity._2)+(2*r.nextFloat()*(insect.local_max_position._2 - insect.position._2))+(2*r.nextFloat()*(global_max_position._2 - insect.position._2)))

   (x,y)
  }

  def update_position(insect: Insect): Position = {
    //create new positions
    val x_cord = (insect.velocity._1) + insect.position._1
    val y_cord = (insect.velocity._2) + insect.position._2

    //(new_pos(0), new_pos(1))
    (x_cord, y_cord)
  }

  def height(x:Double, y:Double):Double = math.sin(10*math.Pi*x) * math.sin(10*math.Pi*y) - 10*(math.pow(x-0.5, 2) + math.pow(y-0.5, 2))

  //testing formula
  //math.cos(x) + math.sin(y)

}
