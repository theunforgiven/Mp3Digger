package com.Mp3Digger.Actor

import akka.actor.Actor
import Messages.SaveArticleBatch
import com.Mp3Digger.Service.Batch
import java.io.File
import scalax.io.{Resource, Output}
import com.novus.salat._
import com.novus.salat.global._
import com.Mp3Digger.Parsing.ArticleDtoBase
import akka.serialization.JsonSerialization._
import com.Mp3Digger.Parsing.ArticleImports._

class FileActor extends Actor {
  override def preStart() {
    println("FileActor Starting")
  }

  override def postStop() {
    println("FileActor Stopping")
  }
  def saveToFile(batch: Batch, articles: List[ArticleDtoBase]) {
    val file = new File("Batch." + batch.start + "." + batch.end + ".json")
    file.createNewFile()
    val output:Output = Resource.fromFile(file)
    val fileLines = articles.map((x) => tojson[ArticleDtoBase](x).toString())
    output.writeStrings(fileLines, "\n")
    println("Wrote file: " + file.getAbsolutePath + file.getName)
  }

  protected def receive = {
    case SaveArticleBatch(batch, articles) => {
      saveToFile(batch, articles)
    }
  }
}