package com.gmail.tt195361;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OneCharElementTest {

	@Test
	public void oneMatch() {
		checkOneMatch(
				'\0', "", false, 0,
				"現在位置に文字がない -> 一致せず、現在位置はそのまま");
		checkOneMatch(
				'a', "a", true, 1,
				"現在位置に文字があり、それが指定の文字 -> 一致し、現在位置は次の文字に移動する");
		checkOneMatch(
				'a', "b", false, 0,
				"現在位置に文字があるが、指定の文字でない -> 一致せず、現在位置はそのまま");
	}

	private void checkOneMatch(
			char ch, String str,
			boolean expectedResult, int expectedCurrentIndex, String message) {
		OneCharElement oneCharElem = new OneCharElement(ch);
		RegexElementTest.checkOneMatch(
				oneCharElem, str, expectedResult, expectedCurrentIndex, message);
	}
	
	static void check(
			OneCharElement expected, OneCharElement actual, String message) {
		assertEquals(
				"specifiedChar: " + message,
				expected.getSpecifiedChar(), actual.getSpecifiedChar());
	}
}
