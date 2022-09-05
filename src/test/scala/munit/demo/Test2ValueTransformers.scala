package munit.demo

import cats.effect.IO
import munit.FunSuite

import scala.concurrent.duration._

/** Value transformers.
  */
class Test2ValueTransformers extends FunSuite {

  override def munitTimeout: Duration = 1.second

  // Use CatsEffectSuite instead from `munit-cats-effect-3`
  override def munitValueTransforms: List[ValueTransform] = {
    import cats.effect.unsafe.implicits.global
    super.munitValueTransforms ++ List(
      new ValueTransform(
        "IO",
        { case io: IO[_] => io.unsafeToFuture() }
      )
    )
  }

  test("IO tests work! #1") {
    IO(assert(false == true))
  }

  test("IO tests work! #2") {
    IO(assert(true == true))
  }

  test("IO tests work, but timeout!") {
    IO.sleep(1200.millis) *>
      IO(assert(true == true))
  }

}
