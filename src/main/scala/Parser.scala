import logging.Logging

import scala.collection.mutable.Map
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{text => stext, _}
import net.ruippeixotog.scalascraper.scraper.HtmlValidator._

import scala.collection.mutable.ArrayBuffer
class Parser extends Logging{

  log.info(s"Hello world")

  val nextCollectionDivId : String = "#nextCollectionSection"
  val nextCollectionDatesElementType : String = "h5"

  def parse(html: String): String = {

    val browser = JsoupBrowser()
    val doc = browser.parseString(html)

    val headertext = doc >> text("#header")
    val footertext = doc >> text("#footer")
    val footerElement = doc >?> element("#footer")
    val items = doc >> elementList("#menu span")
    val items3 = doc >> elementList("#content section h3")

    val item4 = doc >> extractor("#mytable td", texts)
    val outcome = doc >/~ validator(text("title"))(_ == "Test page")

    //val valResult = doc >/~ validator(elementList("section"))(_.size >= 1)

    val resu = doc >/~ validator(elementList("section"))(_.size >= 1) match {
      case Left(s) => println("Result was failure: " + s)
      case Right(i) => println("Result was success: " + i)

    }


val myextractor = extractor("h4", texts)

val out = doc >/~ validator(text("h1"), "Internal Server Error")(_.contains("500"))

    headertext
    //footertext
    //val doc = browser.parseFile("core/src/test/resources/example.html")

  }

  def councilParse(html: String): String = {

    val browser = JsoupBrowser()
    val doc = browser.parseString(html)

    // Check if there are at least 3 ".active" elements
    //val outcome2 = doc >/~ validator(".active")(_.size >= 3)

    // Check if there are at least 3 ".active" elements
    //val outcome2 = doc >/~ validator(".active")(_.size >= 3)


    val collectionDivElement = doc >?>  element(nextCollectionDivId)



    // Get the "Next Collections" heading
    val nextCollectionHeading  = collectionDivElement >> elementList("h4")

    // Get the text value only from the H4 element.
    val nextCollectionHeadingText = doc >> extractor(nextCollectionDivId + " h4",texts)

    // Get both lists of bins.
    val nextCollectionBinDates  = doc >> elementList(nextCollectionDivId + " ul")

    // Get all of the bins listed for both dates.
    val nextCollectionBinDatesBinsAll  = doc >> elementList("#nextCollectionSection h4 ul li")

    // Validate that there is a NextCollection section on the page
    val validateNextCollectionSection = doc >/~ validator(elementList(nextCollectionDivId))(_.size == 1) match {
      case Left(s) => println("Failed to find exactly one nextCollection section: " + s)
      case Right(i) => println("Successfully found one NExtCollection setion: " + i)

    }

  // Validate that there are two h5 headers for us to parse.
    val validateNumberOfh5s = doc >/~ validator(elementList("h5"))(_.size == 2) match {
      case Left(s) => println("Failed to find two h5s for next dates: " + s)
      case Right(i) => println("Successfully found two h5s: " + i)

    }

    // Instead of looping through items and parsing text,


    // Loop through the elements under H4 until the end of the Div and assign the text of each li to each top-level date.
//val whatIneedtoloopthrough = collectionDivElement.asInstanceOf[Some].value.asInstanceOf[Element].childNodes.toList

    //val importantStringsList: List[String] = ArrayBuffer[String]
    var dateName:String = ""
    var mapOfItems:Map[String,String] = Map()

    collectionDivElement.get.children.foreach{
//      i => println(i.toString)
//        println(i.getClass)
//        // If the JsoupoElement is h5, capture the text from this and place it in key of Map - use i.tagName == h5 OR ul
//        // If the JsoupElement is ul, get the text of the children into a list of strings and this list of strings as the value to the key above
//          println(i.attrs)
//        println(i.innerHtml)
//        println(i.tagName)
//        println("---------")
i =>
        i.tagName match {
          case "h5" => {println(i.text); dateName = i.text}
          case "ul" => mapOfItems.put(dateName, "test")//TODO = work out how to replace "test" with a List of Strings which are the text from the children(i.children.map(child => (child.text).toList)))
          case _ => println("not ul")}
        println("--child----")
    }
    collectionDivElement.toString
  }

}
