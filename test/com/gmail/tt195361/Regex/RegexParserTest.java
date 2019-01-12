package com.gmail.tt195361.Regex;

import org.junit.Test;

public class RegexParserTest {

	@Test
	public void testAny() {
		checkParse(
				".",
				PatternEnumeratorTest.make(
						new AnyCharPattern()),
				"'.' -> 任意の 1 文字 ");
	}

	@Test
	public void testStartOfString() {
		checkParse(
				"^a",
				PatternEnumeratorTest.make(
						new StartOfStringPattern(),
						new OneCharPattern('a')),
				"最初の '^' -> 文字列の最初 ");
		checkParse(
				"a^",
				PatternEnumeratorTest.make(
						new OneCharPattern('a'),
						new OneCharPattern('^')),
				"最初以外の '^' -> 文字 '^'");
	}

	@Test
	public void testEndOfString() {
		checkParse(
				"a$",
				PatternEnumeratorTest.make(
						new OneCharPattern('a'),
						new EndOfStringPattern()),
				"最後の '$' -> 行末 ");
		checkParse(
				"$a",
				PatternEnumeratorTest.make(
						new OneCharPattern('$'),
						new OneCharPattern('a')),
				"最後以外の '$' -> 文字 '$'");
	}

	@Test
	public void testClosure() {
		checkParse(
				"a*",
				PatternEnumeratorTest.make(
						new ClosurePattern(
								new OneCharPattern('a'))),
				"2 番目以降の '*' -> 閉包 ");
		checkParse(
				"*a",
				PatternEnumeratorTest.make(
						new OneCharPattern('*'),
						new OneCharPattern('a')),
				"最初の '*' -> 文字 '*'");
		checkParse(
				"a**",
				PatternEnumeratorTest.make(
						new ClosurePattern(
								new ClosurePattern(
										new OneCharPattern('a')))),
				"'*' が続いてもよい ");
	}

	@Test
	public void testOneChar() {
		checkParse(
				"a",
				PatternEnumeratorTest.make(
						new OneCharPattern('a')),
				"エスケープされていない -> その文字");
		checkParse(
				"\\.",
				PatternEnumeratorTest.make(
						new OneCharPattern('.')),
				"エスケープされている -> その次の文字");
		checkParse(
				"\\",
				PatternEnumeratorTest.make(
						new OneCharPattern('\\')),
				"エスケープが最後の文字 -> エスケープ");
	}
	
	@Test
	public void testCharClass() {
		checkParse(
				"[abc]",
				PatternEnumeratorTest.make(
						new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"文字を指定");
		checkParse(
				"[a-c]",
				PatternEnumeratorTest.make(
						new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"文字範囲を指定");
		checkParse(
				"[",
				PatternEnumeratorTest.make(
						new OneCharPattern('[')),
				"'[' が最後の文字 -> 文字 '[' ");
	}

	private void checkParse(String pattern, PatternEnumerator expected, String message) {
		PatternEnumerator actual = RegexParser.parse(pattern);
		PatternEnumeratorTest.check(expected, actual, message);
	}
}
