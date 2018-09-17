package com.gmail.tt195361;

import org.junit.Test;

public class RegexParserTest {

	@Test
	public void testAny() {
		checkParse(
				".",
				ElementEnumerator.makeForUnitTest(
						new AnyCharElement()),
				"'.' -> �C�ӂ� 1 ���� ");
	}

	@Test
	public void testStartOfString() {
		checkParse(
				"^a",
				ElementEnumerator.makeForUnitTest(
						new StartOfStringElement(),
						new OneCharElement('a')),
				"�擪�� '^' -> �s�� ");
		checkParse(
				"a^",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('a'),
						new OneCharElement('^')),
				"�擪�ȊO�� '^' -> ���� '^'");
	}

	@Test
	public void testEndOfString() {
		checkParse(
				"a$",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('a'),
						new EndOfStringElement()),
				"�Ō�� '$' -> �s�� ");
		checkParse(
				"$a",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('$'),
						new OneCharElement('a')),
				"�Ō�ȊO�� '$' -> ���� '$'");
	}

	@Test
	public void testClosure() {
		checkParse(
				"a*",
				ElementEnumerator.makeForUnitTest(
						new ClosureElement(
								new OneCharElement('a'))),
				"2 �Ԗڈȍ~�� '*' -> �� ");
		checkParse(
				"*a",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('*'),
						new OneCharElement('a')),
				"�ŏ��� '*' -> ���� '*'");
		checkParse(
				"a**",
				ElementEnumerator.makeForUnitTest(
						new ClosureElement(
								new ClosureElement(
										new OneCharElement('a')))),
				"'*' �������Ă��悢 ");
	}

	@Test
	public void testOneChar() {
		checkParse(
				"a",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('a')),
				"�G�X�P�[�v����Ă��Ȃ� -> ���̕���");
		checkParse(
				"\\.",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('.')),
				"�G�X�P�[�v����Ă��� -> ���̎��̕���");
		checkParse(
				"\\",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('\\')),
				"�G�X�P�[�v���Ō�̕��� -> �G�X�P�[�v");
	}
	
	@Test
	public void testCharClass() {
		checkParse(
				"[abc]",
				ElementEnumerator.makeForUnitTest(
						new CharClassElement(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"�������w��");
		checkParse(
				"[a-c]",
				ElementEnumerator.makeForUnitTest(
						new CharClassElement(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"�����͈͂��w��");
		checkParse(
				"[",
				ElementEnumerator.makeForUnitTest(
						new OneCharElement('[')),
				"'[' ���Ō�̕��� -> ���� '[' ");
	}

	private void checkParse(String pattern, ElementEnumerator expected, String message) {
		ElementEnumerator actual = RegexParser.parse(pattern);
		ElementEnumeratorTest.check(expected, actual, message);
	}
}
