package com.gmail.tt195361;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegexMatcherTest {
		
	@Test
	public void testMatch() {	
		checkSuccess(
				"b.*b", "ababa", 1, "bab",
				"'b.*b' �� 'ababa' �Ɉʒu 1 �� 'bab' �� ��v����");
		checkFail(
				"b.*b", "abaca",
				"�fb.*b' �� 'abaca' �Ɉ�v���Ȃ�");
		checkSuccess(
				".*", "abcde", 0, "abcde",
				"'.*' �͈ʒu 0 �Ŏw��̕����񂷂ׂĂƃ}�b�`����");
		checkSuccess(
				"^", "abc", 0, "",
				"'^' �݂͈̂ʒu 0 �ŋ󕶎���ƃ}�b�`����");
		checkSuccess(
				"abc", "aababc", 3, "abc",
				"�p�^�[���̍ŏ��������̈ʒu�ň�v����ꍇ�ł��A�p�^�[���̈�v�����o�ł���");
		checkSuccess(
				"", "abc", 0, "",
				"�󕶎���̃p�^�[���́A�ʒu 0 �ŋ󕶎���ƈ�v����B");
	}
	
	private void checkSuccess(
			String pattern, String str,
			int expectedStartIndex, String expectedMatchString, String message) {
		check(pattern, str, true, expectedStartIndex, expectedMatchString, message);
	}
	
	private void checkFail(
			String pattern, String str, String message) {
		final int DontCareIndex = -1;
		final String DontCareStr = null;
		check(pattern, str, false, DontCareIndex, DontCareStr, message);
	}
	
	private void check(
			String pattern, String str, boolean expectedIsSuccess,
			int expectedStartIndex, String expectedMatchString, String message) {
		RegexMatcher matcher = new RegexMatcher(pattern);
		MatchResult result = matcher.match(str);

		boolean actualIsSuccess = result.isSucceess();
		assertEquals(message + ": isSucceess", expectedIsSuccess, actualIsSuccess);
		
		if (expectedIsSuccess) {
			int actualStartIndex = result.getStartIndex();
			assertEquals(message + ": startIndex", expectedStartIndex, actualStartIndex);

			String actualMatchString = result.getMatchString();
			assertEquals(message + ": matchString", expectedMatchString, actualMatchString);
		}
	}
}
