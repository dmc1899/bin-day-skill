
import org.scalatest.{FlatSpec, GivenWhenThen, Matchers}
import com.typesafe.config.{Config, ConfigFactory}
import logging.Logging
import org.mockito.{Mockito, Matchers => MockMatchers}
import org.mockito.Mockito.mock

import scala.io.Source

class ParserSpec extends FlatSpec with GivenWhenThen with Matchers with Logging{

  behavior of "A Parser"

  it should "parse correctly" in {

    log.info(s"Starting parser test")

    val config: Config =
      ConfigFactory.load("conf/application.conf")

    val resourcesPath = getClass.getResource("webpages/sample_response_page.html")
    val fileContents = Source.fromFile(resourcesPath.toURI).getLines().mkString

    val parser: Parser = new Parser()
    val councilResponse = parser.councilParse(fileContents)

    log.info(s"Finishing parser test")
  }

}
