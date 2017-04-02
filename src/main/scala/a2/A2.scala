package a2

/**
  * Created by jeva on 31/03/17.
  * Drives simulation
  */
object A2 extends App{

  type Velocity = (Double, Double)
  type Position = (Double, Double)
  type Swarm = Seq[Insect]

  var global_max: Position = (0,0)

  val s = Simulator(5, (10,10), 50)
  for (x <- s.swarm){println(x)}

}
