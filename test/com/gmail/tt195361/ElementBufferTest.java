package com.gmail.tt195361;

import static org.junit.Assert.*;

class ElementBufferTest {
	static void check(ElementBuffer expected, ElementBuffer actual, String message) {
		while (expected.hasCurrent() && actual.hasCurrent()) {
			RegexElement expectedElement = expected.getCurrent();
			RegexElement actualElement = actual.getCurrent();
			
			checkClass(expectedElement, actualElement, message);
			checkElement(expectedElement, actualElement, message);
			
			expected.moveNext();
			actual.moveNext();
		}
		
		assertFalse("expected に要素がないはず: " + message, expected.hasCurrent());
		assertFalse("actual に要素がないはず:" + message, actual.hasCurrent());
	}
	
	private static void checkClass(
			RegexElement expected, RegexElement actual, String message) {
		Class<?> expectedClass = expected.getClass();
		Class<?> actualClass = actual.getClass();
		assertEquals("Class: " + message, expectedClass, actualClass);
	}
	
	private static void checkElement(
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
		else {
			fail("未知の RegexElement の派生型です");
		}
	}
	
	private static void checkClosureElement(
			ClosureElement expected, ClosureElement actual, String message) {
		checkElement(expected.getRegexElement(), actual.getRegexElement(), message);
	}
	
	private static void checkOneCharElement(
			OneCharElement expected, OneCharElement actual, String message) {
		assertEquals(
				"expectedChar: " + message,
				expected.getExpectedChar(), actual.getExpectedChar());
	}
}
