package com.gmail.tt195361;

import static org.junit.Assert.*;

class ElementBufferTest {
	static void check(ElementBuffer expected, ElementBuffer actual, String message) {
		while (expected.hasCurrent() && actual.hasCurrent()) {
			RegexElement expectedElement = expected.getCurrent();
			RegexElement actualElement = actual.getCurrent();
			
			TestUtils.areSameClass(expectedElement, actualElement, message);
			RegexElementTest.check(expectedElement, actualElement, message);
			
			expected.moveNext();
			actual.moveNext();
		}
		
		assertFalse("expected に要素がないはず: " + message, expected.hasCurrent());
		assertFalse("actual に要素がないはず:" + message, actual.hasCurrent());
	}
}
