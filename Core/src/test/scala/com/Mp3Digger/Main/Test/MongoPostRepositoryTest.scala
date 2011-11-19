package com.Mp3Digger.Main.Test

import com.mongodb.casbah.MongoConnection
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import com.Mp3Digger.Repository.PostImports._
import com.Mp3Digger.Repository.{Post, PostFilePart, MongoPostRepository}

class MongoPostRepositoryTest extends FunSuite with BeforeAndAfterEach with BeforeAndAfterAll with ShouldMatchers {

	val con                                     = MongoConnection("localhost")
	val TestDbName                              = "test_Mp3Usenet"
	val db                                      = con(TestDbName)
	val articleEntries                          = db("articleEntries")
	var repository: MongoPostRepository = _

	override def beforeAll()
	{

	}

	override def beforeEach()
	{
		articleEntries.drop()
		repository = new MongoPostRepository(db)
	}

	test("Can find article by id")
	{
		val postFiles = List(new PostFilePart("Title", 1, "articleId", 1), new PostFilePart("Title", 2, "articleId2", 2))
		val post = new Post(None, "Poster", "fName.ext", 2, postFiles)
		val savedPost = repository.save(post)

		val retrievedPost = repository.findById(savedPost.id.get)
		retrievedPost should be('defined)
		retrievedPost.get should equal(savedPost)
	}

	test("Can find article by filename and poster")
	{
		val postFiles = List(new PostFilePart("Title", 1, "articleId", 2), new PostFilePart("Title", 2, "articleId2", 2))
		val post = new Post(None, "Poster", "fName.ext", 2, postFiles)
		val savedPost = repository.save(post)

		val retrievedPost = repository.findByPosterAndFileName("fName.ext", "Poster")
		retrievedPost should be('defined)
		retrievedPost.get should equal(savedPost)
	}

	test("Can Save Article Entry To MongoDB")
	{
		val postFiles = List(new PostFilePart("Title", 1, "articleId", 2), new PostFilePart("Title", 2, "articleId2", 2))
		val post = new Post(None, "Poster", "fName.ext", 2, postFiles)
		val savedPost = repository.save(post)

		val retrievedArticleEntry = repository.findById(savedPost.id.get)
		retrievedArticleEntry.get should be(savedPost)
	}

	test("Can append file parts to an existing post")
	{
		val postFiles = List(new PostFilePart("Title", 1, "articleId", 4), new PostFilePart("Title", 2, "articleId2", 4))
		val post = new Post(None, "Poster", "fName.ext", 4, postFiles)
		val postId = repository.save(post).id.get
		val postFilesToAppend = List(new PostFilePart("Title", 3, "articleId", 2), new PostFilePart("Title", 4, "articleId2", 2))
		repository.appendFilePartsToPost(postId, postFilesToAppend)
		val retrievedPost = repository.findById(postId)
		val expectedPostFileParts = postFiles ++ postFilesToAppend
		retrievedPost.get.postFileParts should be(expectedPostFileParts)
	}

  test("Can find some shit") {
    val postFiles = List(new PostFilePart("Title", 1, "articleId", 4), new PostFilePart("Title", 2, "articleId2", 4))
		val post = new Post(None, "Poster", "fName.ext", 4, postFiles)
		val postId = repository.save(post).id.get
    val someShit = repository.findSomeShit()
    someShit should not be('empty)
  }
}