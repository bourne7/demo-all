ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.6"

val AkkaVersion = "2.6.3"

libraryDependencies ++= Seq(
  // Scala 的依赖需要做好版本兼容
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,

  // Java 的依赖需要用精确依赖
  "org.projectlombok" % "lombok" % "1.18.12" % "provided",
  "ch.qos.logback" % "logback-classic" % "1.2.11"
)