package a2

import a2.A2.{Position, Swarm, Velocity}

import scala.collection.mutable


/**
  * Created by jeva on 1/04/17.
  *
  * 	Attributes:
  *- global_max = highest position seen by any particle
  *- top_5 = highest five particles
  *- particles = list of particles in environment
  **
  *Methods:
  **
  *Public
  *- run: creates terrain, creates swarm, runs required iterations, reports results,               plots particles;
  **
  *Private
  *- create_particle
  *- update_particles: updates the position of each particle using particle's tick-tock            method which return a new particle that is updated.
  *- report_results: prints to console highest position and top five
  *- plot_particles: drives Java FX
  */
case class Simulator(swarm_size: Int, terrain: (Int, Int), interations: Int) {

  //var top_five_positions: mutable.ArrayBuffer[(Double, Double)];
  val swarm: Swarm = Simulator.create_swarm(swarm_size, terrain)

}

object Simulator{

  //========== INITIALISE SWARM ===========

  //create the initial swarm
	def create_swarm(swarm_size: Int, terrain: (Int, Int)): Swarm = for (x <- 1 to swarm_size) yield create_insect(terrain)

  //an insect needs a position and a velocity
	def create_insect(terrain: (Int, Int)): Insect = Insect(create_random_position((terrain._1, terrain._2)), create_random_velocity())

  //creates a random position within the bounds of the x,y limits provided
  def create_random_position(terrain: (Int, Int)): Position = {
    val r = scala.util.Random
    //create position at integer distribution on terrain.
    //nextInt will return between 0 and limit given
    (r.nextInt(terrain._1), r.nextInt(terrain._1))
  }

  def create_random_velocity(): Velocity = (scala.util.Random.nextFloat(), scala.util.Random.nextFloat() )


  //  =========== RUN SIMULATION ========


  def run(): Unit = ???

  def update_swarm(): Unit = ???

	def report_results(): Unit = ???

	def plot_particles() = ???


}
