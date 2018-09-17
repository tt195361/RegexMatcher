package com.gmail.tt195361;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringEnumeratorTest {

	@Test
	public void testIsStart() {
		checkIsStart("abc", 0, true, "index �� 0 => true");
		checkIsStart("abc", 1, false, "index �� 0 ���傫�� => false");
	}

	private void checkIsStart(
			String str, int startIndex, boolean expected, String message) {
		StringEnumerator strEnum = new StringEnumerator(str, startIndex);
		boolean actual = strEnum.isStart();
		assertEquals(message, expected, actual);
	}
	
	@Test
	public void testIsLast() {
		checkIsLast("abc", 2, true, "index �� '������̍Ō�' => true");
		checkIsLast("abc", 3, false, "index �� ����ȊO => false");
	}

	private void checkIsLast(
			String str, int startIndex, boolean expected, String message) {
		StringEnumerator strEnum = new StringEnumerator(str, startIndex);
		boolean actual = strEnum.isLast();
		assertEquals(message, expected, actual);
	}
	
	@Test
	public void testIsEnd() {
		checkIsEnd("abc", 3, true, "index �� ������̍Ō����� => true");
		checkIsEnd("abc", 2, false, "index �� ������̍Ōォ������O => false");
	}

	private void checkIsEnd(
			String str, int startIndex, boolean expected, String message) {
		StringEnumerator strEnum = new StringEnumerator(str, startIndex);
		boolean actual = strEnum.isEnd();
		assertEquals(message, expected, actual);
	}
}
