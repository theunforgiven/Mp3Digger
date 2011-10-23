package com.Mp3Digger.Parsing

import collection.immutable.Map
import com.Mp3Digger.Repository.{PostFilePart, Post}

class ArticleAssembler {

  def parseSinglePartPost(articleDto: ArticleDto): Post =  {
    val filePart = new PostFilePart(articleDto.articleSubject, articleDto.filePart, articleDto.articleId, articleDto.articleNumber)
    new Post(None, articleDto.articlePoster, articleDto.fileName, articleDto.totalFileParts, List(filePart))
  }

  def parseSinglePartPosts(articles: Map[String, List[ArticleDto]]) : List[Post] = {
    articles.values.flatMap(x => x).map(x => parseSinglePartPost(x)).toList
  }

  def parseMultiPartPost(poster: String, fileName: String, postParts: List[ArticleDto]): Post = {
    val totalPostCount = postParts.head.totalFileParts
    val fileList = postParts.map((postPart) => {
      articleDtoToPostFilePart(postPart)
    }).toList
    new Post(None, poster, fileName, totalPostCount, fileList)
  }

  def parseMultiPartPosts(articles: Map[String, List[ArticleDto]]) : List[Post] = {
    articles.flatMap(postGroup => {
      val fileGroup = postGroup._2.groupBy(x => x.fileName)
      fileGroup.map((fileGroup) => parseMultiPartPost(postGroup._1, fileGroup._1, fileGroup._2))
    }).toList
  }

  def articleDtoToPostFilePart(articleDto: ArticleDto) : PostFilePart = {
    new PostFilePart(articleDto.articleSubject, articleDto.filePart, articleDto.articleId, articleDto.articleNumber)
  }

  def assembleArticles(articles: List[ArticleDto]): List[Post] = {
    val (singlePost, multiPost) = articles.groupBy(x => x.articlePoster).partition((x) => x._2.length < 2)
    parseSinglePartPosts(singlePost) ++ parseMultiPartPosts(multiPost)
  }
}