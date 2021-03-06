package com.gmail.tt195361.Regex;

import java.util.HashSet;
import static org.junit.Assert.*;
import org.junit.Test;

public class ClosurePatternTest {

	private final String DontCare = null;
	
	@Test
	public void repeatAnyChar() {
		AnyCharPattern anyCharPat = new AnyCharPattern();
		
		checkRepeatPattern(
				anyCharPat, "", 0, true, "",
				"'.*' はどんな文字列にも一致する、空文字列の場合");
		checkRepeatPattern(
				anyCharPat, "abc", 0, true, "abc",
				"'.*' はどんな文字列にも一致する、空でない文字列の場合");
	}
	
	@Test
	public void repeatCharClass() {
		HashSet<Character> charSet = TestUtils.makeHashSet('a', 'b', 'c');
		CharClassPattern charClassPat = new CharClassPattern(false, charSet);

		checkRepeatPattern(
				charClassPat, "xyz", 0, true, "",
				"'[abc]*' は空文字列にも一致する");
		checkRepeatPattern(
				charClassPat, "abcdef", 0, true, "abc",
				"'[abc]*' は、'a', 'b', 'c' の 0 回以上の繰り返しと一致する");
	}
	
	@Test
	public void repeatClosure() {
		OneCharPattern oneCharPat = new OneCharPattern('a');
		ClosurePattern closurePat = new ClosurePattern(oneCharPat);

		checkRepeatPattern(
				closurePat, "abc", 0, true, "a",
				"'a**' は 'a' の 0 回以上の繰り返しと一致する。'*' を 2 つ以上書いても 1 つと同じ");
		checkRepeatPattern(
				closurePat, "xyz", 0, true, "",
				"'a**' は空文字列にも一致する");
	}
	
	@Test
	public void repeatEndOfString() {
		EndOfStringPattern endOfStringPat = new EndOfStringPattern();

		checkRepeatPattern(
				endOfStringPat, "", 0, true, "",
				"'$*' は 文字列の最後と一致する。 '$' に続いて '*' を書いても意味はない");
		checkRepeatPattern(
				endOfStringPat, "abc", 0, true, "",
				"'$*' は文字列のどこでも 0 回以上の繰り返しで空文字と一致する");
	}

	@Test
	public void repeatOneChar() {
		OneCharPattern oneCharPat = new OneCharPattern('a');

		checkRepeatPattern(
				oneCharPat, "aaabc", 0, true, "aaa",
				"'a*' は 'a' の 0 回以上の繰り返しと一致する");
		checkRepeatPattern(
				oneCharPat, "def", 0, true, "",
				"'a*' は空文字と一致する");
	}

	@Test
	public void repeatStartOfString() {
		StartOfStringPattern startOfStringPat = new StartOfStringPattern();

		checkRepeatPattern(
				startOfStringPat, "abc", 0, true, "",
				"'^*' は 文字列の最初と一致する。'^' に続いて '*' を書いても意味はない");
		checkRepeatPattern(
				startOfStringPat, "abc", 1, true, "",
				"'^*' は文字列の最初でなくても 0 回以上の繰り返しで空文字と一致する");
	}
	
	private void checkRepeatPattern(
			RegexPattern repeatPat, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosurePattern closurePat = new ClosurePattern(repeatPat);
		PatternEnumerator patEnum = PatternEnumeratorTest.make(closurePat);
		checkOneMatch(
				patEnum, str, startIndex, expectedResult, expectedSubstring, message);
	}
	
	@Test
	public void matchTheRest() {
		AnyCharPattern anyCharPat = new AnyCharPattern();
		ClosurePattern closurePat = new ClosurePattern(anyCharPat);
		EndOfStringPattern endOfStringPat = new EndOfStringPattern();
		OneCharPattern oneCharPatB = new OneCharPattern('b');
		OneCharPattern oneCharPatA = new OneCharPattern('a');
		OneCharPattern oneCharPatD = new OneCharPattern('d');
		
		checkMatchTheRest(
				closurePat, endOfStringPat, "abc", true, "abc",
				"'.*$' の '.*' は 最長の候補 'abc' と一致する");
		checkMatchTheRest(
				closurePat, oneCharPatB, "abc", true, "ab",
				"'.*b' の '.*' は 1 文字の候補 'a' と一致する");
		checkMatchTheRest(
				closurePat, oneCharPatA, "abc", true, "a",
				"'.*a' の '.*' は 空文字列の候補と一致する");
		checkMatchTheRest(
				closurePat, oneCharPatD, "abc", false, DontCare,
				"'.*d' の '.*' はどの候補とも一致しない");
	}
	
	private void checkMatchTheRest(
			ClosurePattern closurePat, RegexPattern followingPat, String str,
			boolean expectedResult, String expectedSubstring, String message) {
		PatternEnumerator patEnum =
				PatternEnumeratorTest.make(closurePat, followingPat);
		checkOneMatch(
				patEnum, str, 0, expectedResult, expectedSubstring, message);
	}
	
	private void checkOneMatch(
			PatternEnumerator patEnum, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosurePattern closurePat = (ClosurePattern)patEnum.getCurrent();
		StringEnumerator strEnum = StringEnumerator.makeForMatch(str);
		strEnum.setStartIndexForUnitTest(startIndex);
		
		boolean actualResult = closurePat.oneMatch(patEnum, strEnum);
		assertEquals("Result: " + message, expectedResult, actualResult);
		
		if (expectedResult) {
			String actualSubstring = strEnum.getSubstring();
			assertEquals("Substring: " + message, expectedSubstring, actualSubstring);
		}
	}
	
	static void check(
			ClosurePattern expected, ClosurePattern actual, String message) {
		RegexPatternTest.check(
				expected.getRepeatPattern(), actual.getRepeatPattern(), message);
	}
}
