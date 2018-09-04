package com.gmail.tt195361;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringBufferTest {

	@Test
	public void testIsStart() {
		checkIsStart("abc", 0, true, "index ‚ª 0 => true");
		checkIsStart("abc", 1, false, "index ‚ª 0 ‚æ‚è‘å‚«‚¢ => false");
	}

	private void checkIsStart(
			String str, int startIndex, boolean expected, String message) {
		StringBuffer strBuffer = new StringBuffer(str, startIndex);
		boolean actual = strBuffer.isStart();
		assertEquals(message, expected, actual);
	}
	
	@Test
	public void testIsLast() {
		checkIsLast("abc", 2, true, "index ‚ª '•¶Žš—ñ‚ÌÅŒã' => true");
		checkIsLast("abc", 3, false, "index ‚ª ‚»‚êˆÈŠO => false");
	}

	private void checkIsLast(
			String str, int startIndex, boolean expected, String message) {
		StringBuffer strBuffer = new StringBuffer(str, startIndex);
		boolean actual = strBuffer.isLast();
		assertEquals(message, expected, actual);
	}
	
	@Test
	public void testIsEnd() {
		checkIsEnd("abc", 3, true, "index ‚ª •¶Žš—ñ‚ÌÅŒã‚æ‚èŒã‚ë => true");
		checkIsEnd("abc", 2, false, "index ‚ª •¶Žš—ñ‚ÌÅŒã‚©‚»‚ê‚æ‚è‘O => false");
	}

	private void checkIsEnd(
			String str, int startIndex, boolean expected, String message) {
		StringBuffer strBuffer = new StringBuffer(str, startIndex);
		boolean actual = strBuffer.isEnd();
		assertEquals(message, expected, actual);
	}
}
