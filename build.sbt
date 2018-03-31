name := "bin-day-skill"

version := "0.1"

organization := "uk.co.keshroad"

lazy val scalaSetting = scalaVersion := "2.12.4"
lazy val slf4jVersion = "1.7.25"

//==================================================================================================
// MODULES
//==================================================================================================


//==================================================================================================
// DEPENDENCIES
//==================================================================================================
lazy val binDaySkillSettings = Seq(
  scalaSetting,
  scapegoatVersion := "1.3.3",
  //code coverage
  coverageEnabled in (Test, compile) := true,
  coverageEnabled in (Compile, compile) := false,
  coverageMinimum := 70,
  coverageFailOnMinimum := true,
  coverageOutputHTML := true,
  coverageOutputXML := false,
  mainClass in (Compile, packageBin) := Some(
    "uk.co.keshroad.Main"),
  //exclude files from jar
  mappings in (Compile, packageBin) ~= {
    //exclude scripts from jar
    _.filter(!_._1.getName.endsWith(".sh"))
      //filter conf files
      .filter(!_._1.getName.endsWith(".conf"))
      //filter properties files
      .filter(!_._1.getName.endsWith(".properties"))
      //filter avro schema files
      .filter(!_._1.getName.endsWith(".avsc"))
  },
  // Linter : http://www.wartremover.org/
  wartremoverErrors ++= Warts.unsafe,
  //code formatter: http://scalameta.org/scalafmt/
  scalafmtOnCompile := true,
  scalafmtTestOnCompile := true
)

libraryDependencies ++= Seq( "net.ruippeixotog" %% "scala-scraper" % "2.0.0",
"log4j" % "log4j" % "1.2.17",
"org.mockito" % "mockito-all" % "1.9.5",
"org.scalatest" %% "scalatest" % "3.0.1",
"com.typesafe" % "config" % "1.3.1",
"org.slf4j" % "slf4j-api" % slf4jVersion,
"org.slf4j" % "slf4j-log4j12" % slf4jVersion,
"org.slf4j" % "slf4j-simple" % slf4jVersion,
"com.typesafe" % "config" % "1.3.1")