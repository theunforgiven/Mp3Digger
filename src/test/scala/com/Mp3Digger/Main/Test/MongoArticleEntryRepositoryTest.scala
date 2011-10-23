package com.Mp3Digger.Main.Test

import com.mongodb.casbah.MongoConnection
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import com.Mp3Digger.Repository.{Post, PostFilePart, MongoArticleEntryRepository}

class MongoArticleEntryRepositoryTest extends FunSuite with BeforeAndAfterEach with BeforeAndAfterAll with ShouldMatchers {

  val con = MongoConnection("localhost")
  val TestDbName = "test_Mp3Usenet"
  val db = con(TestDbName)
  val articleEntries = db("articleEntries")
  var repository: MongoArticleEntryRepository = _

  override def beforeAll() {

  }

  override def beforeEach() {
    articleEntries.drop()
    repository = new MongoArticleEntryRepository(db)
  }

  test("Can find article by id") {
    val postFiles = List(new PostFilePart("Title", 1, "articleId", 1) , new PostFilePart("Title", 2, "articleId2",2))
    val post = new Post(None, "Poster", "fName.ext", 2, postFiles)
    val savedPost = repository.save(post)

    val retrievedPost = repository.findById(savedPost.id.get)
    retrievedPost should be('defined)
    retrievedPost.get should equal(savedPost)
  }

  test("Can find article by filename") {
    val postFiles = List(new PostFilePart("Title", 1, "articleId", 2) , new PostFilePart("Title", 2, "articleId2", 2))
    val post = new Post(None, "Poster", "fName.ext", 2, postFiles)
    val savedPost = repository.save(post)

    val retrievedPost = repository.findByFileName("fName.ext")
    retrievedPost should be('defined)
    retrievedPost.get should equal(savedPost)
  }
//  test("Can Save Article Entry To MongoDB") {
//    val articleEntry = new Post(None, 0, "Title", "Poster", "", "filename.ext", 0)
//    val savedArticleEntry = repository.save(articleEntry)
//
//    savedArticleEntry.id should be ('defined)
//
//    val retrievedArticleEntry = repository.findById(savedArticleEntry.id.get)
//    retrievedArticleEntry.get.id should be(savedArticleEntry.id)
//  }
}