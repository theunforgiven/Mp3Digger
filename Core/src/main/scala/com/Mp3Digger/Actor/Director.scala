package com.Mp3Digger.Actor

import java.util.concurrent.CountDownLatch
import akka.actor.{PoisonPill, ActorRef, Actor}
import Messages._
import scala.math.max
import com.Mp3Digger.Service.{Batch, BatchFactory}

class Director(workerCount: Int, batchCount: Int, ntpPool: ActorRef, filePool: ActorRef, latch: CountDownLatch) extends Actor {
  val batchFactory = new BatchFactory()

  def receive = {
    case FetchArticles(start, newsGroup) => {
      val batches: scala.List[Batch] = batchArticleFetching(newsGroup, start)

      for (batch <- batches)
      {
        ntpPool ! FetchArticleBatch(batch, newsGroup)
      }

      ntpPool ! PoisonPill
    }
    case FetchNewsgroupList() => {
      ntpPool ! FetchNewsgroupList()
    }
    case SaveArticleBatch(batch, articles) => {
      filePool ! SaveArticleBatch(batch, articles)
    }
  }

  override def postStop() {
    latch.countDown()
  }

  def batchArticleFetching(newsGroup: String, start: Long): scala.List[Batch] = {
    val (articleLow, articleHigh) = (ntpPool ? FetchLastArticleNumber(newsGroup)).get.asInstanceOf[(Long, Long)]
    println("Got article ramge low: " + articleLow + " high: " + articleHigh)
    val batches = batchFactory.createBatches(max(articleLow, start), articleHigh, batchCount)
    batches
  }
}

