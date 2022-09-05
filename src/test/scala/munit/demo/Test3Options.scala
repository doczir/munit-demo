package munit.demo

import munit.FunSuite

/** Test options.
  */
class Test3Options extends FunSuite {

  // Where did IntelliJ IDEA support go? ¯\_(ツ)_/¯
  test("Always fails".fail) {
    assert(false == true)
  }

  test("Sometimes fails".flaky) {
    assert(Math.random() > 0.5)
  }

}
