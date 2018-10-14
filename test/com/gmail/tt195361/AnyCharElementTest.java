package com.gmail.tt195361;

import org.junit.Test;

public class AnyCharElementTest {

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
		AnyCharElement anyCharElem = new AnyCharElement();
		RegexElementTest.checkOneMatch(
				anyCharElem, str, expectedResult, expectedCurrentIndex, message);
	}
}
