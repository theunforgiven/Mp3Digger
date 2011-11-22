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
                subject: String,
                @(JSONTypeHint @field)(value = classOf[PostFilePart]) postFileParts: List[PostFilePart]) {
	private def this() = this(null, null, null, 0, null, null)

}

@BeanInfo
case class PostFilePart(filePart: Int, articleId: String, articleNumber: Long) {
	private def this() = this(0, null, 0)
}

object PostImports {

	implicit val PostFormat        : Format[Post]         = asProduct6("id", "poster", "fileName", "totalFilePartCount", "subject", "postFileParts")(Post)(Post.unapply(_).get)
	implicit val PostFilePartFormat: Format[PostFilePart] = asProduct3("filePart", "articleId", "articleNumber")(PostFilePart)(PostFilePart.unapply(_).get)
}