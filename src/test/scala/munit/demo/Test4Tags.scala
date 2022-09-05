package munit.demo

import munit.{FunSuite, Tag}

import scala.concurrent.Future

/** Custom tags.
  */
class Test4Tags extends FunSuite {
  final case class Rerun(count: Int) extends Tag("Rerun")

  override def munitTestTransforms: List[TestTransform] =
    super.munitTestTransforms ++ List(
      new TestTransform(
        "Rerun",
        { test =>
          val rerunCount =
            test.tags.collectFirst { case Rerun(count) => count }.getOrElse(1)

          if (rerunCount == 1) test
          else {
            import scala.concurrent.ExecutionContext.Implicits.global
            test.withBody(() => {
              Future.sequence((1 to rerunCount).map(_ => test.body()))
            })
          }
        }
      )
    )

  test("Multiple runs".tag(Rerun(3))) {
    println("Hello")
  }

}
