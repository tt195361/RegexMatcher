package com.gmail.tt195361;

import static org.junit.Assert.*;

class RegexElementTest {
	
	static void check(
			RegexElement expected, RegexElement actual, String message) {
		if (expected instanceof AnyCharElement) {
			// �`�F�b�N������e�Ȃ�
		}
		else if (expected instanceof StartOfStringElement) {
			// �`�F�b�N������e�Ȃ�
		}
		else if (expected instanceof EndOfStringElement) {
			// �`�F�b�N������e�Ȃ�
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
			fail("���m�� RegexElement �̔h���^�ł�");
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
