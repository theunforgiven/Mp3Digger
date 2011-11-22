package com.Mp3Digger.Main.Test

import com.mongodb.casbah.MongoConnection
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import com.Mp3Digger.Repository.PostImports._
import com.Mp3Digger.Repository.{Post, PostFilePart, MongoPostRepository}

class MongoPostRepositoryTest extends FunSuite with BeforeAndAfterEach with BeforeAndAfterAll with ShouldMatchers {

  val con = MongoConnection("localhost")
  val TestDbName = "test_Mp3Usenet"
  val db = con(TestDbName)
  val articleEntries = db("articleEntries")
  var repository: MongoPostRepository = _

  override def beforeAll() {

  }

  override def beforeEach() {
    articleEntries.drop()
    repository = new MongoPostRepository(db)
  }

  def createSavedPost(totalPartCount: Int = 2): Post = {
    val postFiles = List(new PostFilePart(1, "articleId", totalPartCount), new PostFilePart(2, "articleId2", totalPartCount))
    val post = new Post(None, "Poster", "fName.ext", 2, "title", postFiles)
    repository.save(post)
  }

  test("Can find article by id") {
    val savedPost = createSavedPost()
    val retrievedPost = repository.findById(savedPost.id.get)

    retrievedPost should be('defined)
    retrievedPost.get should equal(savedPost)
  }

  test("Can find article by filename and poster") {
    val savedPost = createSavedPost()
    val retrievedPost = repository.findByPosterAndFileName("fName.ext", "Poster")

    retrievedPost should be('defined)
    retrievedPost.get should equal(savedPost)
  }

  test("Can Save Article Entry To MongoDB") {
    val savedPost = createSavedPost()

    val retrievedArticleEntry = repository.findById(savedPost.id.get)
    retrievedArticleEntry.get should be(savedPost)
  }

  test("Can append file parts to an existing post") {
    val savedPost: Post = createSavedPost()
    val postFiles = savedPost.postFileParts
    val postId = savedPost.id.get
    val postFilesToAppend = List(new PostFilePart(3, "articleId", 4), new PostFilePart(4, "articleId2", 4))
    repository.appendFilePartsToPost(postId, postFilesToAppend)
    val retrievedPost = repository.findById(postId)
    val expectedPostFileParts = postFiles ++ postFilesToAppend
    retrievedPost.get.postFileParts should be(expectedPostFileParts)
  }

  test("Can find some shit") {
    val postId = createSavedPost().id.get
    val someShit = repository.findSomeShit()
    someShit should not be ('empty)
  }
}