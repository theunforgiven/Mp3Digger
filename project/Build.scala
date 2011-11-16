import sbt._

object Mp3DiggerBuild extends Build {
  lazy val core = Project("Core", file("./Core"))
}