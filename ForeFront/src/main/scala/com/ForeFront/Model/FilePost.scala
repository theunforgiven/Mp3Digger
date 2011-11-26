package com.ForeFront.Model

import com.Mp3Digger.Repository.Post

case class FilePost(fileName: String, poster: String, subject: String, haveParts: String, totalParts: String) {

}

object FilePost {
  implicit def postToFilePost(post: Post): FilePost = {
    FilePost(post.fileName, post.poster, post.subject, post.postFileParts.size.toString, post.totalFilePartCount.toString)
  }

  implicit def postListToFilePostList(post: List[Post]): List[FilePost] = {
    post.map(postToFilePost(_))
  }
}