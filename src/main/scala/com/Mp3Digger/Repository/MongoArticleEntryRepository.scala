package com.Mp3Digger.Repository

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.WriteConcern


class MongoArticleEntryRepository(db: MongoDB) extends ArticleEntryRepository {
  private val articleEntries = db("articleEntries")
  articleEntries.ensureIndex(MongoDBObject("fileName" -> 1 ) , "fileName_index", true)

  def findByFileName(s: String): Option[Post] = {
    val result = articleEntries.findOne(MongoDBObject("fileName" -> s))
    result.map { db2question }
  }

  def save(articleEntry: Post): Post = {
    articleEntry.id match {
      case None =>{
            val dbObj = grater[Post].asDBObject(articleEntry)
            val result = articleEntries.insert(dbObj, WriteConcern.Safe)
            articleEntry.copy(id = Some(dbObj("_id").toString))
      }
      case Some(id: String) => {
            val dbObj = grater[Post].asDBObject(articleEntry)
            val res = articleEntries.update(MongoDBObject("_id" -> new ObjectId(id)), dbObj, false,false)
            articleEntry.copy(id = Some(dbObj("id").toString))
      }
    }
  }

  def save(articles: List[Post]) {

    val dbObjs = articles.map((x) => grater[Post].asDBObject(x))
    articleEntries.insert(dbObjs)
  }

  def db2question(dbObj: DBObject): Post = {
      val articleEntry = grater[Post].asObject(dbObj)
      articleEntry.copy(id = Some(dbObj("_id").toString))
  }

  def findById(id: String) : Option[Post] = {
    val result: Option[DBObject] = articleEntries.findOne(MongoDBObject("_id" -> new ObjectId(id)))
    result.map { db2question }
  }
}