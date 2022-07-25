ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "Transpower",
    idePackagePrefix := Some("org.bestcolis")
  )

libraryDependencies += "org.scalatest" %% "scalatest-wordspec" % "3.2.12" % "test"

val http4sVersion = "0.23.12"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,

  "io.circe" %% "circe-generic" % "0.14.2",
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.14.2"
)

scalacOptions ++= Seq("-Ypartial-unification")

