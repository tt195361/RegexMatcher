package com.gmail.tt195361.Regex;

import static org.junit.Assert.*;

class RegexPatternTest {
	
	static void check(
			RegexPattern expected, RegexPattern actual, String message) {
		if (expected instanceof AnyCharPattern) {
			// �`�F�b�N������e�Ȃ�
		}
		else if (expected instanceof StartOfStringPattern) {
			// �`�F�b�N������e�Ȃ�
		}
		else if (expected instanceof EndOfStringPattern) {
			// �`�F�b�N������e�Ȃ�
		}
		else if (expected instanceof ClosurePattern) {
			ClosurePatternTest.check(
					(ClosurePattern)expected, (ClosurePattern)actual, message);
		}
		else if (expected instanceof OneCharPattern) {
			OneCharPatternTest.check(
					(OneCharPattern)expected, (OneCharPattern)actual, message);
		}
		else if (expected instanceof CharClassPattern) {
			CharClassPatternTest.check(
					(CharClassPattern)expected, (CharClassPattern) actual, message);
		}
		else {
			fail("���m�� RegexPattern �̔h���^�ł�");
		}
	}
	
	static void checkOneMatch(
			RegexPattern regexPat, String str,
			boolean expectedResult, int expectedCurrentIndex, String message) {
		PatternEnumerator patEnum = PatternEnumeratorTest.make(regexPat);
		StringEnumerator strEnum = new StringEnumerator(str);
		
		boolean actualResult = regexPat.oneMatch(patEnum, strEnum);
		assertEquals("Result: " + message, expectedResult, actualResult);
		
		int actualCurrentIndex = strEnum.getCurrentIndex();
		assertEquals("CurrentIndex: " + message, expectedCurrentIndex, actualCurrentIndex);
	}
}
