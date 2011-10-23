package com.Mp3Digger.Repository

case class Post(id: Option[String], poster: String, fileName: String, totalFilePartCount: Int, postFileParts: List[PostFilePart] = List[PostFilePart]()) {

}

case class PostFilePart(title: String, filePart: Int, articleId: String, articleNumber: Long){

}

//    val post = getPreviousPost(article) match {
//      case Some(x) => {
//        val fileParts = postFilePart :: x.postFileParts
//        val filePartCount = fileParts.length
//        x.copy(postFileParts = fileParts, totalFilePartCount =  filePartCount)
//      }
//      case None => {
//        val p = new Post(None, "poster", fileName, 1, List(postFilePart))
//        p.copy(postFileParts = (postFilePart :: p.postFileParts))
//      }
//    }
//
//    savePost(post)