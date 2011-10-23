package com.Mp3Digger.Service

import scala.math._

class BatchFactory {
  def createBatches(start: Long, end: Long, batchSize: Long): List[Batch] = {
    var resultList: List[Batch] = List()

    for (i <- start until end by batchSize) {
      val rangeHi = min(i + (batchSize - 1), end)
      resultList = new Batch(i, rangeHi) :: resultList
    }

    resultList.sortBy(x => x.start)
  }
}

class Batch(val start: Long, val end: Long) {
  override def toString = "(%d, %d)" format(start, end)

  override def hashCode = (41 * (41 + start) + end).toInt

  override def equals(other: Any): Boolean = other match {
    case that: Batch => (
      that.canEqual(this)
        && this.start == that.start
        && this.end == that.end
      )
    case _ => false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Batch]
}