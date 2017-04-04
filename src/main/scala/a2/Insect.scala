package a2

import a2.A2.{Position, Velocity}
import com.sun.xml.internal.messaging.saaj.soap.impl.HeaderImpl

/**
  * Created by jeva on 31/03/17.
  *
  * 	- Insect
  * Attributes:
  *- local_max = highest point seen by this particle
  *- current_position
  *- current_velocity
  **
  *Methods:
  *Public
  *- tick: updates position, velocity and height
  *
  *Private
  *- update_velocity
  *- update_position
  *- get_height
  */


case class Insect(position: Position, velocity: Velocity) {
  var local_max_position: Position = position
  var local_max_height: Double = Insect.update_height(this)
  var height: Double = Insect.height(position._1, position._2)

}

object Insect {
  val r = scala.util.Random
  var global_max_position: Position = (0, 0)
  var global_max_value: Double = 0.0

  //creates a new insect based upon an older one with an updated velocity, height and position
  def tick(insect: Insect): Insect = {

    val new_insect: Insect = insect.copy(
      position = update_position(insect),
      velocity = update_velocity(insect))

    //update height based on new position
    new_insect.height = height(new_insect.position._1, new_insect.position._2)

    //update local and global maximum if necessary
    if (new_insect.height > Insect.global_max_value) {
      global_max_value = new_insect.height
    }
    else if (new_insect.height > insect.local_max_height) {
      new_insect.local_max_height = new_insect.height
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
    val x: Double = speed_check((0.5 * insect.velocity._1)+(2*r.nextFloat()*(insect.local_max_height -  insect.height))+(2*r.nextFloat()*(global_max_position._1 - insect.height)))
    val y: Double = speed_check((0.5 * insect.velocity._2)+(2*r.nextFloat()*(insect.local_max_height - insect.height))+(2*r.nextFloat()*(global_max_position._2 - insect.height)))

   (x,y)
  }


  def update_position(insect: Insect): Position = {
    //create new positions
    var new_pos = List(insect.velocity._1 + insect.position._1, insect.velocity._2 + insect.position._2)
    //take into account what happens at the edge of the display
    for (coordinant <- new_pos){
      if (coordinant < 0.0) 0.0
      else if (coordinant > 1.0) 1.0
      else coordinant
    }

    (new_pos(0), new_pos(1))

  }


  def height(x:Double, y:Double):Double = r.nextInt(100)



}

