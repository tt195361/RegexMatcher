package com.gmail.tt195361.Regex;

import org.junit.Test;

public class RegexParserTest {

	@Test
	public void testAny() {
		checkParse(
				".",
				PatternEnumerator.makeForUnitTest(
						new AnyCharPattern()),
				"'.' -> 任意の 1 文字 ");
	}

	@Test
	public void testStartOfString() {
		checkParse(
				"^a",
				PatternEnumerator.makeForUnitTest(
						new StartOfStringPattern(),
						new OneCharPattern('a')),
				"最初の '^' -> 文字列の最初 ");
		checkParse(
				"a^",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('a'),
						new OneCharPattern('^')),
				"最初以外の '^' -> 文字 '^'");
	}

	@Test
	public void testEndOfString() {
		checkParse(
				"a$",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('a'),
						new EndOfStringPattern()),
				"最後の '$' -> 行末 ");
		checkParse(
				"$a",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('$'),
						new OneCharPattern('a')),
				"最後以外の '$' -> 文字 '$'");
	}

	@Test
	public void testClosure() {
		checkParse(
				"a*",
				PatternEnumerator.makeForUnitTest(
						new ClosurePattern(
								new OneCharPattern('a'))),
				"2 番目以降の '*' -> 閉包 ");
		checkParse(
				"*a",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('*'),
						new OneCharPattern('a')),
				"最初の '*' -> 文字 '*'");
		checkParse(
				"a**",
				PatternEnumerator.makeForUnitTest(
						new ClosurePattern(
								new ClosurePattern(
										new OneCharPattern('a')))),
				"'*' が続いてもよい ");
	}

	@Test
	public void testOneChar() {
		checkParse(
				"a",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('a')),
				"エスケープされていない -> その文字");
		checkParse(
				"\\.",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('.')),
				"エスケープされている -> その次の文字");
		checkParse(
				"\\",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('\\')),
				"エスケープが最後の文字 -> エスケープ");
	}
	
	@Test
	public void testCharClass() {
		checkParse(
				"[abc]",
				PatternEnumerator.makeForUnitTest(
						new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"文字を指定");
		checkParse(
				"[a-c]",
				PatternEnumerator.makeForUnitTest(
						new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"文字範囲を指定");
		checkParse(
				"[",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('[')),
				"'[' が最後の文字 -> 文字 '[' ");
	}

	private void checkParse(String pattern, PatternEnumerator expected, String message) {
		PatternEnumerator actual = RegexParser.parse(pattern);
		PatternEnumeratorTest.check(expected, actual, message);
	}
}
