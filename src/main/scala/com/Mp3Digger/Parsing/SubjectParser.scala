package com.Mp3Digger.Parsing

import util.matching.Regex
import util.matching.Regex.Match

class SubjectParser {
  val postFileNameMatcher = """"(.*\.\w{3,4})"""".r
  val postPartMatcher = """[\(|\[](\d{1,5})/(\d{1,5})[\)|\]]""".r

  def parseFileName(title: String) : String = {
    val matches = postFileNameMatcher.findAllIn(title).matchData.toList.lastOption
    matches match
    {
      case Some(m: Regex.Match) =>{
        m.matched.replaceAll("\"", "")
      }
      case None => {
        ""
      }
    }
  }

  def parsePostPart(title: String):(Int, Int) = {
    val matches = postPartMatcher.findAllIn(title).matchData
    val stringMatches = matches.toList.map(x => x.force.subgroups)
    val postParts = stringMatches.last;

    (postParts.head.toInt, postParts.last.toInt)
  }

  def parseFilePart(title: String):(Int, Int) = {
    val matches = postPartMatcher.findAllIn(title).matchData
    val stringMatches = matches.toList.map(x => x.force.subgroups)
    val postParts = stringMatches.head;

    (postParts.head.toInt, postParts.last.toInt)
  }

  def parsePostTitle(title: String): String = {
    val matches = postPartMatcher.findFirstMatchIn(title)
    matches match {
      case None =>{
        ""
      }
      case Some(titleMatch: Match) => {
        title.take(titleMatch.start)
      }
    }
  }

}