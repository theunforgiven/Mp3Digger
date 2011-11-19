package com.ForeFront
package snippet

import xml.{NodeSeq, Group}
import com.Mp3Digger.Repository.{PostFilePart, Post}
import collection.immutable.List
import lib.MongoHack


object ArticleUtil {

  def findSubject(post: Post): String = {
      post.postFileParts.headOption match {
      case (Some(x: PostFilePart)) => {
       ( x.title)
      }
      case None => ""
    }
  }

  def viewArticles(): NodeSeq = {
    val files = MongoHack.repository.findSomeShit()

    val rows: List[NodeSeq] = files.map(x => tableRow(x.fileName, x.poster, findSubject(x)))
    table(List(tableRow()) ::: rows)
  }

  def tableRow(fileName: String = "File Name", poster: String = "Poster", subject: String = "Subject"): NodeSeq =
  {
    <tr>
      <td>{fileName}</td>
      <td>{poster}</td>
      <td>{subject}</td>
    </tr>
  }

  def table(tableRows: List[NodeSeq]): NodeSeq =
  {
    <table border="1">
      {tableRows}
    </table>
  }
}
