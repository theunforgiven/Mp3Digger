package com.Mp3Digger.Main.Test

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import org.scalatest.matchers.ShouldMatchers
import com.ForeFront.snippet.ArticleUtil

/**
 * Created by IntelliJ IDEA.
 * User: Nicholas
 * Date: 11/19/11
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */

class ArticleUtilTest extends FunSuite with BeforeAndAfterEach with BeforeAndAfterAll with ShouldMatchers {

  test("Can find some shit") {
    val ret = ArticleUtil.viewArticles()
    ret should not be ('empty)
  }
}