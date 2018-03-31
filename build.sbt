import sbt.Keys.scalaVersion
import sbt.File

lazy val scalaSetting = scalaVersion
lazy val slf4jVersion = "1.7.25"

//==================================================================================================
// MODULES
//==================================================================================================
lazy val root = (project in file("."))
  .settings(binDaySettings)

lazy val binDaySettings = Seq(
  name := "bin-day-skill",
  organization := "uk.co.keshroad",
  version := "0.1",
  scalaVersion := "2.11.12",
  libraryDependencies ++= binDaySkillDependencies,
  scapegoatVersion := "1.3.3",
  //code coverage
  coverageEnabled in (Test, compile) := true,
  coverageEnabled in (Compile, compile) := false,
  coverageMinimum := 70,
  coverageFailOnMinimum := true,
  coverageOutputHTML := true,
  coverageOutputXML := false,
  mainClass in (Compile, packageBin) := Some("uk.co.keshroad.Main"),
  //exclude files from jar
  mappings in (Compile, packageBin) ~= {
    //exclude scripts from jar
    _.filter(!_._1.getName.endsWith(".sh"))
    //filter conf files
      .filter(!_._1.getName.endsWith(".conf"))
      //filter properties files
      .filter(!_._1.getName.endsWith(".properties"))
  },
  // Linter : http://www.wartremover.org/
  wartremoverErrors ++= Warts.unsafe,
  //code formatter: http://scalameta.org/scalafmt/
  scalafmtOnCompile := true,
  scalafmtTestOnCompile := true
)

//==================================================================================================
// DEPENDENCIES
//==================================================================================================

lazy val scalaScraper = "net.ruippeixotog" %% "scala-scraper" % "2.0.0"
lazy val log4j = "log4j" % "log4j" % "1.2.17"
lazy val mockito = "org.mockito" % "mockito-all" % "1.9.5"
lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1"
lazy val typesafeConfig = "com.typesafe" % "config" % "1.3.1"
lazy val slf4japi = "org.slf4j" % "slf4j-api" % slf4jVersion
lazy val slf4j12 = "org.slf4j" % "slf4j-log4j12" % slf4jVersion
lazy val slf4jsimple = "org.slf4j" % "slf4j-simple" % slf4jVersion

val binDaySkillDependencies = Seq(scalaScraper,
                                  log4j,
                                  mockito,
                                  scalaTest,
                                  typesafeConfig,
                                  slf4japi,
                                  slf4j12,
                                  slf4jsimple)

//==================================================================================================
// Settings
//==================================================================================================

lazy val akkaLoaderSettings = Seq(
  scalaSetting,
  scapegoatVersion := "1.3.3",
  //code coverage
  coverageEnabled in (Test, compile) := true,
  coverageEnabled in (Compile, compile) := false,
  coverageMinimum := 70,
  coverageFailOnMinimum := true,
  coverageOutputHTML := true,
  coverageOutputXML := false,
  mainClass in (Compile, packageBin) := Some("uk.co.keshroad.Main"),
  //exclude files from jar
  mappings in (Compile, packageBin) ~= {
    //exclude scripts from jar
    _.filter(!_._1.getName.endsWith(".sh"))
    //filter conf files
      .filter(!_._1.getName.endsWith(".conf"))
      //filter properties files
      .filter(!_._1.getName.endsWith(".properties"))
  },
  // Linter : http://www.wartremover.org/
  wartremoverErrors ++= Warts.unsafe,
  //code formatter: http://scalameta.org/scalafmt/
  scalafmtOnCompile := true,
  scalafmtTestOnCompile := true
)
