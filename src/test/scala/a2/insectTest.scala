package a2

import org.scalatest.FunSuite

/**
  * Created by jeva on 31/03/17.
  */
class insectTest extends FunSuite {

  test("SayHelloWorksCorrectly"){
    val i = new insect
    assert(i.sayHello("Scala") == "Hello, Scala!")
  }

}
