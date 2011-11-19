package com.ForeFront.Test

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import com.ForeFront.snippet.ArticleUtil

class ArticleUtilTest extends FunSuite with BeforeAndAfterEach with BeforeAndAfterAll with ShouldMatchers {

  test("Can find some shit") {
    val ret = ArticleUtil.viewArticles()
    ret should not be ('empty)
  }
}