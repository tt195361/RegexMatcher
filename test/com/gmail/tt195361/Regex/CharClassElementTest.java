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
				"���݈ʒu�ɕ������Ȃ� -> ��v�����A���݈ʒu�͂��̂܂�");

		checkOneMatch(
				false, HashSetAbc, "a", true, 1,
				"notContains=false �Ŏw��̕����Ɋ܂܂�Ă��� -> ��v���A���̕����Ɉړ�");
		checkOneMatch(
				false, HashSetAbc, "d", false, 0,
				"notContains=false �Ŏw��̕����Ɋ܂܂�Ă��Ȃ� -> ��v�����A���݈ʒu�͂��̂܂�");

		checkOneMatch(
				true, HashSetAbc, "z", true, 1,
				"notContains=true �Ŏw��̕����Ɋ܂܂�Ă��Ȃ� -> ��v���A���̕����Ɉړ�");
		checkOneMatch(
				true, HashSetAbc, "c", false, 0,
				"notContains=true �Ŏw��̕����Ɋ܂܂�Ă��� -> ��v�����A���݈ʒu�͂��̂܂�");
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
