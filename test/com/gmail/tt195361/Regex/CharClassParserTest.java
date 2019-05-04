package com.gmail.tt195361.Regex;

import org.junit.Test;

public class CharClassParserTest {

	@Test
	public void testParse() {
		// 1 ����
		checkParse(
				"[a]",
				new CharClassPattern(false, TestUtils.makeHashSet('a')),
				"1 �������� -> �w��� 1 ������ HashSet �ɓ���");
		checkParse(
				"[abc]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c')),
				"1 �������w�� -> �w�肵�����ꂼ��̕����� HashSet �ɓ���");
		
		// �͈͎w��
		checkParse(
				"[x-z]",
				new CharClassPattern(false, TestUtils.makeHashSet('x', 'y', 'z')),
				"�͈͎w�� -> �w�肵���͈͂̕����� HashSet �ɓ���");
		checkParse(
				"[j-h]",
				new CharClassPattern(false, TestUtils.makeHashSet('h', 'i', 'j')),
				"�͈͂̏������t -> �������t�ł��w��͈̔͂� HashSet �ɓ���");
		checkParse(
				"[-a]",
				new CharClassPattern(false, TestUtils.makeHashSet('-', 'a')),
				"'-' ���ŏ� -> '-' �̓��e����");
		checkParse(
				"[^-a]",
				new CharClassPattern(true, TestUtils.makeHashSet('-', 'a')),
				"'-' �� '^' �̌� -> '-' �̓��e����");
		checkParse(
				"[a-]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', '-')),
				"1 ������ '-' ���� -> �w��� 1 ������ '-' �� HashSet �ɓ���");
		
		// 1 �����Ɣ͈͂̑g�ݍ��킹
		checkParse(
				"[ae-gi]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', 'e', 'f', 'g', 'i')),
				"1 �����Ɣ͈͂̑g�ݍ��킹 -> ���ꂼ��̕����Ɣ͈͂� HashSet �ɓ���");

		// �����̃G�X�P�[�v
		checkParse(
				"[\\-^]",
				new CharClassPattern(false, TestUtils.makeHashSet('\\', ']', '^')),
				"�����̃G�X�P�[�v�͎�舵��Ȃ� --> '\' ����  '^' �͈͎̔w��ɂȂ�");
				
		// �����N���X�̍Ō�� ']'
		checkParse(
				"[]]",
				new CharClassPattern(false, TestUtils.makeHashSet(']')),
				"']' ���ŏ� -> ']' �Ƃ��Ď�舵����");
		checkParse(
				"[^]]",
				new CharClassPattern(true, TestUtils.makeHashSet(']')),
				"']' �� '^' �̎� -> '^' �̎��ł� ']' �Ƃ��Ď�舵����");
		checkParse(
				"[",
				new CharClassPattern(false, TestUtils.makeHashSet()),
				"'[' ���� �� ']' �Ȃ� -> HashSet �͋�");
		checkParse(
				"[]",
				new CharClassPattern(false, TestUtils.makeHashSet(']')),
				"'[' �̒���� ']' ���� -> 1 �����ڂ� ']' �̓��e����");
		checkParse(
				"[a",
				new CharClassPattern(false, TestUtils.makeHashSet('a')),
				"1 ���������� ']' �Ȃ� -> �w��� 1 ������ HashSet �ɓ���");
		checkParse(
				"[a-",
				new CharClassPattern(false, TestUtils.makeHashSet('a', '-')),
				"1 ������ '-' ������ ']' �Ȃ� -> �w��� 1 ������ '-' �� HashSet �ɓ���");
		checkParse(
				"[^",
				new CharClassPattern(true, TestUtils.makeHashSet()),
				"'[' �� '^' ���� �� ']' �Ȃ� -> HashSet �͋�");
		checkParse(
				"[^]",
				new CharClassPattern(true, TestUtils.makeHashSet(']')),
				"'^' �����ŕ����̎w��Ȃ� -> '^' �̌�� ']' �̓��e����");

		// �w��ȊO��\�킷 '^'
		checkParse(
				"[^abc]",
				new CharClassPattern(true, TestUtils.makeHashSet('a', 'b', 'c')),
				"'[' �̌�� '^' -> �w��̕������܂܂�Ȃ��ɂȂ�");
		checkParse(
				"[\\^]",
				new CharClassPattern(false, TestUtils.makeHashSet('\\', '^')),
				"'[' �̌�� '^' ���G�X�P�[�v -> �����̃G�X�P�[�v�͎�舵��Ȃ�");
		checkParse(
				"[a^]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', '^')),
				"'[' �̒���łȂ� '^' -> '^' �͕����Ƃ��Ĉ���� HashSet �ɓ���");

		// ����������������܂܂�Ă���ꍇ�B
		checkParse(
				"[aaa-b]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', 'b')),
				"����������������܂܂�Ă��� -> ���镶���� HashSet �� 1 ��������");
	}
	
	private void checkParse(String pattern, CharClassPattern expected, String message) {
		StringEnumerator strEnum = StringEnumerator.makeForParse(pattern);
		CharClassParser target = new CharClassParser();
		CharClassPattern actual = target.parse(strEnum);
		CharClassPatternTest.check(expected, actual, message);
	}

}
