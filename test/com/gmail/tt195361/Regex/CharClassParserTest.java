package com.gmail.tt195361.Regex;

import org.junit.Test;

public class CharClassParserTest {

	@Test
	public void testParse() {
		// 1 �����Ɣ͈�
		checkParse(
				"[abc]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', 'b', 'c')),
				"1 �������w�� -> �w�肵�����ꂼ��̕����� HashSet �ɓ���");
		checkParse(
				"[x-z]",
				new CharClassPattern(false, TestUtils.makeHashSet('x', 'y', 'z')),
				"�͈͎w�� -> �w�肵���͈͂̕����� HashSet �ɓ���");
		checkParse(
				"[j-h]",
				new CharClassPattern(false, TestUtils.makeHashSet('h', 'i', 'j')),
				"�͈͂̏������t -> �������t�ł��w��͈̔͂� HashSet �ɓ���");
		checkParse(
				"[ae-gi]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', 'e', 'f', 'g', 'i')),
				"1 �����Ɣ͈͂̑g�ݍ��킹 -> ���ꂼ��̕����Ɣ͈͂� HashSet �ɓ���");

		// �����̃G�X�P�[�v
		checkParse(
				"[\\[\\-\\]]",
				new CharClassPattern(false, TestUtils.makeHashSet('[', '-', ']')),
				"�����̃G�X�P�[�v -> �G�X�P�[�v���ꂽ������ HashSet �ɓ���" +
				"'-' �̓G�X�P�[�v����Ă���̂ŁA�͈͂ł͂Ȃ� '-' �ɂȂ�");
		checkParse(
				"[\\[-\\]]",
				new CharClassPattern(false, TestUtils.makeHashSet('[', '\\', ']')),
				"�G�X�P�[�v���ꂽ�����͈̔� -> �w��͈̔͂� HashSet �ɓ���");
				
		// �����N���X�̍Ō�� ']'
		checkParse(
				"[]",
				new CharClassPattern(false, TestUtils.makeHashSet()),
				"�w�肪�� -> HashSet �͋�");
		checkParse(
				"[a]",
				new CharClassPattern(false, TestUtils.makeHashSet('a')),
				"1 �������� -> �w��� 1 ������ HashSet �ɓ���");
		checkParse(
				"[a-]",
				new CharClassPattern(false, TestUtils.makeHashSet('a', '-')),
				"1 ������ '-' ���� -> �w��� 1 ������ '-' �� HashSet �ɓ���");

		// �����N���X�̍Ō�� ']' ���Ȃ�
		checkParse(
				"[",
				new CharClassPattern(false, TestUtils.makeHashSet()),
				"'[' ���� �� ']' �Ȃ� -> HashSet �͋�");
		checkParse(
				"[a",
				new CharClassPattern(false, TestUtils.makeHashSet('a')),
				"1 ���������� ']' �Ȃ� -> �w��� 1 ������ HashSet �ɓ���");
		checkParse(
				"[a-",
				new CharClassPattern(false, TestUtils.makeHashSet('a', '-')),
				"1 ������ '-' ������ ']' �Ȃ� -> �w��� 1 ������ '-' �� HashSet �ɓ���");

		// �w��ȊO��\�킷 '^'
		checkParse(
				"[^abc]",
				new CharClassPattern(true, TestUtils.makeHashSet('a', 'b', 'c')),
				"'[' �̌�� '^' -> �w��̕������܂܂�Ȃ��ɂȂ�");
		checkParse(
				"[\\^]",
				new CharClassPattern(false, TestUtils.makeHashSet('^')),
				"'[' �̌�� '^' ���G�X�P�[�v -> '^' �������Ƃ��Ďw�肳���");
		checkParse(
				"[^]",
				new CharClassPattern(true, TestUtils.makeHashSet()),
				"'^' �����ŕ����̎w��Ȃ� -> HashSet �͋�");
		checkParse(
				"[^",
				new CharClassPattern(true, TestUtils.makeHashSet()),
				"'[' �� '^' ���� �� ']' �Ȃ� -> HashSet �͋�");
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
		StringEnumerator strEnum = new StringEnumerator(pattern, 0);
		CharClassParser target = new CharClassParser();
		CharClassPattern actual = target.parse(strEnum);
		CharClassPatternTest.check(expected, actual, message);
	}

}
