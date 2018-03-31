name := "bin-day-skill"

version := "0.1"

scalaVersion := "2.12.4"
lazy val slf4jVersion = "1.7.25"

libraryDependencies ++= Seq( "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
"log4j" % "log4j" % "1.2.17",
"org.mockito" % "mockito-all" % "1.9.5",
"org.scalatest" %% "scalatest" % "3.0.1",
"com.typesafe" % "config" % "1.3.1",
"org.slf4j" % "slf4j-api" % slf4jVersion,
"org.slf4j" % "slf4j-log4j12" % slf4jVersion,
"org.slf4j" % "slf4j-simple" % slf4jVersion,
"com.typesafe" % "config" % "1.3.1")