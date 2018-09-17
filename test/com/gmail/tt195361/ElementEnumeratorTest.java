package com.gmail.tt195361;

import static org.junit.Assert.*;

class ElementEnumeratorTest {
	static void check(ElementEnumerator expected, ElementEnumerator actual, String message) {
		while (expected.hasCurrent() && actual.hasCurrent()) {
			RegexElement expectedElement = expected.getCurrent();
			RegexElement actualElement = actual.getCurrent();
			
			TestUtils.areSameClass(expectedElement, actualElement, message);
			RegexElementTest.check(expectedElement, actualElement, message);
			
			expected.moveNext();
			actual.moveNext();
		}
		
		assertFalse("expected �ɗv�f���Ȃ��͂�: " + message, expected.hasCurrent());
		assertFalse("actual �ɗv�f���Ȃ��͂�:" + message, actual.hasCurrent());
	}
}
