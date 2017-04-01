package a2

import org.scalatest.FunSuite

/**
  * Created by jeva on 31/03/17.
  */
class insectTest extends FunSuite {

  test("Can create insect object"){
    val i = Insect((0,0),(0,0))
    assert(i.getClass() == classOf[Insect])
  }

  test("velocity changes on update"){
    val i = Insect.update_velocity(Insect((0,0),(1,1)))
    assert(i.velocity != (1,1))
  }

  test("velocity can't exceed abs|1|"){
    val i = Insect.update_velocity(Insect((0,0),(2,2)))
    def create(number_of_insects: Int): Array[Insect] = {
      ///val swarm = Array[Insect](number_of_insects)
      ???
    }

    assert(i.velocity._1 >= -1 && i.velocity._1 <= 1)
    assert(i.velocity._2 >= -1 && i.velocity._2 <= 1)
  }

  test("position changes on update"){
    val i = Insect((1,1),(1,1))
    i.position = Insect.update_position(i.position, i.velocity)
    assert(i.position != (1,1))

  }

}
