package com.Mp3Digger.Repository

import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.WriteConcern
import com.Mp3Digger.Repository.PostImports._
import collection.immutable.List


class MongoPostRepository(db: MongoDB) extends PostRepository {
  private val articleEntries = db("articleEntries")
	articleEntries.ensureIndex(MongoDBObject("fileName" -> 1, "poster" -> 1), "fileNamePoster_index", true)

	def findByPosterAndFileName(fileName: String, poster: String): Option[Post] = {
		val result = articleEntries.findOne(MongoDBObject("fileName" -> fileName, "poster" -> poster))
		result.map {
			           db2question
		           }
	}

	def save(articleEntry: Post): Post = {
		articleEntry.id match {
			case None             => {
				val dbObj = grater[Post].asDBObject(articleEntry)
				val result = articleEntries.insert(dbObj, WriteConcern.Safe)
				articleEntry.copy(id = Some(dbObj("_id").toString))
			}
			case Some(id: String) => {
				val dbObj = grater[Post].asDBObject(articleEntry)
				val res = articleEntries.update(MongoDBObject("_id" -> new ObjectId(id)), dbObj, false, false)
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

	def findById(id: String): Option[Post] = {
		val result: Option[DBObject] = articleEntries.findOne(MongoDBObject("_id" -> new ObjectId(id)))
		result.map {
			           db2question
		           }
	}

	def appendFilePartsToPost(postId: String, fileParts: List[PostFilePart]) {
		val mongoFileParts = fileParts.map(grater[PostFilePart].asDBObject(_))
		val updateQuery = $addToSet("postFileParts") $each mongoFileParts
		val query = MongoDBObject("_id" -> new ObjectId(postId))
		articleEntries.update(query, updateQuery)
	}

  def findSomeShit(): scala.List[Post] = {
    articleEntries.find()
                  .limit(100)
                  .map( db2question )
                  .toList
  }
}