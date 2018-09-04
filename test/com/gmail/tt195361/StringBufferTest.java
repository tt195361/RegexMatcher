package com.gmail.tt195361;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringBufferTest {

	@Test
	public void testIsStart() {
		checkIsStart("abc", 0, true, "index が 0 => true");
		checkIsStart("abc", 1, false, "index が 0 より大きい => false");
	}

	private void checkIsStart(
			String str, int startIndex, boolean expected, String message) {
		StringBuffer strBuffer = new StringBuffer(str, startIndex);
		boolean actual = strBuffer.isStart();
		assertEquals(message, expected, actual);
	}
	
	@Test
	public void testIsLast() {
		checkIsLast("abc", 2, true, "index が '文字列の最後' => true");
		checkIsLast("abc", 3, false, "index が それ以外 => false");
	}

	private void checkIsLast(
			String str, int startIndex, boolean expected, String message) {
		StringBuffer strBuffer = new StringBuffer(str, startIndex);
		boolean actual = strBuffer.isLast();
		assertEquals(message, expected, actual);
	}
	
	@Test
	public void testIsEnd() {
		checkIsEnd("abc", 3, true, "index が 文字列の最後より後ろ => true");
		checkIsEnd("abc", 2, false, "index が 文字列の最後かそれより前 => false");
	}

	private void checkIsEnd(
			String str, int startIndex, boolean expected, String message) {
		StringBuffer strBuffer = new StringBuffer(str, startIndex);
		boolean actual = strBuffer.isEnd();
		assertEquals(message, expected, actual);
	}
}
