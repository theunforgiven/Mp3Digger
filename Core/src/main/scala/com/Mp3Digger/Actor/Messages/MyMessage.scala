package com.Mp3Digger.Actor.Messages

import com.Mp3Digger.Service.Batch
import com.Mp3Digger.Parsing.{ArticleDto, ArticleDtoBase}

sealed trait MyMessage
case class FetchArticles(start: Long, newsGroup: String) extends MyMessage

case class FetchLastArticleNumber(newsGroup: String) extends MyMessage
case class FetchArticleBatch(batch: Batch, newsGroup: String) extends MyMessage

case class SaveArticleBatch(batch: Batch, articles: List[ArticleDto])