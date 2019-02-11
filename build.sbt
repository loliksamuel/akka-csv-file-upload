organization  := "com.setoo"

name          := "pricing_server"

version       := "1.0.10-SNAPSHOT"

scalaVersion  := "2.11.8"

javaOptions += "-Xmx8G"

javaOptions += "-Xms12G"

javaOptions += "-Xss1M"

javaOptions += "-XX:MaxPermSize=724m"

javaOptions += "-XX:MaxMetaspaceSize=1024M"

javaOptions += "-XX:ReservedCodeCacheSize=128m"

javaOptions += "-XX:+UseConcMarkSweepGC"

javaOptions += "-XX:+CMSClassUnloadingEnabled"


libraryDependencies ++= Seq(
 // val akkaVersion      = "2.4.17"



   "com.typesafe.akka" %% "akka-http"         % "10.0.4"  //"10.0.9"  "10.0.4
  ,"com.typesafe.akka" %% "akka-slf4j"        % "2.4.5"  //"2.4.5"  "2.4.17
  ,"com.typesafe.akka" %% "akka-actor"        % "2.4.5"  //"2.4.5"  "2.4.17
  ,"com.typesafe.akka" %% "akka-stream"       % "2.4.5"  //"2.4.5"  "2.4.17
  ,"com.typesafe.akka" %% "akka-http-testkit" % "10.0.9" % "test"

  ,"org.apache.spark"  %% "spark-core"   % "2.2.0" % "provided"   // spark core
  ,"org.apache.spark"  %% "spark-sql"    % "2.2.0" % "provided"   // spark sql interface
  ,"org.apache.spark"  %% "spark-mllib"  % "2.2.0" % "provided"   // spark machine learning new library

  ,"com.typesafe"       % "config"        % "1.3.1"   // config library for akka
  ,"org.json4s"        %% "json4s-native" % "3.5.2" % "provided"   // json parser
  ,"ch.qos.logback"    % "logback-classic"    % "1.2.3"
  ,"com.hynnet"        % "logback-classic" % "1.1.3"
  ,"org.scalatest"     %% "scalatest" % "3.0.1" % Test
  ,"io.rest-assured"   % "scala-support" % "3.0.2" % Test

)

