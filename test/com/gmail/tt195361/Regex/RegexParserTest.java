package com.gmail.tt195361.Regex;

import org.junit.Test;

public class RegexParserTest {

	@Test
	public void testAny() {
		checkParse(
				".",
				PatternEnumeratorTest.make(
						new AnyCharPattern()),
				"'.' -> �C�ӂ� 1 ���� ");
	}

	@Test
	public void testStartOfString() {
		checkParse(
				"^a",
				PatternEnumeratorTest.make(
						new StartOfStringPattern(),
						new OneCharPattern('a')),
				"�ŏ��� '^' -> ������̍ŏ� ");
		checkParse(
				"a^",
				PatternEnumeratorTest.make(
						new OneCharPattern('a'),
						new OneCharPattern('^')),
				"�ŏ��ȊO�� '^' -> ���� '^'");
	}

	@Test
	public void testEndOfString() {
		checkParse(
				"a$",
				PatternEnumeratorTest.make(
						new OneCharPattern('a'),
						new EndOfStringPattern()),
				"�Ō�� '$' -> �s�� ");
		checkParse(
				"$a",
				PatternEnumeratorTest.make(
						new OneCharPattern('$'),
						new OneCharPattern('a')),
				"�Ō�ȊO�� '$' -> ���� '$'");
	}

	@Test
	public void testClosure() {
		checkParse(
				"a*",
				PatternEnumeratorTest.make(
						new ClosurePattern(
								new OneCharPattern('a'))),
				"2 �Ԗڈȍ~�� '*' -> �� ");
		checkParse(
				"*a",
				PatternEnumeratorTest.make(
						new OneCharPattern('*'),
						new OneCharPattern('a')),
				"�ŏ��� '*' -> ���� '*'");
		checkParse(
				"a**",
				PatternEnumeratorTest.make(
						new ClosurePattern(
								new ClosurePattern(
										new OneCharPattern('a')))),
				"'*' �������Ă��悢 ");
	}

	@Test
	public void testOneChar() {
		checkParse(
				"a",
				PatternEnumeratorTest.make(
						new OneCharPattern('a')),
				"�G�X�P�[�v����Ă��Ȃ� -> ���̕���");
		checkParse(
				"\\.",
				PatternEnumeratorTest.make(
						new OneCharPattern('.')),
				"�G�X�P�[�v����Ă��� -> ���̎��̕���");
		checkParse(
				"\\",
				PatternEnumeratorTest.make(
						new OneCharPattern('\\')),
				"�G�X�P�[�v���Ō�̕��� -> �G�X�P�[�v");
	}
	
	@Test
	public void testCharClass() {
		checkParse(
				"[abc]",
				PatternEnumeratorTest.make(
						new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"�������w��");
		checkParse(
				"[a-c]",
				PatternEnumeratorTest.make(
						new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"�����͈͂��w��");
		checkParse(
				"[",
				PatternEnumeratorTest.make(
						new OneCharPattern('[')),
				"'[' ���Ō�̕��� -> ���� '[' ");
	}

	private void checkParse(String pattern, PatternEnumerator expected, String message) {
		PatternEnumerator actual = RegexParser.parse(pattern);
		PatternEnumeratorTest.check(expected, actual, message);
	}
}
