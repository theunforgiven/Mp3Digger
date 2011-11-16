package com.Mp3Digger.Main.Test

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.Mp3Digger.Parsing.{ArticleAssembler, ArticleDto}
class ArticleAssemblerTest extends FunSuite with ShouldMatchers {
  val articleAssembler = new ArticleAssembler()

  def createArticleDto(filePart: Int = 1, articlePoster: String = "poster", articleId: String = "1", articleNumber: Long = 1, totalFileCount: Int = 5, fileName: String = "fName.ext") : ArticleDto = {
    new ArticleDto(articleId, articleNumber, articlePoster, "subject", fileName, filePart, totalFileCount, 1, 1)
  }

  def createSinglePartArticle(fileName: String = "fName.ext") : (ArticleDto, List[ArticleDto]) = {
    val articleOrig = createArticleDto(fileName = fileName, totalFileCount = 1)
   (articleOrig, List(articleOrig))
  }

  def createMultiPartPost(fileName: String = "fName.ext"): List[ArticleDto] = {
    List(createArticleDto(fileName = fileName), createArticleDto(filePart = 2, fileName = fileName), createArticleDto(filePart = 3, fileName = fileName))
  }

  test("Can assemble single-part article into single file article") {
    val (articleOrig, articles) = createSinglePartArticle()
    val assembledArticles = articleAssembler.assembleArticles(articles)

    assembledArticles.length should equal (1)
    val article = assembledArticles.head

    article.totalFilePartCount should be(1)
    article.poster should be("poster")
    article.fileName should be("fName.ext")
    article.postFileParts should have size (1)

    val postFilePart = article.postFileParts.head
    postFilePart.articleId should be(articleOrig.articleId)
    postFilePart.articleNumber should be(articleOrig.articleNumber)
    postFilePart.filePart should be(articleOrig.filePart)
  }

  test("Can assemble multi-part article into single file article") {
    val articles = createMultiPartPost()

    val assembledArticles = articleAssembler.assembleArticles(articles)
    assembledArticles should have length (1)

    val article = assembledArticles.head
    article.fileName should be("fName.ext")
    article.totalFilePartCount should be(5)
    article.poster should be("poster")
  }

  test("Can assemble multi-part and single-part articles at the same time") {
    val multiPartArticles = createMultiPartPost("fName1.ext")
    val singlePartArticles = createSinglePartArticle("fName2.ext")._2

    val assembledArticles = articleAssembler.assembleArticles(singlePartArticles ++ multiPartArticles)
    assembledArticles should have length (2)
  }
}