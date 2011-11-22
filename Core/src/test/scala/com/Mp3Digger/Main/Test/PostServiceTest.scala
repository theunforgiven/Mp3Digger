package com.Mp3Digger.Main.Test

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.casbah.MongoConnection._
import com.mongodb.casbah.MongoConnection
import com.Mp3Digger.Service.PostService
import collection.mutable.ListBuffer
import com.Mp3Digger.Repository.{PostRepository, PostFilePart, Post, MongoPostRepository}


class PostServiceTest extends FunSuite with BeforeAndAfterEach with BeforeAndAfterAll with ShouldMatchers {
	val con                  = MongoConnection("localhost")
	val TestDbName           = "test_Mp3Usenet"
	val db                   = con(TestDbName)
	var repository: PostRepository= _
	var service: PostService = _

	override def beforeAll()
	{

	}

	override def beforeEach()
	{
		db("articleEntries").drop()
		repository = new MongoPostRepository(db)
		service = new PostService(repository)
	}

	def createFilePost(poster: String = "poster", subject: String = "Post Subject", fileName: String = "fileName.ext", partCount: Int = 10, partCountStart: Int = 1): Post = {
		val postBuffer = ListBuffer[PostFilePart]()
		for (part <- partCountStart.until(partCount + partCountStart)) {
			postBuffer.append(PostFilePart(part, "articleId", part))
		}
		new Post(None, poster, fileName, partCount, subject, postBuffer.toList)
	}

	test("Can save a post")
	{
		val post = createFilePost()
		val savedPost = service.savePost(post)

		savedPost.id should be('defined)
		post should be(savedPost.copy(id = None))
	}

	test("Can save a list of posts")
	{
		val posts = List(createFilePost(), createFilePost(fileName = "otherFileName.ext"))
		val savedPosts = service.savePosts(posts)

		savedPosts.foreach(_.id should be ('defined))
		posts should be(savedPosts.map(_.copy(id = None)))
	}

	test("Can save a post in multiple pieces")
	{
		val postPart1 = createFilePost()
		val postPart2 = createFilePost(partCountStart = 11)
		val originalFileParts = postPart1.postFileParts ++ postPart2.postFileParts

		val savedPart1 = service.savePost(postPart1)
		val savedPart2 = service.savePost(postPart2)

		savedPart1.id should be(savedPart2.id)
		val retrievedFileParts = repository.findById(savedPart1.id.get).get.postFileParts
		retrievedFileParts should be(originalFileParts)
	}

}