package com.gmail.tt195361.Regex;

import org.junit.Test;

public class RegexParserTest {

	@Test
	public void testAny() {
		checkParse(
				".",
				PatternEnumerator.makeForUnitTest(
						new AnyCharPattern()),
				"'.' -> �C�ӂ� 1 ���� ");
	}

	@Test
	public void testStartOfString() {
		checkParse(
				"^a",
				PatternEnumerator.makeForUnitTest(
						new StartOfStringPattern(),
						new OneCharPattern('a')),
				"�ŏ��� '^' -> ������̍ŏ� ");
		checkParse(
				"a^",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('a'),
						new OneCharPattern('^')),
				"�ŏ��ȊO�� '^' -> ���� '^'");
	}

	@Test
	public void testEndOfString() {
		checkParse(
				"a$",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('a'),
						new EndOfStringPattern()),
				"�Ō�� '$' -> �s�� ");
		checkParse(
				"$a",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('$'),
						new OneCharPattern('a')),
				"�Ō�ȊO�� '$' -> ���� '$'");
	}

	@Test
	public void testClosure() {
		checkParse(
				"a*",
				PatternEnumerator.makeForUnitTest(
						new ClosurePattern(
								new OneCharPattern('a'))),
				"2 �Ԗڈȍ~�� '*' -> �� ");
		checkParse(
				"*a",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('*'),
						new OneCharPattern('a')),
				"�ŏ��� '*' -> ���� '*'");
		checkParse(
				"a**",
				PatternEnumerator.makeForUnitTest(
						new ClosurePattern(
								new ClosurePattern(
										new OneCharPattern('a')))),
				"'*' �������Ă��悢 ");
	}

	@Test
	public void testOneChar() {
		checkParse(
				"a",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('a')),
				"�G�X�P�[�v����Ă��Ȃ� -> ���̕���");
		checkParse(
				"\\.",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('.')),
				"�G�X�P�[�v����Ă��� -> ���̎��̕���");
		checkParse(
				"\\",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('\\')),
				"�G�X�P�[�v���Ō�̕��� -> �G�X�P�[�v");
	}
	
	@Test
	public void testCharClass() {
		checkParse(
				"[abc]",
				PatternEnumerator.makeForUnitTest(
						new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"�������w��");
		checkParse(
				"[a-c]",
				PatternEnumerator.makeForUnitTest(
						new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"�����͈͂��w��");
		checkParse(
				"[",
				PatternEnumerator.makeForUnitTest(
						new OneCharPattern('[')),
				"'[' ���Ō�̕��� -> ���� '[' ");
	}

	private void checkParse(String pattern, PatternEnumerator expected, String message) {
		PatternEnumerator actual = RegexParser.parse(pattern);
		PatternEnumeratorTest.check(expected, actual, message);
	}
}
