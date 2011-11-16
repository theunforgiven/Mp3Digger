package com.Mp3Digger.Main.Test

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import scalax.io.Line.Terminators
import sjson.json._
import JsonSerialization._
import com.Mp3Digger.Parsing.ArticleImports._
import dispatch.json.JsValue
import java.io.File
import scalax.io.{Output, LongTraversable, Resource}
import com.Mp3Digger.Parsing.{ArticleDtoBase, ArticleAssembler, ArticleDto, ArticleParser}
import java.lang.String
import com.Mp3Digger.Repository.{MongoPostRepository, Post, PostFilePart}
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.Imports._
import com.Mp3Digger.Service.PostService
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.util.Locale

class ArticleParsingIntegrationTest extends FunSuite with ShouldMatchers with BeforeAndAfterEach {
	val projectRoot             = "C:\\Users\\Nicholas\\Desktop\\Mp3Digger\\Core\\"
	val testDataDirectory       = projectRoot + "TestData\\"
	val testDataOutputDirectory = projectRoot + "TestOutput\\"
	val articleParser           = new ArticleParser

	def partitionToList(articles: Traversable[ArticleDto], partitionFunction: (ArticleDto) => Boolean): (Traversable[ArticleDto], Traversable[ArticleDto]) = {
		val partitionedArticles = articles.partition(partitionFunction)
		(partitionedArticles._1, partitionedArticles._2)
	}

	def readTestDataFile(fileName: String): LongTraversable[String] = {
		val testData = Resource
		               .fromFile(fileName)
		               .reader
		               .lines(Terminators.Auto, false)

		testData.isEmpty should be(false)
		testData
	}

	ignore("Can parse all of the stuff in a file")
	{
		val junkArticleFilter = junkArticleFilterBuilder(junkFilterList)
		val testData = readTestDataFile(testDataDirectory + "Batch.6297938.6397937.json")
		val parsedArticles = testData.map((x) => articleParser.reparse(fromjson[ArticleDtoBase](JsValue.fromString(x)))).toList

		val (emptyFileNameArticles, articlesWithFileNames) = partitionToList(parsedArticles, _.fileName.isEmpty)
		val (junkEmptyFileNameArticles, nonJunkEmptyFileNameArticles) = partitionToList(emptyFileNameArticles, junkArticleFilter)

		junkEmptyFileNameArticles.foreach(x => println("Skipping Junk Line: " + x.article.articleSubject))
		failIfNonJunkFileNameEmpty(nonJunkEmptyFileNameArticles)
	}

	ignore("Can parse and assemble all of the stuff in a file")
	{
		val articleAssembler = new ArticleAssembler()
		val junkArticleFilter = junkArticleFilterBuilder(junkFilterList)
		val testData = readTestDataFile(testDataDirectory + "Batch.6297938.6397937.json")
		val parsedArticles = testData.map((x) => articleParser.reparse(fromjson[ArticleDtoBase](JsValue.fromString(x)))).toList

		val (emptyFileNameArticles, articlesWithFileNames) = partitionToList(parsedArticles, _.fileName.isEmpty)
		val (junkEmptyFileNameArticles, nonJunkEmptyFileNameArticles) = partitionToList(emptyFileNameArticles, junkArticleFilter)

		val assembledArticles = articleAssembler.assembleArticles(articlesWithFileNames.toList)
		saveToFile("assembledArticles.json", assembledArticles)
		println(assembledArticles.size)
	}

	def saveToMongo(db: MongoDB, posts: List[Post]) {
		val repository = new MongoPostRepository(db)
		val service = new PostService(repository)
		service.savePosts(posts)
	}

	def createDbConnection: MongoDB = {
		val con = MongoConnection("localhost")
		val TestDbName = "Mp3UsenetIntTest"
		con(TestDbName)
	}

	//	class FileVisitor extends SimpleFileVisitor[Path] {
	//		val listBuffer = new ListBuffer[String]()
	//
	//		override def visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult =
	//		{
	//			if (file.toString.contains(".json"))
	//			{
	//				listBuffer.append(file.toString)
	//			}
	//			FileVisitResult.CONTINUE
	//		}
	//	}
	//
	def getTestDataFiles(): List[String] =
	{
		import scalax.file.{Path, PathMatcher}
		val testDataFileMatcher = PathMatcher.IsFile && PathMatcher.GlobNameMatcher("*.json")
		Path(testDataDirectory).descendants(filter = testDataFileMatcher)
							   .map(_.toString())
							   .toList

	}

	ignore("Can get all test data files")
	{
		val dataFiles = getTestDataFiles()
		dataFiles.size should be > 0
		println(dataFiles.map(_.toString()))
	}

	def loadTestDataFileIntoMongo(articleAssembler: ArticleAssembler, db: MongoDB, testDataFile: String) {
		val testData = readTestDataFile(testDataFile)
		log("Read file " + testDataFile)
		val parsedArticles = testData.map((x) => articleParser.reparse(fromjson[ArticleDtoBase](JsValue.fromString(x)))).toList
		log("Parsed file " + testDataFile)
		val (_, articlesWithFileNames) = partitionToList(parsedArticles, _.fileName.isEmpty)
		val assembledArticles = articleAssembler.assembleArticles(articlesWithFileNames.toList)
		log("Assembled file " + testDataFile)
		saveToMongo(db, assembledArticles)
		log("Saved to mongo file" + testDataFile)
	}

