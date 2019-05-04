package com.gmail.tt195361.Regex;

import org.junit.Test;

public class CharClassParserTest {

	@Test
	public void testParse() {
		// 1 文字
		checkParse(
				"[a]",
				new CharClassPattern(false, TestUtils.makeHashSet('a')),
				"1 文字だけ -> 指定の 1 文字が HashSet に入る");
		checkParse(
				"[abc]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c')),
				"1 文字ずつ指定 -> 指定したそれぞれの文字が HashSet に入る");
		
		// 範囲指定
		checkParse(
				"[x-z]",
				new CharClassPattern(false, TestUtils.makeHashSet('x', 'y', 'z')),
				"範囲指定 -> 指定した範囲の文字が HashSet に入る");
		checkParse(
				"[j-h]",
				new CharClassPattern(false, TestUtils.makeHashSet('h', 'i', 'j')),
				"範囲の順序が逆 -> 順序が逆でも指定の範囲が HashSet に入る");
		checkParse(
				"[-a]",
				new CharClassPattern(false, TestUtils.makeHashSet('-', 'a')),
				"'-' が最初 -> '-' はリテラル");
		checkParse(
				"[^-a]",
				new CharClassPattern(true, TestUtils.makeHashSet('-', 'a')),
				"'-' が '^' の後 -> '-' はリテラル");
		checkParse(
				"[a-]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', '-')),
				"1 文字と '-' だけ -> 指定の 1 文字と '-' が HashSet に入る");
		
		// 1 文字と範囲の組み合わせ
		checkParse(
				"[ae-gi]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', 'e', 'f', 'g', 'i')),
				"1 文字と範囲の組み合わせ -> それぞれの文字と範囲が HashSet に入る");

		// 文字のエスケープ
		checkParse(
				"[\\-^]",
				new CharClassPattern(false, TestUtils.makeHashSet('\\', ']', '^')),
				"文字のエスケープは取り扱わない --> '\' から  '^' の範囲指定になる");
				
		// 文字クラスの最後の ']'
		checkParse(
				"[]]",
				new CharClassPattern(false, TestUtils.makeHashSet(']')),
				"']' が最初 -> ']' として取り扱われる");
		checkParse(
				"[^]]",
				new CharClassPattern(true, TestUtils.makeHashSet(']')),
				"']' が '^' の次 -> '^' の次でも ']' として取り扱われる");
		checkParse(
				"[",
				new CharClassPattern(false, TestUtils.makeHashSet()),
				"'[' だけ で ']' なし -> HashSet は空");
		checkParse(
				"[]",
				new CharClassPattern(false, TestUtils.makeHashSet(']')),
				"'[' の直後に ']' だけ -> 1 文字目の ']' はリテラル");
		checkParse(
				"[a",
				new CharClassPattern(false, TestUtils.makeHashSet('a')),
				"1 文字だけで ']' なし -> 指定の 1 文字が HashSet に入る");
		checkParse(
				"[a-",
				new CharClassPattern(false, TestUtils.makeHashSet('a', '-')),
				"1 文字と '-' だけで ']' なし -> 指定の 1 文字と '-' が HashSet に入る");
		checkParse(
				"[^",
				new CharClassPattern(true, TestUtils.makeHashSet()),
				"'[' と '^' だけ で ']' なし -> HashSet は空");
		checkParse(
				"[^]",
				new CharClassPattern(true, TestUtils.makeHashSet(']')),
				"'^' だけで文字の指定なし -> '^' の後の ']' はリテラル");

		// 指定以外を表わす '^'
		checkParse(
				"[^abc]",
				new CharClassPattern(true, TestUtils.makeHashSet('a', 'b', 'c')),
				"'[' の後の '^' -> 指定の文字が含まれないになる");
		checkParse(
				"[\\^]",
				new CharClassPattern(false, TestUtils.makeHashSet('\\', '^')),
				"'[' の後の '^' をエスケープ -> 文字のエスケープは取り扱わない");
		checkParse(
				"[a^]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', '^')),
				"'[' の直後でない '^' -> '^' は文字として扱われ HashSet に入る");

		// 同じ文字が複数回含まれている場合。
		checkParse(
				"[aaa-b]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', 'b')),
				"同じ文字が複数回含まれている -> ある文字は HashSet に 1 つだけ入る");
	}
	
	private void checkParse(String pattern, CharClassPattern expected, String message) {
		StringEnumerator strEnum = StringEnumerator.makeForParse(pattern);
		CharClassParser target = new CharClassParser();
		CharClassPattern actual = target.parse(strEnum);
		CharClassPatternTest.check(expected, actual, message);
	}

}
