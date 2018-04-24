name := "reactive-monitoring"

version := "0.1"

scalaVersion := "2.12.3"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.4.19",
  "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % "0.13",
  "com.typesafe.akka" %% "akka-stream-kafka" % "0.17",
  "com.typesafe.akka" %% "akka-stream-alpakka-amqp" % "0.13",

  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.6" % Test
)