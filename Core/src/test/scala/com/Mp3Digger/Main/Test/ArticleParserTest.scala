package com.Mp3Digger.Main.Test

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import org.mockito.Mockito._
import org.mockito.MockitoAnnotations.Mock
import org.apache.commons.net.nntp.Article
import org.mockito.MockitoAnnotations
import com.Mp3Digger.Parsing.{ArticleDto, ArticleParser}

class ArticleParserTest extends FunSuite with ShouldMatchers with BeforeAndAfterEach {
  val articleParser = new ArticleParser()
  @Mock var article: Article = _

  override def beforeEach() {
    MockitoAnnotations.initMocks(this)
  }

  def createArticleMock(articleId:String , articleNumber: Long, title: String, poster: String) {
    when(article.getArticleId()).thenReturn(articleId)
    when(article.getArticleNumberLong).thenReturn(articleNumber)
    when(article.getSubject()).thenReturn(title)
    when(article.getFrom()).thenReturn(poster)
  }
  def createArticleDto(filePart: Int = 1, articleId: String = "1", articleNumber: Long = 1, totalFileCount: Int = 5) : ArticleDto = {
    new ArticleDto(articleId, articleNumber, "poster", "subject", "fName.ext", filePart, totalFileCount, 1, 1)
  }
  test("Can parse multipart article") {
    val articleId ="myArticleId"
    val articleNumber = 13
    val articlePoster = "poster"
    val articleTitle = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
    createArticleMock(articleId, articleNumber, articleTitle, articlePoster)

    val articleDto = articleParser.parse(article)
    articleDto.articleId should equal(articleId)
    articleDto.articleNumber should equal(articleNumber)
    articleDto.articlePoster should equal(articlePoster)
    articleDto.articleSubject should equal(articleTitle)
    articleDto.fileName should equal("Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar")
    articleDto.filePart should equal(143)
    articleDto.totalFileParts should equal(211)
    articleDto.postPart should equal(10)
    articleDto.totalPostParts should equal(36)
  }
}