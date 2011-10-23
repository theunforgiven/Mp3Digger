package com.Mp3Digger.Main.Test

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.Mp3Digger.Parsing.SubjectParser


class SubjectParserTest extends FunSuite with ShouldMatchers{
  //
  val subjectParser = new SubjectParser()

  test("Can parse file part number out of subject") {
    val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
    subjectParser.parseFilePart(title) should be((10, 36))
  }

  test("Can parse post part number out of subject") {
    val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
    subjectParser.parsePostPart(title) should be((143, 211))
  }

  test("Can parse post title out of subject") {
    val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
    subjectParser.parsePostTitle(title) should be("[United-Forums.co.uk] Complete Top40 1981 Repost ")
  }

  test("Can parse file name out of subject") {
    val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
    subjectParser.parseFileName(title) should be("Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar")
  }
}