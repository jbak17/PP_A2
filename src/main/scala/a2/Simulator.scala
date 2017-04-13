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

  var top_positions_seen: Swarm = mutable.Seq()
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

  def create_random_velocity(): Velocity = (scala.util.Random.nextFloat()*scala.util.Random.nextInt(), scala.util.Random.nextFloat() )


  //  =========== RUN SIMULATION ========

  //updates the swarm, reports results
  def run(simulator: Simulator): Unit = {
    //cycles simulation
    for (iteration <- 1 to simulator.iterations){
      simulator.swarm = update_swarm(simulator.swarm)
      update_highest_record(simulator.swarm)
      get_telemetry(simulator.swarm)
      report_results(simulator.swarm)
    }
  }

  //updates each insect in swarm with new positions, velocities, heights
  def update_swarm(swarm: Swarm): Swarm = swarm.map(insect => Insect.tick(insect))

  //updates the highest position seen so far
  def update_highest_record(swarm: Swarm): Unit = {

    swarm.map(x => update_max(x))

    //update local and global maximum if necessary
    def update_max(insect: Insect): Unit = {
        if (insect.local_max_height > Insect.global_max_value) {
          Insect.global_max_value = insect.local_max_height
          Insect.global_max_position = insect.local_max_position
        };
    };
  }

  //logs results to console
  //need to log best results seen so far and height
	def report_results(swarm: Swarm): Unit = {
    //print maximum position and height

    println(s"The highest position seen so far is:" + Insect.global_max_value + "\n" + s"The location is: " + Insect.global_max_position)

    println("=========================")

    //print top five
    println("Next five highest locations: ")
    Simulator.top_positions_seen.map(x => println("Height: " + x.local_max_height + ", Position: " + x.local_max_position))

    println("=========================")
    println("=========================")
  }

  //plots swarm graphically
	def plot_particles() = ???

  //creates a list of the five highest positions seen so far
  def get_telemetry(swarm: Swarm): Unit = {
    //get five highest from this iteration
    val new_five: Swarm = swarm.sortBy(_.local_max_height).reverse.dropRight(swarm.size-6).drop(1)

    Simulator.top_positions_seen = (new_five ++ top_positions_seen).sortBy(_.local_max_height).reverse.take(5)

  }


  // THE END //
}
