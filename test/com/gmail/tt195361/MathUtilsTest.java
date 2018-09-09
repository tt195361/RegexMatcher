package com.gmail.tt195361;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathUtilsTest {

	@Test
	public void testMinCh() {
		checkMinCh('1', '2', '1', "‘O‚ª¬‚³‚¢ê‡");
		checkMinCh('4', '3', '3', "Œã‚ë‚ª¬‚³‚¢ê‡");
		checkMinCh('5', '5', '5', "“¯‚¶ê‡");
	}

	private void checkMinCh(char ch1, char ch2, char expected, String message) {
		char actual = MathUtils.minCh(ch1, ch2);
		assertEquals(message, expected, actual);
	}

	@Test
	public void testMaxCh() {
		checkMaxCh('7', '6', '7', "‘O‚ª‘å‚«‚¢ê‡");
		checkMaxCh('8', '9', '9', "Œã‚ë‚ª‘å‚«‚¢ê‡");
		checkMaxCh('0', '0', '0', "“¯‚¶ê‡");
	}

	private void checkMaxCh(char ch1, char ch2, char expected, String message) {
		char actual = MathUtils.maxCh(ch1, ch2);
		assertEquals(message, expected, actual);
	}
}
