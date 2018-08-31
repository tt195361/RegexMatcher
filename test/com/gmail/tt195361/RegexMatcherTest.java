package com.gmail.tt195361;

import static org.junit.Assert.*;

import org.junit.Test;
import org.omg.CosNaming.IstringHelper;

public class RegexMatcherTest {

	@Test
	public void testOneChar() {
		ElementBuffer elemBuffer = new ElementBuffer(
				new OneCharElement('a'));
		check(elemBuffer, "a", true, "指定の文字ならマッチする");
		check(elemBuffer, "b", false, "指定以外はマッチしない");
	}
	
	@Test
	public void testAnyChar() {
		ElementBuffer elemBuffer = new ElementBuffer(
				new AnyCharElement());
		check(elemBuffer, "a", true, "どんな文字でもマッチする");
		check(elemBuffer, "ｚ", true, "どんな文字でもマッチする");
	}
	
	@Test
	public void testClosure() {
		ElementBuffer elemBuffer = new ElementBuffer(
				new ClosureElement(new AnyCharElement()),
				new OneCharElement('b'));
		check(elemBuffer, "ababa", true, ".*b にマッチする");
	}
	
	private void check(
			ElementBuffer elemBuffer, String str, boolean expected, String message) {
		StringBuffer strBuffer = new StringBuffer(str);
		boolean actual = RegexMatcher.match(elemBuffer, strBuffer);
		assertEquals(message, expected, actual);
	}
}
