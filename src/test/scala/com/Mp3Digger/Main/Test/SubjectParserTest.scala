package com.Mp3Digger.Main.Test

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import com.Mp3Digger.Parsing.SubjectParser


class SubjectParserTest extends FunSuite with ShouldMatchers {
	//
	val subjectParser = new SubjectParser()

	test("Can parse file part number out of subject")
	{
		val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
		subjectParser.parseFilePart(title) should be((143, 211))
	}

	test("Can parse post part number out of subject")
	{
		val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
		subjectParser.parsePostPart(title) should be((10, 36))
	}

	test("Can parse post title out of subject")
	{
		val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
		subjectParser.parsePostTitle(title) should be("[United-Forums.co.uk] Complete Top40 1981 Repost ")
	}


	test("Can parse file name out of subject")
	{
		val title = "[United-Forums.co.uk] Complete Top40 1981 Repost [10/36] - \"Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar\" yEnc (143/211)"
		subjectParser.parseFileName(title) should be("Complete Top40 1981 -WWW.UNITED-FORUMS.CO.UK-.part09.rar")
	}

	test("Can parse file part number out of subject2")
	{
		val title = "Walter Trout - Transition - (1972) 320 kbps incl. Cover posted by Turmfalke & rebelrider (04/21) \"03. Transition.mp3\" yEnc (16/33)"
		subjectParser.parseFilePart(title) should be((16, 33))
	}

	test("Can parse post part number out of subject2")
	{
		val title = "Walter Trout - Transition - (1972) 320 kbps incl. Cover posted by Turmfalke & rebelrider (04/21) \"03. Transition.mp3\" yEnc (16/33)"
		subjectParser.parsePostPart(title) should be((04, 21))
	}

	test("Can parse post title out of subject2")
	{
		val title = "Walter Trout - Transition - (1972) 320 kbps incl. Cover posted by Turmfalke & rebelrider (04/21) \"03. Transition.mp3\" yEnc (16/33)"
		subjectParser.parsePostTitle(title) should be("Walter Trout - Transition - (1972) 320 kbps incl. Cover posted by Turmfalke & rebelrider ")
	}

	test("Can parse file name out of subject2")
	{
		val title = "Walter Trout - Transition - (1972) 320 kbps incl. Cover posted by Turmfalke & rebelrider (04/21) \"03. Transition.mp3\" yEnc (16/33)"
		subjectParser.parseFileName(title) should be("03. Transition.mp3")
	}

	test("Can parse file name out of subject3")
	{
		val title = "WWW.UNITED-FORUMS.CO.UK - UKTOP40 SINGLES (31-08-08).PAR2 (1/1)"
		subjectParser.parseFileName(title) should be("WWW.UNITED-FORUMS.CO.UK - UKTOP40 SINGLES (31-08-08).PAR2")
	}
	test("Can parse file part number out of subject3")
	{
		val title = "WWW.UNITED-FORUMS.CO.UK - UKTOP40 SINGLES (31-08-08).PAR2 (1/1)"
		subjectParser.parseFilePart(title) should be((1, 1))
	}
	test("Can parse post part number out of subject3")
	{
		val title = "WWW.UNITED-FORUMS.CO.UK - UKTOP40 SINGLES (31-08-08).PAR2 (1/1)"
		subjectParser.parsePostPart(title) should be((0, 0))
	}
	test("Can parse file part number out of subject4")
	{
		val title = "Running Wild - Under Jolly Rodger (1987) 320 kbps incl. Cover posted by Turmfalke & rebelrider(05/17) \"04- Beggar's Night.mp3\" ) yEnc (2/33)"
		subjectParser.parseFilePart(title) should be((2, 33))
	}

	test("Can parse post part number out of subject4")
	{
		val title = "Running Wild - Under Jolly Rodger (1987) 320 kbps incl. Cover posted by Turmfalke & rebelrider(05/17) \"04- Beggar's Night.mp3\" ) yEnc (2/33)"
		subjectParser.parsePostPart(title) should be((5, 17))
	}
	//
	//  test("C")
}