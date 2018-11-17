package com.gmail.tt195361;

import java.util.HashSet;
import static org.junit.Assert.*;
import org.junit.Test;

public class ClosureElementTest {

	private final String DontCare = null;
	
	@Test
	public void repeatAnyChar() {
		AnyCharElement anyCharElem = new AnyCharElement();
		
		checkRepeatElement(
				anyCharElem, "", 0, true, "",
				"'.*' ‚Í‚Ç‚ñ‚È•¶š—ñ‚É‚àˆê’v‚·‚éA‹ó•¶š—ñ‚Ìê‡");
		checkRepeatElement(
				anyCharElem, "abc", 0, true, "abc",
				"'.*' ‚Í‚Ç‚ñ‚È•¶š—ñ‚É‚àˆê’v‚·‚éA‹ó‚Å‚È‚¢•¶š—ñ‚Ìê‡");
	}
	
	@Test
	public void repeatCharClass() {
		HashSet<Character> charSet = TestUtils.makeHashSet('a', 'b', 'c');
		CharClassElement charClassElem = new CharClassElement(false, charSet);

		checkRepeatElement(
				charClassElem, "xyz", 0, true, "",
				"'[abc]*' ‚Í‹ó•¶š—ñ‚É‚àˆê’v‚·‚é");
		checkRepeatElement(
				charClassElem, "abcdef", 0, true, "abc",
				"'[abc]*' ‚ÍA'a', 'b', 'c' ‚Ì 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Æˆê’v‚·‚é");
	}
	
	@Test
	public void repeatClosure() {
		OneCharElement oneCharElem = new OneCharElement('a');
		ClosureElement closureElem = new ClosureElement(oneCharElem);

		checkRepeatElement(
				closureElem, "abc", 0, true, "a",
				"'a**' ‚Í 'a' ‚Ì 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Æˆê’v‚·‚éB'*' ‚ğ 2 ‚ÂˆÈã‘‚¢‚Ä‚à 1 ‚Â‚Æ“¯‚¶");
		checkRepeatElement(
				closureElem, "xyz", 0, true, "",
				"'a**' ‚Í‹ó•¶š—ñ‚É‚àˆê’v‚·‚é");
	}
	
	@Test
	public void repeatEndOfString() {
		EndOfStringElement endOfStringElem = new EndOfStringElement();

		checkRepeatElement(
				endOfStringElem, "", 0, true, "",
				"'$*' ‚Í •¶š—ñ‚ÌÅŒã‚Æˆê’v‚·‚éB '$' ‚É‘±‚¢‚Ä '*' ‚ğ‘‚¢‚Ä‚àˆÓ–¡‚Í‚È‚¢");
		checkRepeatElement(
				endOfStringElem, "abc", 0, true, "",
				"'$*' ‚Í•¶š—ñ‚Ì‚Ç‚±‚Å‚à 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Å‹ó•¶š‚Æˆê’v‚·‚é");
	}

	@Test
	public void repeatOneChar() {
		OneCharElement oneCharElem = new OneCharElement('a');

		checkRepeatElement(
				oneCharElem, "aaabc", 0, true, "aaa",
				"'a*' ‚Í 'a' ‚Ì 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Æˆê’v‚·‚é");
		checkRepeatElement(
				oneCharElem, "def", 0, true, "",
				"'a*' ‚Í‹ó•¶š‚Æˆê’v‚·‚é");
	}

	@Test
	public void repeatStartOfString() {
		StartOfStringElement startOfStringElem = new StartOfStringElement();

		checkRepeatElement(
				startOfStringElem, "abc", 0, true, "",
				"'^*' ‚Í •¶š—ñ‚ÌÅ‰‚Æˆê’v‚·‚éB'^' ‚É‘±‚¢‚Ä '*' ‚ğ‘‚¢‚Ä‚àˆÓ–¡‚Í‚È‚¢");
		checkRepeatElement(
				startOfStringElem, "abc", 1, true, "",
				"'^*' ‚Í•¶š—ñ‚ÌÅ‰‚Å‚È‚­‚Ä‚à 0 ‰ñˆÈã‚ÌŒJ‚è•Ô‚µ‚Å‹ó•¶š‚Æˆê’v‚·‚é");
	}
	
	private void checkRepeatElement(
			RegexElement repeatElem, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosureElement closureElem = new ClosureElement(repeatElem);
		ElementEnumerator elemEnum = ElementEnumerator.makeForUnitTest(closureElem);
		checkOneMatch(
				elemEnum, str, startIndex, expectedResult, expectedSubstring, message);
	}
	
	@Test
	public void matchTheRest() {
		AnyCharElement anyChar = new AnyCharElement();
		ClosureElement closureElem = new ClosureElement(anyChar);
		EndOfStringElement endOfStringElem = new EndOfStringElement();
		OneCharElement oneCharB = new OneCharElement('b');
		OneCharElement oneCharA = new OneCharElement('a');
		OneCharElement oneCharD = new OneCharElement('d');
		
		checkMatchTheRest(
				closureElem, endOfStringElem, "abc", true, "abc",
				"'.*$' ‚Ì '.*' ‚Í Å’·‚ÌŒó•â 'abc' ‚Æˆê’v‚·‚é");
		checkMatchTheRest(
				closureElem, oneCharB, "abc", true, "ab",
				"'.*b' ‚Ì '.*' ‚Í 1 •¶š‚ÌŒó•â 'a' ‚Æˆê’v‚·‚é");
		checkMatchTheRest(
				closureElem, oneCharA, "abc", true, "a",
				"'.*a' ‚Ì '.*' ‚Í ‹ó•¶š—ñ‚ÌŒó•â‚Æˆê’v‚·‚é");
		checkMatchTheRest(
				closureElem, oneCharD, "abc", false, DontCare,
				"'.*d' ‚Ì '.*' ‚Í‚Ç‚ÌŒó•â‚Æ‚àˆê’v‚µ‚È‚¢");
	}
	
	private void checkMatchTheRest(
			ClosureElement closureElem, RegexElement followingElem, String str,
			boolean expectedResult, String expectedSubstring, String message) {
		ElementEnumerator elemEnum =
				ElementEnumerator.makeForUnitTest(closureElem, followingElem);
		checkOneMatch(
				elemEnum, str, 0, expectedResult, expectedSubstring, message);
	}
	
	private void checkOneMatch(
			ElementEnumerator elemEnum, String str, int startIndex, boolean expectedResult,
			String expectedSubstring, String message) {
		ClosureElement closureElem = (ClosureElement)elemEnum.getCurrent();
		StringEnumerator strEnum = new StringEnumerator(str, startIndex);
		
		boolean actualResult = closureElem.oneMatch(elemEnum, strEnum);
		assertEquals("Result: " + message, expectedResult, actualResult);
		
		if (expectedResult) {
			String actualSubstring = strEnum.getSubstring();
			assertEquals("Substring: " + message, expectedSubstring, actualSubstring);
		}
	}
	
	static void check(
			ClosureElement expected, ClosureElement actual, String message) {
		RegexElementTest.check(
				expected.getRepeatElement(), actual.getRepeatElement(), message);
	}
}
