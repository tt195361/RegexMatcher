package com.gmail.tt195361.Regex;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OneCharPatternTest {

	@Test
	public void oneMatch() {
		checkOneMatch(
				'\0', "", false, 0,
				"���݈ʒu�ɕ������Ȃ� -> ��v�����A���݈ʒu�͂��̂܂�");
		checkOneMatch(
				'a', "a", true, 1,
				"���݈ʒu�ɕ���������A���ꂪ�w��̕��� -> ��v���A���݈ʒu�͎��̕����Ɉړ�����");
		checkOneMatch(
				'a', "b", false, 0,
				"���݈ʒu�ɕ��������邪�A�w��̕����łȂ� -> ��v�����A���݈ʒu�͂��̂܂�");
	}

	private void checkOneMatch(
			char ch, String str,
			boolean expectedResult, int expectedCurrentIndex, String message) {
		OneCharPattern oneCharPat = new OneCharPattern(ch);
		RegexPatternTest.checkOneMatch(
				oneCharPat, str, expectedResult, expectedCurrentIndex, message);
	}
	
	static void check(
			OneCharPattern expected, OneCharPattern actual, String message) {
		assertEquals(
				"specifiedChar: " + message,
				expected.getSpecifiedChar(), actual.getSpecifiedChar());
	}
}
