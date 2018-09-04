package com.gmail.tt195361;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegexMatcherTest {
		
	@Test
	public void testMatch() {	
		checkSuccess(
				"b.*b", "ababa", 1, "bab",
				"'b.*b' は 'ababa' に位置 1 で 'bab' と 一致する");
		checkFail(
				"b.*b", "abaca",
				"’b.*b' は 'abaca' に一致しない");
		checkSuccess(
				".*", "abcde", 0, "abcde",
				"'.*' は位置 0 で指定の文字列すべてとマッチする");
		checkSuccess(
				"^", "abc", 0, "",
				"'^' のみは位置 0 で空文字列とマッチする");
		checkSuccess(
				"abc", "aababc", 3, "abc",
				"パターンの最初が複数の位置で一致する場合でも、パターンの一致が検出できる");
		checkSuccess(
				"", "abc", 0, "",
				"空文字列のパターンは、位置 0 で空文字列と一致する。");
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