	def log(message: String) {
		val now = new DateTime()
		val formatter = DateTimeFormat.forStyle("SL").withLocale(Locale.US)
		println("[" + formatter.print(now) + "]: " + message)
	}

	ignore("printing dateetime")
	{
		val message = "hi"
		log(message)

	}
	test("Can parse into mongo")
	{
		val db = createDbConnection
		db("articleEntries").drop()
		val articleAssembler = new ArticleAssembler()
		fail("Rewrite getTestDataFiles sans java NIO")
		//		val testDataFiles = getTestDataFiles()
		//		for (testDataFile <- testDataFiles) {
		//			log("Parsing file " + testDataFile)
		//			loadTestDataFileIntoMongo(articleAssembler, db, testDataFile)
		//		}
		//		fail()

		//		saveToFile("assembledArticles.json", assembledArticles)
	}

	def toJsonString[T](post: T): String = {
		try {
			new String(sjson.json.Serializer.SJSON.out(post.asInstanceOf[AnyRef]), "UTF-8")
		} catch {
			case e: Exception => {
				println(e.getStackTrace)
				throw e
			}
		}
	}

	def jsonStringToObject[T](json: String)(implicit m: Manifest[T]): T = {
		try {
			sjson.json.Serializer.SJSON.in(json.getBytes("UTF-8"), m.erasure.getName).asInstanceOf[T]
		} catch {
			case e: Exception => {
				println(e.getStackTrace)
				throw e
			}
		}
	}

	ignore("Can json a post file part")
	{
		val postFilePart: PostFilePart = new PostFilePart("title", 1, "articleid", 12345)
		val asJson = toJsonString(postFilePart)

		asJson should not be (null)
		asJson.size should be > 0
		jsonStringToObject[PostFilePart](asJson) should be(postFilePart)
	}

	ignore("Can json a post")
	{
		val post = new Post(None, "poster", "filename", 2, List())
		val asJson = toJsonString(post)

		asJson should not be (null)
		asJson.size should be > 0
		jsonStringToObject[Post](asJson) should be(post)
	}
	ignore("Can json a post and post file parts")
	{
		val postFilePart: PostFilePart = new PostFilePart("title", 1, "articleid", 12345)
		val post = new Post(Option("abc"), "poster", "filename", 2, List(postFilePart))
		val asJson = toJsonString(post)

		asJson should not be (null)
		asJson.size should be > 0
		jsonStringToObject[Post](asJson) should be(post)
	}

	def saveToFile(fileName: String, articles: List[Post]) {
		val file = new File(fileName)
		file.createNewFile()
		val output: Output = Resource.fromFile(file)

		val fileLines = toJsonString(articles)
		output.write(fileLines)
		println("Wrote file: " + file.getAbsolutePath + file.getName)
	}

	def failIfNonJunkFileNameEmpty(articles: Traversable[ArticleDto]) {
		val missingFileNames = articles.map(x => "hasFileName false: (%s)\n".format(x.article.articleSubject))
		val errorMessage = missingFileNames.foldLeft("")((x, y) => y + x)
		if (!errorMessage.isEmpty) {
			fail(errorMessage)
		}
	}

	def junkArticleFilterBuilder(filterString: List[String]): (ArticleDto) => Boolean = {
			article => filterString.find(x => article.articleSubject.contains(x)).isDefined
	}

	val junkFilterList = List("Turmfalke + rebelrider",
	                          "had er nog's",
	                          "Les Porcs Autonomes",
	                          "REQ: The Vines cover of Ms. Jackson",
	                          "REQUEST disc 17 van Ik zou je het liefste in een doosje willen doen",
	                          "Edwin McCain - I'll Be",
	                          "Aly & AJ \u0096 Greatest Time of Year",
	                          "!Req!",
	                          "REQ:  ",
	                          "Gothic Vampire Metal 27446",
	                          "!REQ ",
	                          "looking for Britney Spears Radar (Pink Delorean Mix)",
	                          "Cheetah Girls",
	                          "REQ: Fleet Foxes",
	                          "REQ: Fleet Foxes",
	                          "REQ: Exodus - FLOOD",
	                          "WANTED PLZ - Beautiful South / Housemartins - Soup Best Of",
	                          "Anyone Got New Birth",
	                          "A humble request for Woody/Arlo Guthrie please...",
	                          "REQ: The Walls Came Down by The Call",
	                          "!Request! The Vines cover of Ms Jackson see info",
	                          "Blues Collection Request",
	                          "Req Brandi Carlile - The Story",
	                          "verzoekje... wie heeft hitzone 45 en 46? alvast bedankt!",
	                          "!!!   !!! Looking for",
	                          "verzoekje... wie heeft hitzone");

}