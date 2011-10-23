package com.Mp3Digger.Main.Test

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import scalax.io.{Resource}
import scalax.io.Line.Terminators
import com.novus.salat._
import com.novus.salat.global._
import com.Mp3Digger.Parsing.{ArticleDtoBase, ArticleParser}
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters
import scala.collection.JavaConversions
import scala.util.parsing.json.{JSONArray, JSONObject, JSON}
import akka.serialization.JsonSerialization._
import com.Mp3Digger.Parsing.ArticleImports._
import dispatch.json.JsValue

class ArticleParsingIntegrationTest extends FunSuite with ShouldMatchers with BeforeAndAfterEach {
  val baseDirectory = "C:\\Users\\Nicholas\\Desktop\\Mp3Digger\\Core\\"
  val articleParser = new ArticleParser

  test("Can parse all of the stuff in a file") {
    val testData = Resource.fromFile(baseDirectory + "Batch.6197938.6297937.json").reader.lines(Terminators.Auto, false)
    val articleDtos = testData.map((x) => fromjson(JsValue.fromString(x))).map(articleParser.reparse)
    articleDtos.take(10).foreach((x) => println("hasFileName: (%s) (%s) (%s) (%s) (%s)".format(!x.fileName.isEmpty, x.articleId, x.articleNumber,x.articleSubject, x.articlePoster)))

    val articlesWithFileName = articleDtos.count(x => x.fileName.isEmpty)
    println("%s/%s articles with filenames".format(articlesWithFileName, articleDtos.size))
  }

//  test("Can get files"){
//    val stuff = Path.fromString(baseDirectory).children((x: Path) => x.isFile && x.name.endsWith(".json")).map(x => x.toString)
//    println(stuff.reduceLeft(_ + "\n" + _))
//  }
}