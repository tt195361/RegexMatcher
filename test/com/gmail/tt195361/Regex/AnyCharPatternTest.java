package com.gmail.tt195361.Regex;

import org.junit.Test;

public class AnyCharPatternTest {

	@Test
	public void oneMatch() {
		checkOneMatch(
				"", false, 0,
				"現在位置に文字がない -> 一致せず、現在位置はそのまま");
		checkOneMatch(
				"a", true, 1,
				"現在位置に文字がある -> 一致し、現在位置は次の文字に移動する");
	}

	private void checkOneMatch(
			String str, boolean expectedResult, int expectedCurrentIndex, String message) {
		AnyCharPattern anyCharPat = new AnyCharPattern();
		RegexPatternTest.checkOneMatch(
				anyCharPat, str, expectedResult, expectedCurrentIndex, message);
	}
}
