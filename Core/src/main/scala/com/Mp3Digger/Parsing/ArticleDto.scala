package com.Mp3Digger.Parsing
import akka.serialization.DefaultProtocol._

case class ArticleDtoBase(articleId: String, articleNumber: Long, articlePoster: String, articleSubject: String) {

}
case class ArticleDto(fileName: String, filePart: Int, totalFileParts: Int, postPart: Int, totalPostParts: Int, article: ArticleDtoBase)
  extends ArticleDtoBase(article.articleId, article.articleNumber, article.articlePoster, article.articleSubject) {
  def this(articleId: String, articleNumber: Long, articlePoster: String, articleSubject: String, fileName: String,
           filePart: Int, totalFileParts: Int, postPart: Int, totalPostParts: Int) = this(fileName, filePart, totalFileParts, postPart, totalPostParts, new ArticleDtoBase(articleId, articleNumber, articlePoster, articleSubject))
}

object ArticleImports {
  implicit val ArticleDtoBaseFormat: sjson.json.Format[ArticleDtoBase] = asProduct4("articleId", "articleNumber", "articlePoster", "articleSubject")(ArticleDtoBase)(ArticleDtoBase.unapply(_).get)
}

