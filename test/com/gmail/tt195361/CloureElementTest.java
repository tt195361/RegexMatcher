package com.gmail.tt195361;

import java.util.HashSet;
import static org.junit.Assert.*;
import org.junit.Test;

public class CloureElementTest {

	private final String DontCare = null;
	
	@Test
	public void repeatAnyChar() {
		AnyCharElement anyCharElem = new AnyCharElement();
		
		checkRepeatElement(
				anyCharElem, "", 0, true, "",
				"'.*' はどんな文字列にも一致する、空文字列の場合");
		checkRepeatElement(
				anyCharElem, "abc", 0, true, "abc",
				"'.*' はどんな文字列にも一致する、空でない文字列の場合");
	}
	
	@Test
	public void repeatCharClass() {
		HashSet<Character> charSet = TestUtils.makeHashSet('a', 'b', 'c');
		CharClassElement charClassElem = new CharClassElement(false, charSet);

		checkRepeatElement(
				charClassElem, "xyz", 0, true, "",
				"'[abc]*' は空文字列にも一致する");
		checkRepeatElement(
				charClassElem, "abcdef", 0, true, "abc",
				"'[abc]*' は、'a', 'b', 'c' の 0 回以上の繰り返しと一致する");
	}
	
	@Test
	public void repeatClosure() {
		OneCharElement oneCharElem = new OneCharElement('a');
		ClosureElement closureElem = new ClosureElement(oneCharElem);

		checkRepeatElement(
				closureElem, "abc", 0, true, "a",
				"'a**' は 'a' の 0 回以上の繰り返しと一致する。'*' を 2 つ以上書いても 1 つと同じ");
		checkRepeatElement(
				closureElem, "xyz", 0, true, "",
				"'a**' は空文字列にも一致する");
	}
	
	@Test
	public void repeatEndOfString() {
		EndOfStringElement endOfStringElem = new EndOfStringElement();

		checkRepeatElement(
				endOfStringElem, "", 0, true, "",
				"'$*' は 文字列の末尾と一致する。 '$' に続いて '*' を書いても意味はない");
		checkRepeatElement(
				endOfStringElem, "abc", 0, true, "",
				"'$*' は文字列のどこでも 0 回以上の繰り返しで空文字と一致する");
	}

	@Test
	public void repeatOneChar() {
		OneCharElement oneCharElem = new OneCharElement('a');

		checkRepeatElement(
				oneCharElem, "aaabc", 0, true, "aaa",
				"'a*' は 'a' の 0 回以上の繰り返しと一致する");
		checkRepeatElement(
				oneCharElem, "def", 0, true, "",
				"'a*' は空文字と一致する");
	}

	@Test
	public void repeatStartOfString() {
		StartOfStringElement startOfStringElem = new StartOfStringElement();

		checkRepeatElement(
				startOfStringElem, "abc", 0, true, "",
				"'^*' は 文字列の先頭と一致する。'^' に続いて '*' を書いても意味はない");
		checkRepeatElement(
				startOfStringElem, "abc", 1, true, "",
				"'^*' は文字列の先頭でなくても 0 回以上の繰り返しで空文字と一致する");
	}
	
	private void checkRepeatElement(
			RegexElement repeatElem, String str, int startIndex, boolean expectedResult,
			String expectedMatchString, String message) {
		ClosureElement closureElem = new ClosureElement(repeatElem);
		ElementEnumerator elemEnum = ElementEnumerator.makeForUnitTest(closureElem);
		checkOneMatch(
				elemEnum, str, startIndex, expectedResult, expectedMatchString, message);
	}
	
	@Test
	public void matchTheRest() {
		AnyCharElement anyChar = new AnyCharElement();
		ClosureElement closureElem = new ClosureElement(anyChar);
		EndOfStringElement endOfStringElem = new EndOfStringElement();
		OneCharElement oneCharB = new OneCharElement('b');
		OneCharElement oneCharA = new OneCharElement('a');
		OneCharElement oneCharD = new OneCharElement('d');
		
		checkTheRest(
				closureElem, endOfStringElem, "abc", true, "abc",
				"'.*$' の '.*' は 最長の候補 'abc' と一致する");
		checkTheRest(
				closureElem, oneCharB, "abc", true, "ab",
				"'.*b' の '.*' は 1 文字の候補 'a' と一致する");
		checkTheRest(
				closureElem, oneCharA, "abc", true, "a",
				"'.*a' の '.*' は 空文字列の候補と一致する");
		checkTheRest(
				closureElem, oneCharD, "abc", false, DontCare,
				"'.*d' の '.*' はどの候補とも一致しない");
	}
	
	private void checkTheRest(
			ClosureElement closureElem, RegexElement followingElem, String str,
			boolean expectedResult, String expectedMatchString, String message) {
		ElementEnumerator elemEnum =
				ElementEnumerator.makeForUnitTest(closureElem, followingElem);
		checkOneMatch(
				elemEnum, str, 0, expectedResult, expectedMatchString, message);
	}
	
	private void checkOneMatch(
			ElementEnumerator elemEnum, String str, int startIndex, boolean expectedResult,
			String expectedMatchString, String message) {
		ClosureElement closureElem = (ClosureElement)elemEnum.getCurrent();
		StringEnumerator strEnum = new StringEnumerator(str, startIndex);
		
		boolean actualResult = closureElem.oneMatch(elemEnum, strEnum);
		assertEquals("Result: " + message, expectedResult, actualResult);
		
		if (expectedResult) {
			String actualMatchString = strEnum.getMatchString();
			assertEquals("MatchString: " + message, expectedMatchString, actualMatchString);
		}
	}
}
