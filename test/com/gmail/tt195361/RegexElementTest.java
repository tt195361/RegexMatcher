package com.gmail.tt195361;

import static org.junit.Assert.*;

class RegexElementTest {
	
	static void check(
			RegexElement expected, RegexElement actual, String message) {
		if (expected instanceof AnyCharElement) {
			// チェックする内容なし
		}
		else if (expected instanceof StartOfStringElement) {
			// チェックする内容なし
		}
		else if (expected instanceof EndOfStringElement) {
			// チェックする内容なし
		}
		else if (expected instanceof ClosureElement) {
			ClosureElementTest.check(
					(ClosureElement)expected, (ClosureElement)actual, message);
		}
		else if (expected instanceof OneCharElement) {
			OneCharElementTest.check(
					(OneCharElement)expected, (OneCharElement)actual, message);
		}
		else if (expected instanceof CharClassElement) {
			CharClassElementTest.check(
					(CharClassElement)expected, (CharClassElement) actual, message);
		}
		else {
			fail("未知の RegexElement の派生型です");
		}
	}
	
	static void checkOneMatch(
			RegexElement regexElem, String str,
			boolean expectedResult, int expectedCurrentIndex, String message) {
		ElementEnumerator elemEnum = ElementEnumerator.makeForUnitTest(regexElem);
		StringEnumerator strEnum = new StringEnumerator(str, 0);
		
		boolean actualResult = regexElem.oneMatch(elemEnum, strEnum);
		assertEquals("Result: " + message, expectedResult, actualResult);
		
		int actualCurrentIndex = strEnum.getCurrentIndex();
		assertEquals("CurrentIndex: " + message, expectedCurrentIndex, actualCurrentIndex);
	}
}
