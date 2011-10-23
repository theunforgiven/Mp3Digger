package com.Mp3Digger.Actor

import akka.actor.Actor
import Messages.{SaveArticleBatch, FetchArticleBatch, FetchLastArticleNumber}
import org.apache.commons.net.nntp.{NewsgroupInfo, NNTPClient}
import com.Mp3Digger.Service.{Batch, NNTPClientFactory}
import scala.collection.JavaConversions._
import scalax.io._
import com.novus.salat._
import com.novus.salat.global._
import java.io.File
import com.Mp3Digger.Parsing.{ArticleDtoBase, ArticleParser, ArticleDto}

class NtpActor(nntpClientFactory: NNTPClientFactory) extends Actor {
  val articleParser = new ArticleParser()
  var ntpClient: NNTPClient = _;

  override def preStart() {
    println("NtpActor Starting")
    ntpClient = nntpClientFactory.createClient()
  }

  override def postStop() {
    println("NtpActor Stopping")
    if (ntpClient != null) {
      try {
        ntpClient.disconnect()
      }
    }
  }

  def getArticleCount(newsGroup: String): (Long, Long) = {
    val groupInfo = new NewsgroupInfo();
    ntpClient.selectNewsgroup(newsGroup, groupInfo)
    (groupInfo.getFirstArticleLong, groupInfo.getLastArticleLong)
  }

  def getArticleBatch(batch: Batch, newsGroup: String): List[ArticleDtoBase] = {
    ntpClient.selectNewsgroup(newsGroup)
    ntpClient.iterateArticleInfo(batch.start, batch.end)
             .map(articleParser.baseParse)
             .toList
  }



  protected def receive = {
    case FetchLastArticleNumber(newsGroup: String) => {
      val articleCount = getArticleCount(newsGroup)
      self.reply(articleCount)
    }

    case FetchArticleBatch(batch, newsGroup) => {
      println("Would fetch headers of group " + newsGroup + " from " + batch.start + " to " + batch.end)
      val articleBatch = getArticleBatch(batch, newsGroup)
      self.channel ! SaveArticleBatch(batch, articleBatch)
    }
  }
}

