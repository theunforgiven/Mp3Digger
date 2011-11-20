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
  "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
  "org.scalatra" %% "scalatra" % "2.0.1",
  "org.scalatra" %% "scalatra-scalate" % "2.0.1",
  "org.slf4j" % "log4j-over-slf4j" % "1.5.8",
  "org.slf4j" % "slf4j-api" % "1.5.8",
  "org.eclipse.jetty" % "jetty-webapp" % "7.4.5.v20110725" % "container",
  "javax.servlet" % "servlet-api" % "2.5" % "provided",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test")