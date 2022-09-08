package munit.demo

import munit.{FunSuite, Tag}

/** Filtering
  */
class Test8Filters extends FunSuite {

  test("test1".ignore) {
    println("Running test1")
    fail("not implemented")
  }

//  test("test1".only) {
//    assert(true == true)
//  }

  // pass `--exclude-tags=IntegrationTest" to skip
  val IntegrationTest = new Tag("IntegrationTest")
  test("the slow and painful integration test".tag(IntegrationTest)) {
    println("IT Test!")
    assert(true == true)
  }

  test("just here to make exclude look good") {}
}
