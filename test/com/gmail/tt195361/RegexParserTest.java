package com.gmail.tt195361;

import org.junit.Test;

public class RegexParserTest {

	@Test
	public void testAny() {
		checkParse(
				".",
				ElementEnumerator.makeForUnitTest(
						new AnyCharElement()),
				"'.' -> 任意の 1 文字 ");
	}

	@Test
	public void testStartOfString() {
		checkParse(
				"^a",
				ElementEnumerator.makeForUnitTest(
						new StartOfStringElement(),
						new OneCharElement('a')),
				"先頭の '^' -> 行頭 ");
		checkParse(
				"a^",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('a'),
						new OneCharElement('^')),
				"先頭以外の '^' -> 文字 '^'");
	}

	@Test
	public void testEndOfString() {
		checkParse(
				"a$",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('a'),
						new EndOfStringElement()),
				"最後の '$' -> 行末 ");
		checkParse(
				"$a",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('$'),
						new OneCharElement('a')),
				"最後以外の '$' -> 文字 '$'");
	}

	@Test
	public void testClosure() {
		checkParse(
				"a*",
				ElementEnumerator.makeForUnitTest(
						new ClosureElement(
								new OneCharElement('a'))),
				"2 番目以降の '*' -> 閉包 ");
		checkParse(
				"*a",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('*'),
						new OneCharElement('a')),
				"最初の '*' -> 文字 '*'");
		checkParse(
				"a**",
				ElementEnumerator.makeForUnitTest(
						new ClosureElement(
								new ClosureElement(
										new OneCharElement('a')))),
				"'*' が続いてもよい ");
	}

	@Test
	public void testOneChar() {
		checkParse(
				"a",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('a')),
				"エスケープされていない -> その文字");
		checkParse(
				"\\.",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('.')),
				"エスケープされている -> その次の文字");
		checkParse(
				"\\",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('\\')),
				"エスケープが最後の文字 -> エスケープ");
	}
	
	@Test
	public void testCharClass() {
		checkParse(
				"[abc]",
				ElementEnumerator.makeForUnitTest(
						new CharClassElement(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"文字を指定");
		checkParse(
				"[a-c]",
				ElementEnumerator.makeForUnitTest(
						new CharClassElement(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"文字範囲を指定");
		checkParse(
				"[",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('[')),
				"'[' が最後の文字 -> 文字 '[' ");
	}

	private void checkParse(String pattern, ElementEnumerator expected, String message) {
		ElementEnumerator actual = RegexParser.parse(pattern);
		ElementEnumeratorTest.check(expected, actual, message);
	}
}
