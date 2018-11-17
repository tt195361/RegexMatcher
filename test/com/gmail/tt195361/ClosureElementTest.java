package com.gmail.tt195361;

import java.util.HashSet;
import static org.junit.Assert.*;
import org.junit.Test;

public class ClosureElementTest {

	private final String DontCare = null;
	
	@Test
	public void repeatAnyChar() {
		AnyCharElement anyCharElem = new AnyCharElement();
		
		checkRepeatElement(
				anyCharElem, "", 0, true, "",
				"'.*' �͂ǂ�ȕ�����ɂ���v����A�󕶎���̏ꍇ");
		checkRepeatElement(
				anyCharElem, "abc", 0, true, "abc",
				"'.*' �͂ǂ�ȕ�����ɂ���v����A��łȂ�������̏ꍇ");
	}
	
	@Test
	public void repeatCharClass() {
		HashSet<Character> charSet = TestUtils.makeHashSet('a', 'b', 'c');
		CharClassElement charClassElem = new CharClassElement(false, charSet);

		checkRepeatElement(
				charClassElem, "xyz", 0, true, "",
				"'[abc]*' �͋󕶎���ɂ���v����");
		checkRepeatElement(
				charClassElem, "abcdef", 0, true, "abc",
				"'[abc]*' �́A'a', 'b', 'c' �� 0 ��ȏ�̌J��Ԃ��ƈ�v����");
	}
	
	@Test
	public void repeatClosure() {
		OneCharElement oneCharElem = new OneCharElement('a');
		ClosureElement closureElem = new ClosureElement(oneCharElem);

		checkRepeatElement(
				closureElem, "abc", 0, true, "a",
				"'a**' �� 'a' �� 0 ��ȏ�̌J��Ԃ��ƈ�v����B'*' �� 2 �ȏ㏑���Ă� 1 �Ɠ���");
		checkRepeatElement(
				closureElem, "xyz", 0, true, "",
				"'a**' �͋󕶎���ɂ���v����");
	}
	
	@Test
	public void repeatEndOfString() {
		EndOfStringElement endOfStringElem = new EndOfStringElement();

		checkRepeatElement(
				endOfStringElem, "", 0, true, "",
				"'$*' �� ������̍Ō�ƈ�v����B '$' �ɑ����� '*' �������Ă��Ӗ��͂Ȃ�");
		checkRepeatElement(
				endOfStringElem, "abc", 0, true, "",
				"'$*' �͕�����̂ǂ��ł� 0 ��ȏ�̌J��Ԃ��ŋ󕶎��ƈ�v����");
	}

	@Test
	public void repeatOneChar() {
		OneCharElement oneCharElem = new OneCharElement('a');

		checkRepeatElement(
				oneCharElem, "aaabc", 0, true, "aaa",
				"'a*' �� 'a' �� 0 ��ȏ�̌J��Ԃ��ƈ�v����");
		checkRepeatElement(
				oneCharElem, "def", 0, true, "",
				"'a*' �͋󕶎��ƈ�v����");
	}

	@Test
	public void repeatStartOfString() {
		StartOfStringElement startOfStringElem = new StartOfStringElement();

		checkRepeatElement(
				startOfStringElem, "abc", 0, true, "",
				"'^*' �� ������̍ŏ��ƈ�v����B'^' �ɑ����� '*' �������Ă��Ӗ��͂Ȃ�");
		checkRepeatElement(
				startOfStringElem, "abc", 1, true, "",
				"'^*' �͕�����̍ŏ��łȂ��Ă� 0 ��ȏ�̌J��Ԃ��ŋ󕶎��ƈ�v����");
	}
	
	private void checkRepeatElement(
			RegexElement repeatElem, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosureElement closureElem = new ClosureElement(repeatElem);
		ElementEnumerator elemEnum = ElementEnumerator.makeForUnitTest(closureElem);
		checkOneMatch(
				elemEnum, str, startIndex, expectedResult, expectedSubstring, message);
	}
	
	@Test
	public void matchTheRest() {
		AnyCharElement anyChar = new AnyCharElement();
		ClosureElement closureElem = new ClosureElement(anyChar);
		EndOfStringElement endOfStringElem = new EndOfStringElement();
		OneCharElement oneCharB = new OneCharElement('b');
		OneCharElement oneCharA = new OneCharElement('a');
		OneCharElement oneCharD = new OneCharElement('d');
		
		checkMatchTheRest(
				closureElem, endOfStringElem, "abc", true, "abc",
				"'.*$' �� '.*' �� �Œ��̌�� 'abc' �ƈ�v����");
		checkMatchTheRest(
				closureElem, oneCharB, "abc", true, "ab",
				"'.*b' �� '.*' �� 1 �����̌�� 'a' �ƈ�v����");
		checkMatchTheRest(
				closureElem, oneCharA, "abc", true, "a",
				"'.*a' �� '.*' �� �󕶎���̌��ƈ�v����");
		checkMatchTheRest(
				closureElem, oneCharD, "abc", false, DontCare,
				"'.*d' �� '.*' �͂ǂ̌��Ƃ���v���Ȃ�");
	}
	
	private void checkMatchTheRest(
			ClosureElement closureElem, RegexElement followingElem, String str,
			boolean expectedResult, String expectedSubstring, String message) {
		ElementEnumerator elemEnum =
				ElementEnumerator.makeForUnitTest(closureElem, followingElem);
		checkOneMatch(
				elemEnum, str, 0, expectedResult, expectedSubstring, message);
	}
	
	private void checkOneMatch(
			ElementEnumerator elemEnum, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosureElement closureElem = (ClosureElement)elemEnum.getCurrent();
		StringEnumerator strEnum = new StringEnumerator(str, startIndex);
		
		boolean actualResult = closureElem.oneMatch(elemEnum, strEnum);
		assertEquals("Result: " + message, expectedResult, actualResult);
		
		if (expectedResult) {
			String actualSubstring = strEnum.getSubstring();
			assertEquals("Substring: " + message, expectedSubstring, actualSubstring);
		}
	}
	
	static void check(
			ClosureElement expected, ClosureElement actual, String message) {
		RegexElementTest.check(
				expected.getRepeatElement(), actual.getRepeatElement(), message);
	}
}
