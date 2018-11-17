package com.gmail.tt195361.Regex;

import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CharClassElementTest {

	@Test
	public void oneMatch() {
		final boolean DontCareBool = false;
		final HashSet<Character> DontCareHashSet = TestUtils.makeHashSet();
		final HashSet<Character> HashSetAbc = TestUtils.makeHashSet('a', 'b', 'c');
		
		checkOneMatch(
				DontCareBool, DontCareHashSet, "", false, 0,
				"現在位置に文字がない -> 一致せず、現在位置はそのまま");

		checkOneMatch(
				false, HashSetAbc, "a", true, 1,
				"notContains=false で指定の文字に含まれている -> 一致し、次の文字に移動");
		checkOneMatch(
				false, HashSetAbc, "d", false, 0,
				"notContains=false で指定の文字に含まれていない -> 一致せず、現在位置はそのまま");

		checkOneMatch(
				true, HashSetAbc, "z", true, 1,
				"notContains=true で指定の文字に含まれていない -> 一致し、次の文字に移動");
		checkOneMatch(
				true, HashSetAbc, "c", false, 0,
				"notContains=true で指定の文字に含まれている -> 一致せず、現在位置はそのまま");
	}

	private void checkOneMatch(
			boolean notContains, HashSet<Character> charSet, String str,
			boolean expectedResult, int expectedCurrentIndex, String message) {
		CharClassElement charClassElem = new CharClassElement(notContains, charSet);
		RegexElementTest.checkOneMatch(
				charClassElem, str, expectedResult, expectedCurrentIndex, message);
	}
	
	static void check(
			CharClassElement expected, CharClassElement actual, String message) {
		assertEquals(
				"notContains: " + message,
				expected.getNotContains(), actual.getNotContains());
		TestUtils.checkHashSet(
				"charSet: + message",
				expected.getCharSet(), actual.getCharSet());
	}
}
