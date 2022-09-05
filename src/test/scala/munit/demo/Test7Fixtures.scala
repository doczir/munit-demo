package munit.demo

import munit.FunSuite

import java.nio.file.{Files, Path}
import scala.util.Random

/** Fixtures
  */
class Test7FunctionalFixtures extends FunSuite {

  val file: FunFixture[Path] = FunFixture[Path](
    setup = test => Files.createTempFile("tmp", test.name),
    teardown = file => Files.deleteIfExists(file)
  )

  file.test("basic") { file =>
    assert(Files.isRegularFile(file))
  }

  // up to 3 fixtures can be combined
  val twoFiles = FunFixture.map2(file, file)

  twoFiles.test("double it") { case (file1, file2) =>
    assertNotEquals(file1, file2)
  }

}

class Test7TestLocalFixtures extends FunSuite {

  val file = new Fixture[Path]("file") {
    private var file: Path = null
    override def apply(): Path = file

    override def beforeEach(context: BeforeEach): Unit = file =
      Files.createTempFile("tmp", context.test.name)
    override def afterEach(context: AfterEach): Unit =
      Files.deleteIfExists(file)
  }

  override def munitFixtures: Seq[Fixture[_]] = List(file)

  test("basic") {
    assert(Files.isRegularFile(file()))
  }
}

class Test7SuitLocalFixtures extends FunSuite {
  type Connection = String

  val db: Fixture[Connection] = new Fixture[Connection]("database") {
    private var connection: Connection = null
    override def apply(): Connection = connection

    override def beforeAll(): Unit =
      connection = Random.alphanumeric.take(5).mkString
    override def afterAll(): Unit =
      connection = null // close
  }
  override def munitFixtures: Seq[Fixture[_]] = List(db)

  test("t1") {
    println(db())
  }

  test("t2") {
    println(db())
  }

}
