package munit.demo

import munit.FunSuite

/** Assertions
  */
class Test6Assertions extends FunSuite {

  test("Simple assert with clue") {
    assert(true == false, "The thing did not happen correctly.")
  }

  test("Simple assert with more clues") {
    val values = Set(1, 2, 3, 4)

    assert(clue(values).contains(5))
  }

  test("assertEquals case class") {
    case class Person(name: String, age: Int, email: String)

    val a = Person("Joe", 29, "joe@example.com")
    val b = Person("Jill", 29, "jill@example.com")
    assertEquals(a, b)
  }

  test("assertEquals List") {
    val a = List(1, 2, 3)
    val b = List(1, 4, 3)
    assertEquals(a, b)
  }

  test("assertEquals Map") {
    val a = Map(1 -> "a", 2 -> "b")
    val b = Map(1 -> "a", 2 -> "b", 3 -> "c")
    assertEquals(a, b)
  }

  test("assertEquals... is diffing toString based?") {
    case class Secret(value: String) {
      override def toString: String = "Secret(***)"
    }
    val a = Secret("a")
    val b = Secret("b")
    assertEquals(a, b)
  }

  test("assert exception") {
    val ex = interceptMessage[IllegalStateException]("something's not quite right") {
      throw new IllegalStateException("something's not quite right")
    }

    assert(ex ne null)
  }

}
