package com.gmail.tt195361.Regex;

import org.junit.Test;

public class AnyCharPatternTest {

	@Test
	public void oneMatch() {
		checkOneMatch(
				"", false, 0,
				"���݈ʒu�ɕ������Ȃ� -> ��v�����A���݈ʒu�͂��̂܂�");
		checkOneMatch(
				"a", true, 1,
				"���݈ʒu�ɕ��������� -> ��v���A���݈ʒu�͎��̕����Ɉړ�����");
	}

	private void checkOneMatch(
			String str, boolean expectedResult, int expectedCurrentIndex, String message) {
		AnyCharPattern anyCharPat = new AnyCharPattern();
		RegexPatternTest.checkOneMatch(
				anyCharPat, str, expectedResult, expectedCurrentIndex, message);
	}
}
