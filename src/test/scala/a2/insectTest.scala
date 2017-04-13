package a2

import a2.A2.{Swarm, Velocity}
import org.scalatest._

/**
  * Created by jeva on 31/03/17.
  */
class insectTest extends FlatSpec {

  //Helper items to use in tests
  val i1: Insect = Insect((0.5,0.5),(0.5,-0.5))
  val mySwarm: Swarm = Simulator.create_swarm(25)

  "An Insect " should " be of class insect" in {
    assert(i1.getClass() == classOf[Insect])
  }

  "It" should " change velocity on update" in {
    val i: Velocity = Insect.update_velocity(i1)
    assert(i != (1,1))
  }

  "An Insect's velocity" should "not exceed abs|1|" in {
    val i: Velocity = Insect.update_velocity(i1)
    def create(number_of_insects: Int): Array[Insect] = {
      ///val swarm = Array[Insect](number_of_insects)
      ???
    }

    assert(i._1 >= -1 && i._1 <= 1)
    assert(i._2 >= -1 && i._2 <= 1)
  }

  "It's position" should "change on update" in {
    val i2: Insect = i1.copy(position = Insect.update_position(i1))
    assert(i2.position != (1,1))
  }

  "A tick" should "create a new item that is different from the previous" in {
    val i2: Insect = Insect.tick(i1)
    assert(i1.position != i2.position)
    assert(i1.velocity != i2.velocity)
  }

  "A swarm" should "be created with 25 number of elements" in {
    assertResult(25)(mySwarm.size)
  }

  "A swarm" should ",when updated, have insects in different places" in {
    val newSwarm: Swarm = Simulator.update_swarm(mySwarm)
    val testBug: Insect = newSwarm(0)
    val oldBug: Insect = mySwarm(0)
    assert(testBug.position != oldBug.position)
    assert(testBug.velocity != testBug.position)
  }

  "A double" should "be returned from the height function" in {
    assert(Insect.height(.9, 1.0).getClass() == classOf[Double])
  }

  //Test that the global positions are being updated
  "A height" should "be added to the highest position seen after one iteration" in {
    val newSwarm: Swarm = Simulator.update_swarm(mySwarm)
    assert(Insect.global_max_value != 0.0)
  }

  "A insect" should "not change direction if it's velocity is within maximal bounds" in {

    val i1: Insect = Insect((0.5,0.5),(0.5,-0.5))
    assert(Insect.invert_direction(i1).velocity == i1.velocity)

  }

  "A insect" should "change direction if it has reached the border" in {

    val i1: Insect = Insect((2,2),(0.5,-0.5))
    assert(Insect.invert_direction(i1).velocity != i1.velocity)

  }


  /*
  This test was deprecated when get_telemetry became a unit return
  "A swarm" should "be reduced to 5 after processing for telemetry" in {

    assertResult(5)(Simulator.get_telemetry(mySwarm).size)
  }
  */
}
