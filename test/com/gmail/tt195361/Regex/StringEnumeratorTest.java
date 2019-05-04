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
		checkCurrentFunction(
				(strEnum) -> strEnum.isStart(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
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
		checkCurrentFunction(
				(strEnum) -> strEnum.isLast(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
	}
	
	@Test
	public void isEnd() {
		checkIsEnd(
				"abc", new Boolean[] { false, false, false, true, false },
				"���݈ʒu�ɕ���������� false�A������̏I���Ȃ�� true�A"
				+ "����Ɉړ������ false");
		checkIsEnd(
				"", new Boolean[] { true, false }, 
				"�󕶎���͍ŏ����當����̏I���");
	}

	private void checkIsEnd(
			String str, Boolean[] expectedArray, String message) {
		checkCurrentFunction(
				(strEnum) -> strEnum.isEnd(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
	}
	
	@Test
	public void hasCurrentForParse() {
		checkHasCurrentForParse(
				"abc", new Boolean[] { true, true, true, false, false },
				"Parse �̏ꍇ�A���݈ʒu�ɕ���������� true�A"
				+ "������̏I���܂ňړ������ false");
		checkHasCurrentForParse(
				"", new Boolean[] { false, false },
				"�󕶎���͍ŏ����猻�݂̈ʒu�ɕ����͂Ȃ�");
	}
	
	private void checkHasCurrentForParse(
			String str, Boolean[] expectedArray, String message) {
		checkCurrentFunction(
				(strEnum) -> strEnum.hasCurrent(),
				StringEnumerator.makeForParse(str), expectedArray, message);
	}
	
	@Test
	public void hasCurrentForMatch() {
		checkHasCurrentForMatch(
				"abc", new Boolean[] { true, true, true, true, false },
				"Match �̏ꍇ�A���݈ʒu�ɕ��������邩������̏I���Ȃ� true�A"
				+ "������̏I�����z����� false");
		checkHasCurrentForMatch(
				"", new Boolean[] { true, false },
				"�󕶎���̏ꍇ�͍ŏ����當����̏I���");
	}
	
	private void checkHasCurrentForMatch(
			String str, Boolean[] expectedArray, String message) {
		checkCurrentFunction(
				(strEnum) -> strEnum.hasCurrent(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
	}
	
	@Test
	public void getCurrentIndexForParse() {
		checkGetCurrentIndexForParse(
				"abc", new Integer[] { 0, 1, 2, 3, 3, 3, 3 },
				"Parse �̏ꍇ�AmoveNext() ���ĂԂƌ��݈ʒu������ړ����A"
				+ "���݈ʒu�ɕ������Ȃ��Ȃ�΂���ȏ�ړ����Ȃ�");
		checkGetCurrentIndexForParse(
				"", new Integer[] { 0, 0, 0, 0 },
				"�󕶎���͍ŏ����猻�݈ʒu�ɕ������Ȃ��A����ȏ�ړ����Ȃ�");
	}
	
	private void checkGetCurrentIndexForParse(
			String str, Integer[] expectedArray, String message) {
		checkCurrentFunction(
				(strEnum) -> strEnum.getCurrentIndex(),
				StringEnumerator.makeForParse(str), expectedArray, message);
	}
	
	@Test
	public void getCurrentIndexForMatch() {
		checkGetCurrentIndexForMatch(
				"abc", new Integer[] { 0, 1, 2, 3, 4, 4, 4 },
				"Match �̏ꍇ�AmoveNext() ���ĂԂƌ��݈ʒu������ړ����A"
				+ "������̏I�����z����Ƃ���ȏ�ړ����Ȃ�");
		checkGetCurrentIndexForMatch(
				"", new Integer[] { 0, 1, 1, 1 },
				"�󕶎���͍ŏ����當����̏I���ŁA"
				+ "������z����Ƃ���ȏ�ړ����Ȃ�");
	}
	
	private void checkGetCurrentIndexForMatch(
			String str, Integer[] expectedArray, String message) {
		checkCurrentFunction(
				(strEnum) -> strEnum.getCurrentIndex(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
	}
	
	@Test
	public void getCurrent() {
		checkGetCurrent(
				"abc", new Character[] { 'a', 'b', 'c', '\0', '\0' },
				"���݈ʒu�̕������擾����B" +
				"������̏I��肩������z����� '\0' ��Ԃ�");
		checkGetCurrent(
				"", new Character[] { '\0', '\0' },
				"�󕶎���͍ŏ����當����̏I���Ȃ̂� '\0' ��Ԃ�");
	}
	
	private void checkGetCurrent(
			String str, Character[] expectedArray, String message) {
		checkCurrentFunction(
				(strEnum) -> strEnum.getCurrent(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
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
		checkCurrentFunction(
				(strEnum) -> strEnum.getSubstring(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
	}
	
	@Test
	public void hasValidStart() {
		checkHasValidStart(
				"abc", new Boolean[] { true, true, true, true, false },
				"�J�n�ʒu�ɕ��������邩������̏I��� -> true, "
				+ "�I�����z���� -> false");
		checkHasValidStart(
				"", new Boolean[] { true, false },
				"�󕶎���͍ŏ���������̏I��� -> true, �I�����z���� -> false");
	}
	
	private void checkHasValidStart(
			String str, Boolean[] expectedArray, String message) {
		checkStartFunction(
				(strEnum) -> strEnum.hasValidStart(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
	}
	
	@Test
	public void getStartIndex() {
		checkGetStartIndex(
				"abc", new Integer[] { 0, 1, 2, 3, 4, 4, 4 },
				"moveNextStart() ���ĂԂƊJ�n�ʒu������ړ����A"
				+ "�Ō���z����Ƃ���ȏ�ړ����Ȃ�");
		checkGetStartIndex(
				"", new Integer[] { 0, 1, 1, 1 },
				"�󕶎���͍ŏ����當����̏I���ŁA"
				+ "������z����Ƃ���ȏ�ړ����Ȃ�");
	}
	
	private void checkGetStartIndex(
			String str, Integer[] expectedArray, String message) {
		checkStartFunction(
				(strEnum) -> strEnum.getStartIndex(),
				StringEnumerator.makeForMatch(str), expectedArray, message);
	}
	
	private <R> void checkCurrentFunction(
			Function<StringEnumerator, R> checkFunc,
			StringEnumerator strEnum, R[] expectedArray, String message) {
		checkFunction(
				checkFunc, (se) -> se.moveNext(),
				strEnum, expectedArray, message);
	}
	
	private <R> void checkStartFunction(
			Function<StringEnumerator, R> checkFunc,
			StringEnumerator strEnum, R[] expectedArray, String message) {
		checkFunction(
				checkFunc, (se) -> se.moveNextStart(),
				strEnum, expectedArray, message);
	}
	
	private <R> void checkFunction(
			Function<StringEnumerator, R> checkFunc,
			Consumer<StringEnumerator> moveFunc,
			StringEnumerator strEnum, R[] expectedArray, String message) {
		
		for (R expected: expectedArray) {
			R actual = checkFunc.apply(strEnum);
			assertEquals(message, expected, actual);
			
			moveFunc.accept(strEnum);
		}
	}
}
