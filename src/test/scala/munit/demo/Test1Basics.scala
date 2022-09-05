package munit.demo

import cats.effect.IO
import munit.FunSuite

import scala.concurrent.Future

/** Simple tests.
  * <ul>
  *   <li>Runnable from IntelliJ IDEA. Based on JUnit -> requires JUnit plugin.</li>
  *   <li>Tests inside test suite executed sequentially.</li>
  *   <li>SBT can parallelize execution of test suites.</li>
  * </ul>
  */
class Test1Basics extends FunSuite {

  test("this should pass") {
    assert(true == true)
  }

  test("this should fail") {
    assert(true != true)
  }

  test("fail inside Future") {
    import scala.concurrent.ExecutionContext.Implicits.global
    Future {
      assert(true != true)
    }
  }

  test("fail inside IO") {
    IO {
      assert(true != true)
    }
  }

}
