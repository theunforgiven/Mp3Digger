package com.Mp3Digger.Parsing

import util.matching.Regex
import util.matching.Regex.{MatchIterator, Match}

class SubjectParser {
	val postFileNameMatcher  = """"(.*\.\w{3,4})"""".r
	val postFileNameMatcher2 = "(.*)\\s+\\(\\d+/\\d+\\)".r
	val postPartMatcher      = """[\(|\[](\d{1,5})/(\d{1,5})[\)|\]]""".r

	def executeFileNameRegex(regex: Regex = postFileNameMatcher, title: String): Option[String] = {
		val matches = regex.findAllIn(title).matchData.toList

		matches.lastOption match {
			case Some(x) => {
				Option(x.subgroups.lastOption.getOrElse("").replaceAll("\"", ""))
			}
			case None    => {
				Option.empty
			}
		}
	}

	def parseFileName(title: String): String = {
		val firstRegex = executeFileNameRegex(postFileNameMatcher, title)
		val secondRegex = executeFileNameRegex(postFileNameMatcher2, title)
		firstRegex.orElse(secondRegex).getOrElse("")
	}

	def parseFilePart(title: String): (Int, Int) = {
		val matches = postPartMatcher.findAllIn(title).matchData
		val stringMatches = matches.toList.map(x => x.force.subgroups)
		val postParts = stringMatches.lastOption;
		postParts match {
			case None                              => {
				(0, 0)
			}
			case Some(postPartMatch: List[String]) => {
				(postPartMatch.head.toInt, postPartMatch.last.toInt)
			}
		}
	}

	def parsePostPart(title: String): (Int, Int) = {
		val matches = postPartMatcher.findAllIn(title).matchData
		val stringMatches = matches.toList.map(x => x.force.subgroups)
		stringMatches.headOption match {
			case Some(x) if stringMatches.size > 1 => {
				(x.head.toInt, x.last.toInt)
			}
			case None => {
				return (0,0)
			}
			case _    => {
				return (0, 0)
			}
		}
	}

	def parsePostTitle(title: String): String = {
		val matches = postPartMatcher.findFirstMatchIn(title)
		matches match {
			case None                    => {
				""
			}
			case Some(titleMatch: Match) => {
				title.take(titleMatch.start)
			}
		}
	}

}