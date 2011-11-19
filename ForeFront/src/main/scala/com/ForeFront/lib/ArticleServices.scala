package com.ForeFront
package lib

import net.liftweb.http.rest.RestHelper
import net.liftweb.json.Extraction
import com.mongodb.casbah.MongoConnection
import com.Mp3Digger.Repository.{PostFilePart, Post, PostRepository, MongoPostRepository}

object ArticleServices extends RestHelper {
  case class PostInfo(subject: String,
                      poster: String,
                      fileName: String) {
    def toXml = <post subject={subject}
                      poster={poster}
                      filename={fileName}/>

    def toJson = Extraction.decompose(this)
  }

  // a JSON-able class that holds all the users
  case class TopArticles(posts: List[PostInfo]) {
    def toJson = Extraction.decompose(this)

    def toXml = <posts>
      {posts.map(_.toXml)}
    </posts>
  }

  // define a REST handler for an XML request
  serve {
    case "articles" :: "articles" :: _ XmlGet _ =>
      TopArticles(MongoHack.repository.findSomeShit()).toXml
  }

  // define a REST handler for a JSON reqest
  serve {
    case "articles" :: "articles" :: _ JsonGet _ =>
      TopArticles(MongoHack.repository.findSomeShit()).toJson
  }

  // a couple of helpful conversion rules
  implicit def postToInfo(post: Post): PostInfo = {
    val (subject) = post.postFileParts.headOption match {
      case (Some(x: PostFilePart)) => {
       ( x.title)
      }
      case None => ""
    }
    PostInfo(subject, post.poster, post.fileName)
  }

  implicit def postListToInfo(articleList: List[Post]): List[PostInfo] = {
    articleList.map(postToInfo)
  }

}

object MongoHack {
  val con = MongoConnection("localhost")
  val db = con("test_Mp3Usenet")
  var repository: PostRepository = new MongoPostRepository(db)
}


