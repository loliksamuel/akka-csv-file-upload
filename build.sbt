name := """akka-http-csv-upload"""
version := "1.0"
scalaVersion := "2.12.1"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.0.4",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.17",
  "com.hynnet" % "logback-classic" % "1.1.3",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "io.rest-assured" % "scala-support" % "3.0.2" % Test
)

