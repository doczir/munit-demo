package munit.demo

import munit.FunSuite

/** Custom base class.
  */
abstract class DBFunSuite extends FunSuite {
  override def munitTestTransforms: List[TestTransform] =
    super.munitTestTransforms ++ List(
      new TestTransform(
        "DBFunSuite",
        { test =>
          import scala.concurrent.ExecutionContext.Implicits.global
          test.withBody(() => {
            println("Setup DB")
            var result: TestValue = null

            try {
              result = test.body()
            } finally {
              if (result != null)
                result.onComplete(_ => println("Cleanup DB"))
            }

            result
          })
        }
      )
    )
}

class Test5CustomBaseClasses extends DBFunSuite {

  test("Integration runs") {
    println("Testing")
    assert(true == false)
  }

}
