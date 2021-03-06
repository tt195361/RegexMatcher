package com.gmail.tt195361.Regex;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathUtilsTest {

	@Test
	public void testMinCh() {
		checkMinCh('1', '2', '1', "前が小さい場合");
		checkMinCh('4', '3', '3', "後ろが小さい場合");
		checkMinCh('5', '5', '5', "同じ場合");
	}

	private void checkMinCh(char ch1, char ch2, char expected, String message) {
		char actual = MathUtils.minCh(ch1, ch2);
		assertEquals(message, expected, actual);
	}

	@Test
	public void testMaxCh() {
		checkMaxCh('7', '6', '7', "前が大きい場合");
		checkMaxCh('8', '9', '9', "後ろが大きい場合");
		checkMaxCh('0', '0', '0', "同じ場合");
	}

	private void checkMaxCh(char ch1, char ch2, char expected, String message) {
		char actual = MathUtils.maxCh(ch1, ch2);
		assertEquals(message, expected, actual);
	}
}
