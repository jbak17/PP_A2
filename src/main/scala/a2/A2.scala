package a2

/**
  * Created by jeva on 31/03/17.
  * Drives simulation
  */
object A2 extends App{

  type Velocity = (Double, Double)
  type Position = (Double, Double)
  type Swarm = Seq[Insect]

  val s = Simulator(15, 3)
  Simulator.run(s)

}
