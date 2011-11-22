package com.Mp3Digger.Repository

import org.apache.commons.net.nntp.NewsgroupInfo

case class Newsgroup(name: String, articleCount: Long) {

}

object Newsgroup {
  def apply(newsgroupInfo: NewsgroupInfo): Newsgroup = {
    Newsgroup(newsgroupInfo.getNewsgroup, newsgroupInfo.getArticleCountLong)
  }
}