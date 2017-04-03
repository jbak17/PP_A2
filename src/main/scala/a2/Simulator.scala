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
  *- get_telemetry (highest and next five):
  */
case class Simulator(swarm_size: Int, iterations: Int) {

  //var top_five_positions: mutable.ArrayBuffer[(Double, Double)];
  var swarm: Swarm = Simulator.create_swarm(swarm_size)

}

object Simulator{

  //========== INITIALISE SWARM ===========

  //create the initial swarm
	def create_swarm(swarm_size: Int): Swarm = for (x <- 1 to swarm_size) yield create_insect()

  //an insect needs a position and a velocity
	def create_insect(): Insect = Insect(create_random_position(), create_random_velocity())

  //creates a random position (x,y) within 0.0 and 1.0
  def create_random_position(): Position = {
    val r = scala.util.Random
    (r.nextFloat(), r.nextFloat())
  }

  def create_random_velocity(): Velocity = (scala.util.Random.nextFloat(), scala.util.Random.nextFloat() )


  //  =========== RUN SIMULATION ========

  //updates the swarm, reports results
  def run(simulator: Simulator): Unit = {
    //cycles simulation
    for (iteration <- 1 to simulator.iterations){
      simulator.swarm = update_swarm(simulator.swarm)
      report_results(simulator.swarm)
    }

  }

  //updates each insect in swarm with new positions, velocities, heights
  def update_swarm(swarm: Swarm): Swarm = swarm.map(insect => Insect.tick(insect))


  //logs results to console
  //need to log best results seen so far and height
	def report_results(swarm: Swarm): Unit = {
    //print maximum position and height
    println(s"The highest position seen so far is:" + Insect.global_max_value + "\n" + s"The height is: " + Insect.global_max_position)

    //print top five
    println("Next five highest locations: \n \n")
    Simulator.get_telemetry(swarm).map(x => println("Height: " + x.height + ", Position: " + x.position))
  }

  //plots swarm graphically
	def plot_particles() = ???

  //creates a list of the five highest positions seen so far
  def get_telemetry(swarm: Swarm): Swarm = swarm.sortBy(_.local_max_height).reverse.dropRight(swarm.size-6).drop(1)


  // THE END //
}
