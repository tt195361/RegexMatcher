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
				"'.*' ‚Í‚Ç‚ñ‚È•¶š—ñ‚É‚àˆê’v‚·‚éA‹ó•¶š—ñ‚Ìê‡");
		checkRepeatPattern(
				anyCharPat, "abc", 0, true, "abc",
				"'.*' ‚Í‚Ç‚ñ‚È•¶š—ñ‚É‚àˆê’v‚·‚éA‹ó‚Å‚È‚¢•¶š—ñ‚Ìê‡");
	}
	
	@Test
	public void repeatCharClass() {
		HashSet<Character> charSet = TestUtils.makeHashSet('a', 'b', 'c');
		CharClassPattern charClassPat = new CharClassPattern(false, charSet);

		checkRepeatPattern(
				charClassPat, "xyz", 0, true, "",
				"'[abc]*' ‚Í‹ó•¶š—ñ‚É‚àˆê’v‚·‚é");
		checkRepeatPattern(
				charClassPat, "abcdef", 0, true, "abc",
				"'[abc]*' ‚ÍA'a', 'b', 'c' ‚Ì 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Æˆê’v‚·‚é");
	}
	
	@Test
	public void repeatClosure() {
		OneCharPattern oneCharPat = new OneCharPattern('a');
		ClosurePattern closurePat = new ClosurePattern(oneCharPat);

		checkRepeatPattern(
				closurePat, "abc", 0, true, "a",
				"'a**' ‚Í 'a' ‚Ì 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Æˆê’v‚·‚éB'*' ‚ğ 2 ‚ÂˆÈã‘‚¢‚Ä‚à 1 ‚Â‚Æ“¯‚¶");
		checkRepeatPattern(
				closurePat, "xyz", 0, true, "",
				"'a**' ‚Í‹ó•¶š—ñ‚É‚àˆê’v‚·‚é");
	}
	
	@Test
	public void repeatEndOfString() {
		EndOfStringPattern endOfStringPat = new EndOfStringPattern();

		checkRepeatPattern(
				endOfStringPat, "", 0, true, "",
				"'$*' ‚Í •¶š—ñ‚ÌÅŒã‚Æˆê’v‚·‚éB '$' ‚É‘±‚¢‚Ä '*' ‚ğ‘‚¢‚Ä‚àˆÓ–¡‚Í‚È‚¢");
		checkRepeatPattern(
				endOfStringPat, "abc", 0, true, "",
				"'$*' ‚Í•¶š—ñ‚Ì‚Ç‚±‚Å‚à 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Å‹ó•¶š‚Æˆê’v‚·‚é");
	}

	@Test
	public void repeatOneChar() {
		OneCharPattern oneCharPat = new OneCharPattern('a');

		checkRepeatPattern(
				oneCharPat, "aaabc", 0, true, "aaa",
				"'a*' ‚Í 'a' ‚Ì 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Æˆê’v‚·‚é");
		checkRepeatPattern(
				oneCharPat, "def", 0, true, "",
				"'a*' ‚Í‹ó•¶š‚Æˆê’v‚·‚é");
	}

	@Test
	public void repeatStartOfString() {
		StartOfStringPattern startOfStringPat = new StartOfStringPattern();

		checkRepeatPattern(
				startOfStringPat, "abc", 0, true, "",
				"'^*' ‚Í •¶š—ñ‚ÌÅ‰‚Æˆê’v‚·‚éB'^' ‚É‘±‚¢‚Ä '*' ‚ğ‘‚¢‚Ä‚àˆÓ–¡‚Í‚È‚¢");
		checkRepeatPattern(
				startOfStringPat, "abc", 1, true, "",
				"'^*' ‚Í•¶š—ñ‚ÌÅ‰‚Å‚È‚­‚Ä‚à 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Å‹ó•¶š‚Æˆê’v‚·‚é");
	}
	
	private void checkRepeatPattern(
			RegexPattern repeatPat, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosurePattern closurePat = new ClosurePattern(repeatPat);
		PatternEnumerator patEnum = PatternEnumerator.makeForUnitTest(closurePat);
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
				"'.*$' ‚Ì '.*' ‚Í Å’·‚ÌŒó•â 'abc' ‚Æˆê’v‚·‚é");
		checkMatchTheRest(
				closurePat, oneCharPatB, "abc", true, "ab",
				"'.*b' ‚Ì '.*' ‚Í 1 •¶š‚ÌŒó•â 'a' ‚Æˆê’v‚·‚é");
		checkMatchTheRest(
				closurePat, oneCharPatA, "abc", true, "a",
				"'.*a' ‚Ì '.*' ‚Í ‹ó•¶š—ñ‚ÌŒó•â‚Æˆê’v‚·‚é");
		checkMatchTheRest(
				closurePat, oneCharPatD, "abc", false, DontCare,
				"'.*d' ‚Ì '.*' ‚Í‚Ç‚ÌŒó•â‚Æ‚àˆê’v‚µ‚È‚¢");
	}
	
	private void checkMatchTheRest(
			ClosurePattern closurePat, RegexPattern followingPat, String str,
			boolean expectedResult, String expectedSubstring, String message) {
		PatternEnumerator patEnum =
				PatternEnumerator.makeForUnitTest(closurePat, followingPat);
		checkOneMatch(
				patEnum, str, 0, expectedResult, expectedSubstring, message);
	}
	
	private void checkOneMatch(
			PatternEnumerator patEnum, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosurePattern closurePat = (ClosurePattern)patEnum.getCurrent();
		StringEnumerator strEnum = new StringEnumerator(str, startIndex);
		
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
