package a2

import a2.A2.{Position, Velocity}

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
  *- constructor
  *- tick-tock: updates position, velocity and height
  *Private
  *- update_velocity
  *- update_position
  *- get_height
  */


case class Insect(pos: Position, vel: Velocity) {
  var local_max: Position = pos
  //initial local max is first position
  var position: Position = pos
  var velocity: Velocity = vel

}

object Insect {
  val r = scala.util.Random
  var global_max: Position = (0, 0)

  //I don't know if I should have this return a Velocity, or if it should be a whole new insect...
  def update_velocity(insect: Insect): Insect = {
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
    val x: Double = speed_check((0.5 * insect.velocity._1)+(2*r.nextFloat()*(insect.local_max._1 -  current_height))+(2*r.nextFloat()*(global_max._1 - current_height)))
    val y: Double = speed_check((0.5 * insect.velocity._2)+(2*r.nextFloat()*(insect.local_max._2 - current_height))+(2*r.nextFloat()*(global_max._2 - current_height)))


    insect.copy(vel = (x,y))
  }


  def update_position(position: Position, velocity: Velocity): Position = {

    (position._1 + velocity._1, position._2 + velocity._2)

  }

  def height(x:Double, y:Double):Double = 1.0



  def sayHello(greet: String): String = {s"Hello, $greet!"}

}
