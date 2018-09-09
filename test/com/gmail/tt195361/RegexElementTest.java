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
			checkClosureElement((ClosureElement)expected, (ClosureElement)actual, message);
		}
		else if (expected instanceof OneCharElement) {
			checkOneCharElement((OneCharElement)expected, (OneCharElement)actual, message);
		}
		else if (expected instanceof CharClassElement) {
			checkCharClassElement((CharClassElement)expected, (CharClassElement) actual, message);
		}
		else {
			fail("���m�� RegexElement �̔h���^�ł�");
		}
	}
	
	private static void checkClosureElement(
			ClosureElement expected, ClosureElement actual, String message) {
		check(expected.getRegexElement(), actual.getRegexElement(), message);
	}
	
	private static void checkOneCharElement(
			OneCharElement expected, OneCharElement actual, String message) {
		assertEquals(
				"expectedChar: " + message,
				expected.getExpectedChar(), actual.getExpectedChar());
	}
	
	static void checkCharClassElement(
			CharClassElement expected, CharClassElement actual, String message) {
		assertEquals(
				"notContained: " + message,
				expected.getNotContained(), actual.getNotContained());
		TestUtils.checkHashSet(
				"charSet: + message",
				expected.getCharSet(), actual.getCharSet());
	}
}