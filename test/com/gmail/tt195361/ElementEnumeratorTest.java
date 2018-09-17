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
		
		assertFalse("expected ‚É—v‘f‚ª‚È‚¢‚Í‚¸: " + message, expected.hasCurrent());
		assertFalse("actual ‚É—v‘f‚ª‚È‚¢‚Í‚¸:" + message, actual.hasCurrent());
	}
}
