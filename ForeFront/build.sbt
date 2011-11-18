name := "ForeFront"

scalaVersion := "2.9.1"

version := "1.0.0"

seq(webSettings: _*)

// If using JRebel
// jettyScanDirs := Nil


resolvers += DefaultMavenRepository

resolvers += "Scala Tools Release Repository" at "http://scala-tools.org/repo-releases/"

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

resolvers += "repo.novus rels" at "http://repo.novus.com/snapshots/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

// Customize any further dependencies as desired
libraryDependencies ++= Seq(
  "net.liftweb" %% "lift-webkit" % "2.4-M5" % "compile",
  "net.liftweb" %% "lift-mapper" % "2.4-M5" % "compile",
  "net.liftweb" %% "lift-scalate" % "2.4-M5" % "compile",
  "org.fusesource.scalate" % "scalate-core" % "1.5.2",
  "org.fusesource.scalate" % "scalate-page" % "1.5.2",
  "org.fusesource.scalate" % "scalate-wikitext" % "1.5.2",
  "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
  "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT",
  "com.novus" %% "salat" % "0.0.8-SNAPSHOT",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "container",
  "junit" % "junit" % "4.5" % "test",
  "ch.qos.logback" % "logback-classic" % "0.9.26",
  "org.scala-tools.testing" % "test-interface" % "0.5" % "test",
  "org.mockito" % "mockito-all" % "1.9.0-rc1" % "test",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test")