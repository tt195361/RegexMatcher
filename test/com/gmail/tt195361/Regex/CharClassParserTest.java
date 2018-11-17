package com.gmail.tt195361.Regex;

import org.junit.Test;

public class CharClassParserTest {

	@Test
	public void testParse() {
		// 1 文字と範囲
		checkParse(
				"[abc]",
				new CharClassElement(false, TestUtils.makeHashSet('a', 'b', 'c')),
				"1 文字ずつ指定 -> 指定したそれぞれの文字が HashSet に入る");
		checkParse(
				"[x-z]",
				new CharClassElement(false, TestUtils.makeHashSet('x', 'y', 'z')),
				"範囲指定 -> 指定した範囲の文字が HashSet に入る");
		checkParse(
				"[j-h]",
				new CharClassElement(false, TestUtils.makeHashSet('h', 'i', 'j')),
				"範囲の順序が逆 -> 順序が逆でも指定の範囲が HashSet に入る");
		checkParse(
				"[ae-gi]",
				new CharClassElement(false, TestUtils.makeHashSet('a', 'e', 'f', 'g', 'i')),
				"1 文字と範囲の組み合わせ -> それぞれの文字と範囲が HashSet に入る");

		// 文字のエスケープ
		checkParse(
				"[\\[\\-\\]]",
				new CharClassElement(false, TestUtils.makeHashSet('[', '-', ']')),
				"文字のエスケープ -> エスケープされた文字が HashSet に入る" +
				"'-' はエスケープされているので、範囲ではなく '-' になる");
		checkParse(
				"[\\[-\\]]",
				new CharClassElement(false, TestUtils.makeHashSet('[', '\\', ']')),
				"エスケープされた文字の範囲 -> 指定の範囲が HashSet に入る");
				
		// 文字クラスの最後の ']'
		checkParse(
				"[]",
				new CharClassElement(false, TestUtils.makeHashSet()),
				"指定が空 -> HashSet は空");
		checkParse(
				"[a]",
				new CharClassElement(false, TestUtils.makeHashSet('a')),
				"1 文字だけ -> 指定の 1 文字が HashSet に入る");
		checkParse(
				"[a-]",
				new CharClassElement(false, TestUtils.makeHashSet('a', '-')),
				"1 文字と '-' だけ -> 指定の 1 文字と '-' が HashSet に入る");

		// 文字クラスの最後の ']' がない
		checkParse(
				"[",
				new CharClassElement(false, TestUtils.makeHashSet()),
				"'[' だけ で ']' なし -> HashSet は空");
		checkParse(
				"[a",
				new CharClassElement(false, TestUtils.makeHashSet('a')),
				"1 文字だけで ']' なし -> 指定の 1 文字が HashSet に入る");
		checkParse(
				"[a-",
				new CharClassElement(false, TestUtils.makeHashSet('a', '-')),
				"1 文字と '-' だけで ']' なし -> 指定の 1 文字と '-' が HashSet に入る");

		// 指定以外を表わす '^'
		checkParse(
				"[^abc]",
				new CharClassElement(true, TestUtils.makeHashSet('a', 'b', 'c')),
				"'[' の後の '^' -> 指定の文字が含まれないになる");
		checkParse(
				"[\\^]",
				new CharClassElement(false, TestUtils.makeHashSet('^')),
				"'[' の後の '^' をエスケープ -> '^' が文字として指定される");
		checkParse(
				"[^]",
				new CharClassElement(true, TestUtils.makeHashSet()),
				"'^' だけで文字の指定なし -> HashSet は空");
		checkParse(
				"[^",
				new CharClassElement(true, TestUtils.makeHashSet()),
				"'[' と '^' だけ で ']' なし -> HashSet は空");
		checkParse(
				"[a^]",
				new CharClassElement(false, TestUtils.makeHashSet('a', '^')),
				"'[' の直後でない '^' -> '^' は文字として扱われ HashSet に入る");

		// 同じ文字が複数回含まれている場合。
		checkParse(
				"[aaa-b]",
				new CharClassElement(false, TestUtils.makeHashSet('a', 'b')),
				"同じ文字が複数回含まれている -> ある文字は HashSet に 1 つだけ入る");
	}
	
	private void checkParse(String pattern, CharClassElement expected, String message) {
		StringEnumerator strEnum = new StringEnumerator(pattern, 0);
		CharClassParser target = new CharClassParser();
		CharClassElement actual = target.parse(strEnum);
		CharClassElementTest.check(expected, actual, message);
	}

}
