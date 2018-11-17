package com.gmail.tt195361.Regex;

import java.util.function.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class StringEnumeratorTest {

	@Test
	public void isStart() {
		checkIsStart(
				"abc", new Boolean[] { true, false, false, false },
				"現在位置が文字列の最初ならば true");
		checkIsStart(
				"", new Boolean[] { true, false },
				"空文字列でも現在位置が最初ならば true");
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
				"現在位置が文字列の最後の文字ならば true");
		checkIsLast(
				"", new Boolean[] { false, false },
				"空文字列は最後の文字がない");
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
				"現在位置に文字があれば false、文字列の終わりならば true、さらに移動すると false");
		checkIsEnd(
				"", new Boolean[] { true, false }, 
				"空文字列は最初から文字列の終わり");
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
				"現在の位置に文字があれば true、文字列の終わりまで移動すると false");
		checkHasCurrent(
				"", new Boolean[] { false, false },
				"空文字列は最初から現在の位置に文字はない");
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
				"現在の位置に文字があるか文字列の終わりなら true、文字列の終わりを越えると false");
		checkHasCurrentOrEnd(
				"", new Boolean[] { true, false },
				"空文字列の場合は最初から文字列の終わり");
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
				"moveNext() を呼ぶと現在位置が一つずつ移動し、最後を越えるとそれ以上移動しない");
		checkGetCurrentIndex(
				"", new Integer[] { 0, 1, 1, 1 },
				"空文字列は最初から文字列の終わりで、それを越えるとそれ以上移動しない");
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
				"現在位置の文字を取得する。文字列の終わりかそれを越えると '\0' を返す");
		checkGetCurrent(
				"", new Character[] { '\0', '\0' },
				"空文字列は最初から文字列の終わりなので '\0' を返す");
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
				"開始位置から現在位置までの文字列を取得する。");
		checkGetSubstring(
				"", new String[] { "", "" },
				"空文字列の場合は空文字列が返る");
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
