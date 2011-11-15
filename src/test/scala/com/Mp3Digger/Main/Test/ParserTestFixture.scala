package com.Mp3Digger.Main.Test

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import scalax.file.{PathMatcher, Path}
import java.lang.String
import util.parsing.combinator.RegexParsers
import util.matching.Regex
import scala.util.parsing.combinator.syntactical._

class ParserTestFixture extends FunSuite with ShouldMatchers with BeforeAndAfterEach {
	val projectRoot             = "/Users/cyrusinnovation/dev/personal/scala/Mp3Digger/"
	val testDataDirectory       = projectRoot + "TestData/"
	val testDataOutputDirectory = projectRoot + "TestOutput/"

	ignore("Pattern matching")
	{
		val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
		val pattern = """$soak[$postPart]"$fileName"$soak($filePart)"""

		val tokens = tokenize(pattern)
		tokens.size should be(5)
	}

	def tokenize(s: String): List[String] = {

		Nil
	}

	test("can parse")
	{
		SP.parseItem("""$soak1[$postPart]"$fileName"$soak2($filePart)""") match {
			case SP.Success(result, _) => println(result.toString)
			case _                                => fail("Could not parse the input string.")
		}

	}
}

object SP extends RegexParsers {
//	private def placeHolderIndicator = literal("$")
//	private def placeHolder = placeHolderIndicator ~ regex("""\w+""".r)

	private def placeHolder = regex("""\$\w+""".r)
	private def character = regex("""\w+""".r)
	private def item = ( placeHolder | character )
	def parseItem(str: String): ParseResult[String] =
	{
		parse(item, str)
	}
}


case class Token(literal: String) {}