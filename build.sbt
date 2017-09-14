organization := "io.github.agsmith"
name := "comic-shop"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.3"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
 "joda-time" % "joda-time" % "2.9.9"
)
