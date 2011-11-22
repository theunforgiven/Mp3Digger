name := "Core"
 
scalaVersion := "2.9.1"

version := "1.0.0" 

// seq(webSettings: _*)

// If using JRebel
// jettyScanDirs := Nil


resolvers += DefaultMavenRepository

resolvers += "Scala Tools Release Repository" at "http://scala-tools.org/repo-releases/"

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

resolvers += "repo.novus rels" at "http://repo.novus.com/snapshots/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

// Customize any further dependencies as desired
libraryDependencies ++= Seq(
  "commons-net" % "commons-net" % "3.0.1" intransitive(),
  "se.scalablesolutions.akka" % "akka-actor" % "1.2" intransitive(),
  "se.scalablesolutions.akka" % "akka" % "1.2" intransitive(),
  "com.mongodb.casbah" %% "casbah" % "2.1.5-1" intransitive(),
  "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT" intransitive(),
  "com.novus" %% "salat" % "0.0.8-SNAPSHOT" intransitive(),
  "org.scalaz" %% "scalaz" % "6.0.3",
  "ch.qos.logback" % "logback-classic" % "1.0.0",
  "org.slf4j" % "slf4j-api" % "1.6.4",
  "org.slf4j" % "log4j-over-slf4j" % "1.6.4",
  "junit" % "junit" % "4.8.1" intransitive(),
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.2.0",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.2.0",
  "org.scala-tools.testing" % "test-interface" % "0.5" % "test",
  "org.mockito" % "mockito-all" % "1.9.0-rc1" % "test",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test")