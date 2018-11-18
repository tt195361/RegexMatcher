package com.gmail.tt195361.Regex;

import java.util.HashSet;
import static org.junit.Assert.*;
import org.junit.Test;

public class ClosurePatternTest {

	private final String DontCare = null;
	
	@Test
	public void repeatAnyChar() {
		AnyCharPattern anyCharPat = new AnyCharPattern();
		
		checkRepeatPattern(
				anyCharPat, "", 0, true, "",
				"'.*' �͂ǂ�ȕ�����ɂ���v����A�󕶎���̏ꍇ");
		checkRepeatPattern(
				anyCharPat, "abc", 0, true, "abc",
				"'.*' �͂ǂ�ȕ�����ɂ���v����A��łȂ�������̏ꍇ");
	}
	
	@Test
	public void repeatCharClass() {
		HashSet<Character> charSet = TestUtils.makeHashSet('a', 'b', 'c');
		CharClassPattern charClassPat = new CharClassPattern(false, charSet);

		checkRepeatPattern(
				charClassPat, "xyz", 0, true, "",
				"'[abc]*' �͋󕶎���ɂ���v����");
		checkRepeatPattern(
				charClassPat, "abcdef", 0, true, "abc",
				"'[abc]*' �́A'a', 'b', 'c' �� 0 ��ȏ�̌J��Ԃ��ƈ�v����");
	}
	
	@Test
	public void repeatClosure() {
		OneCharPattern oneCharPat = new OneCharPattern('a');
		ClosurePattern closurePat = new ClosurePattern(oneCharPat);

		checkRepeatPattern(
				closurePat, "abc", 0, true, "a",
				"'a**' �� 'a' �� 0 ��ȏ�̌J��Ԃ��ƈ�v����B'*' �� 2 �ȏ㏑���Ă� 1 �Ɠ���");
		checkRepeatPattern(
				closurePat, "xyz", 0, true, "",
				"'a**' �͋󕶎���ɂ���v����");
	}
	
	@Test
	public void repeatEndOfString() {
		EndOfStringPattern endOfStringPat = new EndOfStringPattern();

		checkRepeatPattern(
				endOfStringPat, "", 0, true, "",
				"'$*' �� ������̍Ō�ƈ�v����B '$' �ɑ����� '*' �������Ă��Ӗ��͂Ȃ�");
		checkRepeatPattern(
				endOfStringPat, "abc", 0, true, "",
				"'$*' �͕�����̂ǂ��ł� 0 ��ȏ�̌J��Ԃ��ŋ󕶎��ƈ�v����");
	}

	@Test
	public void repeatOneChar() {
		OneCharPattern oneCharPat = new OneCharPattern('a');

		checkRepeatPattern(
				oneCharPat, "aaabc", 0, true, "aaa",
				"'a*' �� 'a' �� 0 ��ȏ�̌J��Ԃ��ƈ�v����");
		checkRepeatPattern(
				oneCharPat, "def", 0, true, "",
				"'a*' �͋󕶎��ƈ�v����");
	}

	@Test
	public void repeatStartOfString() {
		StartOfStringPattern startOfStringPat = new StartOfStringPattern();

		checkRepeatPattern(
				startOfStringPat, "abc", 0, true, "",
				"'^*' �� ������̍ŏ��ƈ�v����B'^' �ɑ����� '*' �������Ă��Ӗ��͂Ȃ�");
		checkRepeatPattern(
				startOfStringPat, "abc", 1, true, "",
				"'^*' �͕�����̍ŏ��łȂ��Ă� 0 ��ȏ�̌J��Ԃ��ŋ󕶎��ƈ�v����");
	}
	
	private void checkRepeatPattern(
			RegexPattern repeatPat, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosurePattern closurePat = new ClosurePattern(repeatPat);
		PatternEnumerator patEnum = PatternEnumerator.makeForUnitTest(closurePat);
		checkOneMatch(
				patEnum, str, startIndex, expectedResult, expectedSubstring, message);
	}
	
	@Test
	public void matchTheRest() {
		AnyCharPattern anyCharPat = new AnyCharPattern();
		ClosurePattern closurePat = new ClosurePattern(anyCharPat);
		EndOfStringPattern endOfStringPat = new EndOfStringPattern();
		OneCharPattern oneCharPatB = new OneCharPattern('b');
		OneCharPattern oneCharPatA = new OneCharPattern('a');
		OneCharPattern oneCharPatD = new OneCharPattern('d');
		
		checkMatchTheRest(
				closurePat, endOfStringPat, "abc", true, "abc",
				"'.*$' �� '.*' �� �Œ��̌�� 'abc' �ƈ�v����");
		checkMatchTheRest(
				closurePat, oneCharPatB, "abc", true, "ab",
				"'.*b' �� '.*' �� 1 �����̌�� 'a' �ƈ�v����");
		checkMatchTheRest(
				closurePat, oneCharPatA, "abc", true, "a",
				"'.*a' �� '.*' �� �󕶎���̌��ƈ�v����");
		checkMatchTheRest(
				closurePat, oneCharPatD, "abc", false, DontCare,
				"'.*d' �� '.*' �͂ǂ̌��Ƃ���v���Ȃ�");
	}
	
	private void checkMatchTheRest(
			ClosurePattern closurePat, RegexPattern followingPat, String str,
			boolean expectedResult, String expectedSubstring, String message) {
		PatternEnumerator patEnum =
				PatternEnumerator.makeForUnitTest(closurePat, followingPat);
		checkOneMatch(
				patEnum, str, 0, expectedResult, expectedSubstring, message);
	}
	
	private void checkOneMatch(
			PatternEnumerator patEnum, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosurePattern closurePat = (ClosurePattern)patEnum.getCurrent();
		StringEnumerator strEnum = new StringEnumerator(str, startIndex);
		
		boolean actualResult = closurePat.oneMatch(patEnum, strEnum);
		assertEquals("Result: " + message, expectedResult, actualResult);
		
		if (expectedResult) {
			String actualSubstring = strEnum.getSubstring();
			assertEquals("Substring: " + message, expectedSubstring, actualSubstring);
		}
	}
	
	static void check(
			ClosurePattern expected, ClosurePattern actual, String message) {
		RegexPatternTest.check(
				expected.getRepeatPattern(), actual.getRepeatPattern(), message);
	}
}
