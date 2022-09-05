package munit.demo

import cats.effect.{IO, Resource, ResourceIO}
import munit.CatsEffectSuite

import java.nio.file.Files

/** Cats
  */
class Test9Cats extends CatsEffectSuite {

  test("basic") {
    for {
      _ <- IO("hello there").assertEquals("hello there")
      _ <- assertIO(IO(12), 12)
      _ <- assertIO_(IO.unit) // assert that IO is not in a failure state
      _ <- IO.unit.assert // same as above
      _ <- assertIOBoolean(IO(true))
      _ <- interceptIO[IllegalArgumentException](
        IO.raiseError(new IllegalArgumentException())
      )
    } yield ()
  }

  val suiteLocalFileFixture = ResourceSuiteLocalFixture(
    "file",
    Resource.make(IO(Files.createTempFile("Test9", "SuiteLocal")))(file =>
      IO(Files.deleteIfExists(file))
    )
  )

  override def munitFixtures =
    super.munitFixtures ++ List(suiteLocalFileFixture)

  test("suite local fixture") {
    for {
      f <- IO(suiteLocalFileFixture())
      _ <- IO.println(f)
      _ <- IO(assert(Files.isRegularFile(f)))
    } yield ()
  }

  // oops, duplicate name
  test("suite local fixture") {
    for {
      f <- IO(suiteLocalFileFixture())
      _ <- IO.println(f)
      _ <- IO(assert(Files.isRegularFile(f)))
    } yield ()
  }

  val testLocalFileFixture = ResourceFixture(
    Resource.make(IO(Files.createTempFile("Test9", "TestLocal")))(file =>
      IO(Files.deleteIfExists(file))
    )
  )

  testLocalFileFixture.test("test local fixture 1") { file =>
    println(file)
    assertIOBoolean(IO(Files.isRegularFile(file)))
  }

  testLocalFileFixture.test("test local fixture 2") { file =>
    println(file)
    assertIOBoolean(IO(Files.isRegularFile(file)))
  }
}
