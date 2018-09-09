package com.gmail.tt195361;

import org.junit.Test;

public class RegexParserTest {

	@Test
	public void testAny() {
		checkParse(
				".",
				ElementBuffer.makeForUnitTest(
						new AnyCharElement()),
				"'.' -> �C�ӂ� 1 ���� ");
	}

	@Test
	public void testStartOfString() {
		checkParse(
				"^a",
				ElementBuffer.makeForUnitTest(
						new StartOfStringElement(),
						new OneCharElement('a')),
				"�擪�� '^' -> �s�� ");
		checkParse(
				"a^",
				ElementBuffer.makeForUnitTest(
						new OneCharElement('a'),
						new OneCharElement('^')),
				"�擪�ȊO�� '^' -> ���� '^'");
	}

	@Test
	public void testEndOfString() {
		checkParse(
				"a$",
				ElementBuffer.makeForUnitTest(
						new OneCharElement('a'),
						new EndOfStringElement()),
				"�Ō�� '$' -> �s�� ");
		checkParse(
				"$a",
				ElementBuffer.makeForUnitTest(
						new OneCharElement('$'),
						new OneCharElement('a')),
				"�Ō�ȊO�� '$' -> ���� '$'");
	}

	@Test
	public void testClosure() {
		checkParse(
				"a*",
				ElementBuffer.makeForUnitTest(
						new ClosureElement(
								new OneCharElement('a'))),
				"2 �Ԗڈȍ~�� '*' -> �� ");
		checkParse(
				"*a",
				ElementBuffer.makeForUnitTest(
						new OneCharElement('*'),
						new OneCharElement('a')),
				"�ŏ��� '*' -> ���� '*'");
		checkParse(
				"a**",
				ElementBuffer.makeForUnitTest(
						new ClosureElement(
								new ClosureElement(
										new OneCharElement('a')))),
				"'*' �������Ă��悢 ");
	}

	@Test
	public void testOneChar() {
		checkParse(
				"a",
				ElementBuffer.makeForUnitTest(
						new OneCharElement('a')),
				"�G�X�P�[�v����Ă��Ȃ� -> ���̕���");
		checkParse(
				"\\.",
				ElementBuffer.makeForUnitTest(
						new OneCharElement('.')),
				"�G�X�P�[�v����Ă��� -> ���̎��̕���");
		checkParse(
				"\\",
				ElementBuffer.makeForUnitTest(
						new OneCharElement('\\')),
				"�G�X�P�[�v���Ō�̕��� -> �G�X�P�[�v");
	}
	
	@Test
	public void testCharClass() {
		checkParse(
				"[abc]",
				ElementBuffer.makeForUnitTest(
						new CharClassElement(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"�������w��");
		checkParse(
				"[a-c]",
				ElementBuffer.makeForUnitTest(
						new CharClassElement(false, TestUtils.makeHashSet('a', 'b', 'c'))),
				"�����͈͂��w��");
		checkParse(
				"[",
				ElementBuffer.makeForUnitTest(
						new OneCharElement('[')),
				"'[' ���Ō�̕��� -> ���� '[' ");
	}

	private void checkParse(String pattern, ElementBuffer expected, String message) {
		ElementBuffer actual = RegexParser.parse(pattern);
		ElementBufferTest.check(expected, actual, message);
	}
}
