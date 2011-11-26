package com.ForeFront.Service

import xml.{NodeSeq, Group}
import com.Mp3Digger.Repository.{PostFilePart, Post}
import collection.immutable.List
import com.ForeFront.Model.FilePost

class ArticleService {
  def findSubject(post: Post): String = {
    post.subject
  }

  def viewArticles(skip: Int, limit: Int): List[FilePost] = {
    MongoHack.getSomeShit(skip, limit).toList
  }
}