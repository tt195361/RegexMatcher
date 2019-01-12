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
		checkCurrentFunction(
				(strEnum) -> strEnum.isStart(),
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
		checkCurrentFunction(
				(strEnum) -> strEnum.isLast(),
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
		checkCurrentFunction(
				(strEnum) -> strEnum.isEnd(),
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
		checkCurrentFunction(
				(strEnum) -> strEnum.hasCurrent(),
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
		checkCurrentFunction(
				(strEnum) -> strEnum.hasCurrentOrEnd(),
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
		checkCurrentFunction(
				(strEnum) -> strEnum.getCurrentIndex(),
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
		checkCurrentFunction(
				(strEnum) -> strEnum.getCurrent(),
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
		checkCurrentFunction(
				(strEnum) -> strEnum.getSubstring(),
				str, expectedArray, message);
	}
	
	@Test
	public void hasCurrentStart() {
		checkHasCurrentStart(
				"abc", new Boolean[] { true, true, true, true, false },
				"開始位置に文字があるか文字列の終わり -> true, 終わりを越えた -> false");
		checkHasCurrentStart(
				"", new Boolean[] { true, false },
				"空文字列は最初が文字列の終わり -> true, 終わりを越えた -> false");
	}
	
	private void checkHasCurrentStart(
			String str, Boolean[] expectedArray, String message) {
		checkStartFunction(
				(strEnum) -> strEnum.hasCurrentStart(),
				str, expectedArray, message);
	}
	
	@Test
	public void getStartIndex() {
		checkGetStartIndex(
				"abc", new Integer[] { 0, 1, 2, 3, 4, 4, 4 },
				"moveNextStart() を呼ぶと開始位置が一つずつ移動し、最後を越えるとそれ以上移動しない");
		checkGetStartIndex(
				"", new Integer[] { 0, 1, 1, 1 },
				"空文字列は最初から文字列の終わりで、それを越えるとそれ以上移動しない");
	}
	
	private void checkGetStartIndex(
			String str, Integer[] expectedArray, String message) {
		checkStartFunction(
				(strEnum) -> strEnum.getStartIndex(),
				str, expectedArray, message);
	}
	
	private <R> void checkCurrentFunction(
			Function<StringEnumerator, R> checkFunc,
			String str, R[] expectedArray, String message) {
		checkFunction(
				checkFunc, (strEnum) -> strEnum.moveNext(),
				str, expectedArray, message);
	}
	
	private <R> void checkStartFunction(
			Function<StringEnumerator, R> checkFunc,
			String str, R[] expectedArray, String message) {
		checkFunction(
				checkFunc, (strEnum) -> strEnum.moveNextStart(),
				str, expectedArray, message);
	}
	
	private <R> void checkFunction(
			Function<StringEnumerator, R> checkFunc,
			Consumer<StringEnumerator> moveFunc,
			String str, R[] expectedArray, String message) {
		StringEnumerator strEnum = new StringEnumerator(str);
		
		for (R expected: expectedArray) {
			R actual = checkFunc.apply(strEnum);
			assertEquals(message, expected, actual);
			
			moveFunc.accept(strEnum);
		}
	}
}
