package com.Mp3Digger.Repository

import sjson.json._
import DefaultProtocol._
import scala.annotation.target._
import scala.reflect._

@BeanInfo
case class Post(@(OptionTypeHint @field)(value = classOf[String]) id: Option[String],
                poster: String,
                fileName: String,
                totalFilePartCount: Int,
                @(JSONTypeHint @field)(value = classOf[PostFilePart]) postFileParts: List[PostFilePart]) {
	private def this() = this(null, null, null, 0, null)

}

@BeanInfo
case class PostFilePart(title: String, filePart: Int, articleId: String, articleNumber: Long) {
	private def this() = this(null, 0, null, 0)
}

object PostImports {

	implicit val PostFormat        : Format[Post]         = asProduct5("id", "poster", "fileName", "totalFilePartCount", "postFileParts")(Post)(Post.unapply(_).get)
	implicit val PostFilePartFormat: Format[PostFilePart] = asProduct4("title", "filePart", "articleId", "articleNumber")(PostFilePart)(PostFilePart.unapply(_).get)
}