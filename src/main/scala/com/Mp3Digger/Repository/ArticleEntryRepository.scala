package com.Mp3Digger.Repository

import com.mongodb.casbah.MongoDB
import org.joda.time.DateTime

trait ArticleEntryRepository {
  def save(question: Post): Post
  def findById(id: String): Option[Post]
}