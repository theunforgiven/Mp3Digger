package com.Mp3Digger.Parsing

import org.apache.commons.net.nntp.Article
import com.Mp3Digger.Repository.{PostFilePart, Post}

class ArticleParser {
  val subjectParser = new SubjectParser

  def parse(articleSubject: String, articleId: String,  articlePoster: String,  articleNumber: Long): ArticleDto = {
    val fileName = safely(() => subjectParser.parseFileName(articleSubject), "")
    val (filePart, totalFileParts) = safely(() => subjectParser.parseFilePart(articleSubject), (0,0))
    val (postPart, totalPostParts) = safely(() => subjectParser.parsePostPart(articleSubject), (0,0))
    val postTitle = safely(() => subjectParser.parsePostTitle(articleSubject), "")

    new ArticleDto(articleId, articleNumber, articlePoster, articleSubject, fileName, filePart, totalFileParts, postPart, totalPostParts)
  }

  def baseParse(articleSubject: String, articleId: String,  articlePoster: String,  articleNumber: Long): ArticleDtoBase = {
    new ArticleDtoBase(articleId, articleNumber, articlePoster, articleSubject)
  }

  def parse(article: Article): ArticleDto = {
    val articleSubject = article.getSubject
    val articleId = article.getArticleId
    val articlePoster = article.getFrom
    val articleNumber = article.getArticleNumberLong

    parse(articleSubject, articleId, articlePoster, articleNumber)
  }

  def baseParse(article: Article): ArticleDtoBase = {
    val articleSubject = article.getSubject
    val articleId = article.getArticleId
    val articlePoster = article.getFrom
    val articleNumber = article.getArticleNumberLong

    baseParse(articleSubject, articleId, articlePoster, articleNumber)
  }

  def reparse(articleBase: ArticleDtoBase) : ArticleDto = {
    return parse(articleBase.articleSubject, articleBase.articleId, articleBase.articlePoster, articleBase.articleNumber)
  }

  private def safely[T](func: () => T, defVal: T): T = {
    try
    {
      func()
    }
    catch{
      case e => defVal
    }
  }
}