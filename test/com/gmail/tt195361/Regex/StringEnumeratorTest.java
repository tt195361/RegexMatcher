package com.gmail.tt195361.Regex;

import java.util.function.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class StringEnumeratorTest {

	@Test
	public void isStart() {
		checkIsStart(
				"abc", new Boolean[] { true, false, false, false },
				"���݈ʒu��������̍ŏ��Ȃ�� true");
		checkIsStart(
				"", new Boolean[] { true, false },
				"�󕶎���ł����݈ʒu���ŏ��Ȃ�� true");
	}

	private void checkIsStart(
			String str, Boolean[] expectedArray, String message) {
		checkFunction(
				(strEnum) -> (Boolean)strEnum.isStart(),
				str, expectedArray, message);
	}
	
	@Test
	public void isLast() {
		checkIsLast(
				"abc", new Boolean[] { false, false, true, false },
				"���݈ʒu��������̍Ō�̕����Ȃ�� true");
		checkIsLast(
				"", new Boolean[] { false, false },
				"�󕶎���͍Ō�̕������Ȃ�");
	}

	private void checkIsLast(
			String str, Boolean[] expectedArray, String message) {
		checkFunction(
				(strEnum) -> (Boolean)strEnum.isLast(),
				str, expectedArray, message);
	}
	
	@Test
	public void isEnd() {
		checkIsEnd(
				"abc", new Boolean[] { false, false, false, true, false },
				"���݈ʒu�ɕ���������� false�A������̏I���Ȃ�� true�A����Ɉړ������ false");
		checkIsEnd(
				"", new Boolean[] { true, false }, 
				"�󕶎���͍ŏ����當����̏I���");
	}

	private void checkIsEnd(
			String str, Boolean[] expectedArray, String message) {
		checkFunction(
				(strEnum) -> (Boolean)strEnum.isEnd(),
				str, expectedArray, message);
	}
	
	@Test
	public void hasCurrent() {
		checkHasCurrent(
				"abc", new Boolean[] { true, true, true, false, false },
				"���݂̈ʒu�ɕ���������� true�A������̏I���܂ňړ������ false");
		checkHasCurrent(
				"", new Boolean[] { false, false },
				"�󕶎���͍ŏ����猻�݂̈ʒu�ɕ����͂Ȃ�");
	}
	
	private void checkHasCurrent(
			String str, Boolean[] expectedArray, String message) {
		checkFunction(
				(strEnum) -> (Boolean)strEnum.hasCurrent(),
				str, expectedArray, message);
	}
	
	@Test
	public void hasCurrentOrEnd() {
		checkHasCurrentOrEnd(
				"abc", new Boolean[] { true, true, true, true, false },
				"���݂̈ʒu�ɕ��������邩������̏I���Ȃ� true�A������̏I�����z����� false");
		checkHasCurrentOrEnd(
				"", new Boolean[] { true, false },
				"�󕶎���̏ꍇ�͍ŏ����當����̏I���");
	}
	
	private void checkHasCurrentOrEnd(
			String str, Boolean[] expectedArray, String message) {
		checkFunction(
				(strEnum) -> (Boolean)strEnum.hasCurrentOrEnd(),
				str, expectedArray, message);
	}
	
	@Test
	public void getCurrentIndex() {
		checkGetCurrentIndex(
				"abc", new Integer[] { 0, 1, 2, 3, 4, 4, 4 },
				"moveNext() ���ĂԂƌ��݈ʒu������ړ����A�Ō���z����Ƃ���ȏ�ړ����Ȃ�");
		checkGetCurrentIndex(
				"", new Integer[] { 0, 1, 1, 1 },
				"�󕶎���͍ŏ����當����̏I���ŁA������z����Ƃ���ȏ�ړ����Ȃ�");
	}
	
	private void checkGetCurrentIndex(
			String str, Integer[] expectedArray, String message) {
		checkFunction(
				(strEnum) -> (Integer)strEnum.getCurrentIndex(),
				str, expectedArray, message);
	}
	
	@Test
	public void getCurrent() {
		checkGetCurrent(
				"abc", new Character[] { 'a', 'b', 'c', '\0', '\0' },
				"���݈ʒu�̕������擾����B������̏I��肩������z����� '\0' ��Ԃ�");
		checkGetCurrent(
				"", new Character[] { '\0', '\0' },
				"�󕶎���͍ŏ����當����̏I���Ȃ̂� '\0' ��Ԃ�");
	}
	
	private void checkGetCurrent(
			String str, Character[] expectedArray, String message) {
		checkFunction(
				(strEnum) -> (Character)strEnum.getCurrent(),
				str, expectedArray, message);
	}
	
	@Test
	public void getSubstring() {
		checkGetSubstring(
				"abc", new String[] { "", "a", "ab", "abc", "abc" },
				"�J�n�ʒu���猻�݈ʒu�܂ł̕�������擾����B");
		checkGetSubstring(
				"", new String[] { "", "" },
				"�󕶎���̏ꍇ�͋󕶎��񂪕Ԃ�");
	}
	
	private void checkGetSubstring(
			String str, String[] expectedArray, String message) {
		checkFunction(
				(strEnum) -> strEnum.getSubstring(),
				str, expectedArray, message);
	}
	
	private <R> void checkFunction(
			Function<StringEnumerator, R> checkFunc,
			String str, R[] expectedArray, String message) {
		StringEnumerator strEnum = new StringEnumerator(str, 0);
		
		for (R expected: expectedArray) {
			R actual = checkFunc.apply(strEnum);
			assertEquals(message, expected, actual);
			
			strEnum.moveNext();
		}
	}
}
