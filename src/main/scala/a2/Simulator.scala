package a2

import a2.A2.Swarm

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
  var swarm: Swarm = Simulator.create_swarm(swarm_size)

}

object Simulator{

  //create the initial swarm
	def create_swarm(swarm_size: Int): Swarm = {
    for (x <- 1 to swarm_size)

  }

  def run(): Unit = ???

	def create_insect(): Insect = ???

	def update_swarm(): Unit = ???

	def report_results(): Unit = ???

	def plot_particles() = ???


}
