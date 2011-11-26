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

ivyXML :=
  <dependencies>
      <exclude name="log4j"/>
  </dependencies>

// Customize any further dependencies as desired
libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-log4j12" % "1.6.4",
  "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
  "org.scalatra" %% "scalatra" % "2.0.1",
  "org.scalatra" %% "scalatra-scalate" % "2.0.1",
  "org.scalatra" %% "scalatra-scalatest" % "2.0.1",
  "org.scala-tools.subcut" %% "subcut" % "1.0-SNAPSHOT",
  "org.eclipse.jetty" % "jetty-webapp" % "7.4.5.v20110725" % "container",
  "javax.servlet" % "servlet-api" % "2.5" % "provided",
  "junit" % "junit" % "4.8.2" % "test",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test")

ivyXML :=
  <dependencies>
      <exclude name="slf4j-log4j12" ext="1.5.8"/>
  </dependencies>