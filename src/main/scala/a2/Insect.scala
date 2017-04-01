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
  *- tick-tock: updates position, velocity and height
  *Private
  *- update_velocity
  *- update_position
  *- get_height
  */


case class Insect(position: Position, velocity: Velocity) {
  var local_max: Position = position
  var height: Double = Insect.height(position._1, position._2)

}

object Insect {
  val r = scala.util.Random
  var global_max_position: Position = (0, 0)
  var global_max_value: Double = 0.0

  def tick(insect: Insect): Insect = {

    val new_insect: Insect = insect.copy(
      position = update_position(insect),
      velocity = update_velocity(insect))

    new_insect.height = height(new_insect.position._1, new_insect.position._2)

  /*@todo Need to figure out how to update the global/max list
  if (new_insect.local_max != insect.local_max)

  */

    new_insect
  }

  def update_height(insect: Insect): Double = ???

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
    val current_height: Double = height(insect.position._1, insect.position._2)
    val x: Double = speed_check((0.5 * insect.velocity._1)+(2*r.nextFloat()*(insect.local_max._1 -  current_height))+(2*r.nextFloat()*(global_max_position._1 - current_height)))
    val y: Double = speed_check((0.5 * insect.velocity._2)+(2*r.nextFloat()*(insect.local_max._2 - current_height))+(2*r.nextFloat()*(global_max_position._2 - current_height)))

   (x,y)
  }


  def update_position(insect: Insect): Position = {

    (insect.velocity._1 + insect.position._1, insect.velocity._2 + insect.position._2)

  }


  def height(x:Double, y:Double):Double = 1.0

  }
