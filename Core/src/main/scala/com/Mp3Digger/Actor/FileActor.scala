package com.Mp3Digger.Actor

import akka.actor.Actor
import Messages.SaveArticleBatch
import java.io.File
import scalax.io.{Resource, Output}
import com.novus.salat._
import com.novus.salat.global._
import akka.serialization.JsonSerialization._
import com.Mp3Digger.Parsing.ArticleImports._
import com.mongodb.casbah.MongoConnection._
import com.mongodb.casbah.MongoConnection
import com.Mp3Digger.Repository.MongoPostRepository
import com.Mp3Digger.Service.{PostService, Batch}
import com.Mp3Digger.Parsing.{ArticleDto, ArticleAssembler, ArticleDtoBase}

class FileActor extends Actor {
  val con = MongoConnection("localhost")
  val repository = new MongoPostRepository(con("Mp3Digger"))
  val service = new PostService(repository)
  val articleAssembler = new ArticleAssembler();
  override def preStart() {
    println("FileActor Starting")
  }

  override def postStop() {
    println("FileActor Stopping")
  }
  def saveToFile(batch: Batch, articles: List[ArticleDto]) {
    val arts = articleAssembler.assembleArticles(articles)
    service.savePosts(arts)
  }

  protected def receive = {
    case SaveArticleBatch(batch, articles) => {
      saveToFile(batch, articles)
    }
  }
}