package com.ForeFront.Service

import xml.{NodeSeq, Group}
import com.Mp3Digger.Repository.{PostFilePart, Post}
import collection.immutable.List

class ArticleService {
  def findSubject(post: Post): String = {
    post.subject
  }

  def viewArticles(skip: Int,  limit: Int): NodeSeq = {
    val files = MongoHack.getSomeShit(skip, limit)

    val rows: List[NodeSeq] = files.map(x => tableRow(x.fileName, x.poster, findSubject(x),
    x.postFileParts.size.toString, x.totalFilePartCount.toString))
    table(List(tableRow()) ::: rows)
  }

  def tableRow(fileName: String = "File Name",
               poster: String = "Poster",
               subject: String = "Subject",
               haveParts: String = "Have",
               totalParts: String = "Total Parts"): NodeSeq = {
    <tr>
      <td>
        {fileName}
      </td>
      <td>
        {poster}
      </td>
      <td>
        {subject}
      </td>
      <td>
        {haveParts}
      </td>
      <td>
        {totalParts}
      </td>
    </tr>
  }

  def table(tableRows: List[NodeSeq]): NodeSeq = {
    <table border="1">
      {tableRows}
    </table>
  }
}
