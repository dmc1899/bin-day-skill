package uk.co.keshroad.collection.schedule.event

import java.nio.file.Paths

import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import uk.co.keshroad.logging.Logging

import scala.io.Source

class ScheduleEventExtractorSpec extends FlatSpec with GivenWhenThen with Matchers with Logging{

  behavior of "A uk.co.keshroad.collection.schedule.event.Parser"

  it should "parse correctly" in {

    log.info(s"Starting parser test")

    val config: Config =
      ConfigFactory.load("conf/application.conf")

    val testResponseHtmlFile = Paths.get(getClass().getClassLoader().getResource("webpages/sample_response_page.html").toURI)

    val fileContents = Source.fromFile(testResponseHtmlFile.toString).getLines().mkString

    val parser: Parser = new Parser()
    val councilResponse = parser.councilParse(fileContents)
    println(councilResponse.toString)

    log.info(s"Finishing parser test")
  }

}
