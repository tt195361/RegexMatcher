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
			checkClosureElement((ClosureElement)expected, (ClosureElement)actual, message);
		}
		else if (expected instanceof OneCharElement) {
			checkOneCharElement((OneCharElement)expected, (OneCharElement)actual, message);
		}
		else if (expected instanceof CharClassElement) {
			checkCharClassElement((CharClassElement)expected, (CharClassElement) actual, message);
		}
		else {
			fail("未知の RegexElement の派生型です");
		}
	}
	
	private static void checkClosureElement(
			ClosureElement expected, ClosureElement actual, String message) {
		check(expected.getRepeatElement(), actual.getRepeatElement(), message);
	}
	
	private static void checkOneCharElement(
			OneCharElement expected, OneCharElement actual, String message) {
		assertEquals(
				"specifiedChar: " + message,
				expected.getSpecifiedChar(), actual.getSpecifiedChar());
	}
	
	static void checkCharClassElement(
			CharClassElement expected, CharClassElement actual, String message) {
		assertEquals(
				"notContains: " + message,
				expected.getNotContains(), actual.getNotContains());
		TestUtils.checkHashSet(
				"charSet: + message",
				expected.getCharSet(), actual.getCharSet());
	}
}
