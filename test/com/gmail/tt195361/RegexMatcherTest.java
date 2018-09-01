package com.gmail.tt195361;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegexMatcherTest {
	
	private final String DontCare = null;
	
	@Test
	public void testMatch() {
		RegexElement[] elements = {
			new OneCharElement('b'),
			new ClosureElement(new AnyCharElement()),
			new OneCharElement('b')
		};
		
		check(elements, "ababa", true, "bab", "'ababa' と マッチする");
		check(elements, "abaca", false, DontCare, "'abaca' はマッチしない");
	}
	
	private void check(
			RegexElement[] elements, String str,
			boolean expectedResult, String expectedMatchString, String message) {
		ElementBuffer elemBuffer = new ElementBuffer(elements);
		RegexMatcher matcher = new RegexMatcher(elemBuffer);

		boolean actualResult = matcher.match(str);
		assertEquals(message, expectedResult, actualResult);

		String actualMatchString = matcher.getMatchString();
		assertEquals(message, expectedMatchString, actualMatchString);
	}
}
