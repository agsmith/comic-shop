organization := "io.github.agsmith"
name := "comic-shop"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.3"

resolvers += Resolver.sonatypeRepo("snapshots")

val DoobieVersion = "0.5.0-M6"

libraryDependencies ++= Seq(
 "joda-time" % "joda-time" % "2.9.9",
 "org.tpolecat" %% "doobie-core" % DoobieVersion,
 "org.tpolecat" %% "doobie-h2" % DoobieVersion,
 "org.tpolecat" %% "doobie-scalatest" % DoobieVersion
)
