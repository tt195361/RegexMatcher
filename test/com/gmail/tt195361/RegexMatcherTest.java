package com.gmail.tt195361;

import static org.junit.Assert.*;

import org.junit.Test;
import org.omg.CosNaming.IstringHelper;

public class RegexMatcherTest {

	@Test
	public void testOneChar() {
		ElementBuffer elemBuffer = new ElementBuffer(
				new OneCharElement('a'));
		check(elemBuffer, "a", true, "�w��̕����Ȃ�}�b�`����");
		check(elemBuffer, "b", false, "�w��ȊO�̓}�b�`���Ȃ�");
	}
	
	@Test
	public void testAnyChar() {
		ElementBuffer elemBuffer = new ElementBuffer(
				new AnyCharElement());
		check(elemBuffer, "a", true, "�ǂ�ȕ����ł��}�b�`����");
		check(elemBuffer, "��", true, "�ǂ�ȕ����ł��}�b�`����");
	}
	
	@Test
	public void testClosure() {
		ElementBuffer elemBuffer = new ElementBuffer(
				new ClosureElement(new AnyCharElement()),
				new OneCharElement('b'));
		check(elemBuffer, "ababa", true, ".*b �Ƀ}�b�`����");
	}
	
	private void check(
			ElementBuffer elemBuffer, String str, boolean expected, String message) {
		StringBuffer strBuffer = new StringBuffer(str);
		boolean actual = RegexMatcher.match(elemBuffer, strBuffer);
		assertEquals(message, expected, actual);
	}
}
