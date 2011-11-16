package com.Mp3Digger.Main.Test

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.Mp3Digger.Service.{Batch, BatchFactory}

class BatchFactoryTest extends FunSuite with ShouldMatchers {
  val batchFactory = new BatchFactory()

  test("Can create a list of batches for a given range") {
    val start = 1;
    val end = 10;
    val chunkSize = 2;

    val result = batchFactory.createBatches(start, end, chunkSize)

    result should be(List(new Batch(1, 2), new Batch(3, 4), new Batch(5, 6), new Batch(7, 8), new Batch(9, 10)))
  }
}