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
				StringEnumerator.makeForMatch(str), expectedArray, message);
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
				StringEnumerator.makeForMatch(str), expectedArray, message);
	}
	
	@Test
	public void isEnd() {
		checkIsEnd(
				"abc", new Boolean[] { false, false, false, true, false },
				"現在位置に文字があれば false、文字列の終わりならば true、"
				+ "さらに移動すると false");
		checkIsEnd(
				"", new Boolean[] { true, false }, 
				"空文字列は最初から文字列の終わり");
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
				"Parse の場合、現在位置に文字があれば true、"
				+ "文字列の終わりまで移動すると false");
		checkHasCurrentForParse(
				"", new Boolean[] { false, false },
				"空文字列は最初から現在の位置に文字はない");
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
				"Match の場合、現在位置に文字があるか文字列の終わりなら true、"
				+ "文字列の終わりを越えると false");
		checkHasCurrentForMatch(
				"", new Boolean[] { true, false },
				"空文字列の場合は最初から文字列の終わり");
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
				"Parse の場合、moveNext() を呼ぶと現在位置が一つずつ移動し、"
				+ "現在位置に文字がなくなればそれ以上移動しない");
		checkGetCurrentIndexForParse(
				"", new Integer[] { 0, 0, 0, 0 },
				"空文字列は最初から現在位置に文字がなく、それ以上移動しない");
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
				"Match の場合、moveNext() を呼ぶと現在位置が一つずつ移動し、"
				+ "文字列の終わりを越えるとそれ以上移動しない");
		checkGetCurrentIndexForMatch(
				"", new Integer[] { 0, 1, 1, 1 },
				"空文字列は最初から文字列の終わりで、"
				+ "それを越えるとそれ以上移動しない");
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
				"現在位置の文字を取得する。" +
				"文字列の終わりかそれを越えると '\0' を返す");
		checkGetCurrent(
				"", new Character[] { '\0', '\0' },
				"空文字列は最初から文字列の終わりなので '\0' を返す");
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
				"開始位置から現在位置までの文字列を取得する。");
		checkGetSubstring(
				"", new String[] { "", "" },
				"空文字列の場合は空文字列が返る");
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
				"開始位置に文字があるか文字列の終わり -> true, "
				+ "終わりを越えた -> false");
		checkHasValidStart(
				"", new Boolean[] { true, false },
				"空文字列は最初が文字列の終わり -> true, 終わりを越えた -> false");
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
				"moveNextStart() を呼ぶと開始位置が一つずつ移動し、"
				+ "最後を越えるとそれ以上移動しない");
		checkGetStartIndex(
				"", new Integer[] { 0, 1, 1, 1 },
				"空文字列は最初から文字列の終わりで、"
				+ "それを越えるとそれ以上移動しない");
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
