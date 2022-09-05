ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val MUnitFramework = new TestFramework("munit.Framework")
val Slow = config("Slow").extend(Test)

lazy val root = (project in file("."))
  .configs(Slow)
  .settings(
    name := "munit-demo",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
    libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.7" % Test,
    testFrameworks += MUnitFramework,
    inConfig(Slow)(Defaults.testTasks),
    Test / testOptions += Tests.Argument(MUnitFramework, "--exclude-tags=IntegrationTest"),
    Slow / testOptions -= Tests.Argument(MUnitFramework, "--exclude-tags=IntegrationTest"),
    Slow / testOptions += Tests.Argument(MUnitFramework, "--include-tags=IntegrationTest"),
  )
