package a2

import a2.A2.Velocity
import org.scalatest._

/**
  * Created by jeva on 31/03/17.
  */
class insectTest extends FlatSpec {
  val i1: Insect = Insect((1,1),(1,1))


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

}
