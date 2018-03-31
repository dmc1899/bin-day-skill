import uk.co.keshroad.collection.schedule.event.Parser
import uk.co.keshroad.logging.Logging

import scala.io.Source

object Main extends App with Logging {

  log.info(s"Hello world")
  val parser: Parser = new Parser()

//  val fileContents = Source.fromFile("/Users/darragh/Documents/project/src/main/resources/example.html").getLines.mkString
//  val response = parser.parse(fileContents)

  val councilFileContents = Source
    .fromFile(
      "/Users/darragh/Documents/project/src/main/resources/sample_response_page.html")
    .getLines
    .mkString
  val councilResponse = parser.councilParse(councilFileContents)
  //println("parsed: " + councilResponse)
}
