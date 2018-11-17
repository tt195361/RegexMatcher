package com.gmail.tt195361.Regex;

import org.junit.Test;

public class CharClassParserTest {

	@Test
	public void testParse() {
		// 1 �����Ɣ͈�
		checkParse(
				"[abc]",
				new CharClassElement(false, TestUtils.makeHashSet('a', 'b', 'c')),
				"1 �������w�� -> �w�肵�����ꂼ��̕����� HashSet �ɓ���");
		checkParse(
				"[x-z]",
				new CharClassElement(false, TestUtils.makeHashSet('x', 'y', 'z')),
				"�͈͎w�� -> �w�肵���͈͂̕����� HashSet �ɓ���");
		checkParse(
				"[j-h]",
				new CharClassElement(false, TestUtils.makeHashSet('h', 'i', 'j')),
				"�͈͂̏������t -> �������t�ł��w��͈̔͂� HashSet �ɓ���");
		checkParse(
				"[ae-gi]",
				new CharClassElement(false, TestUtils.makeHashSet('a', 'e', 'f', 'g', 'i')),
				"1 �����Ɣ͈͂̑g�ݍ��킹 -> ���ꂼ��̕����Ɣ͈͂� HashSet �ɓ���");

		// �����̃G�X�P�[�v
		checkParse(
				"[\\[\\-\\]]",
				new CharClassElement(false, TestUtils.makeHashSet('[', '-', ']')),
				"�����̃G�X�P�[�v -> �G�X�P�[�v���ꂽ������ HashSet �ɓ���" +
				"'-' �̓G�X�P�[�v����Ă���̂ŁA�͈͂ł͂Ȃ� '-' �ɂȂ�");
		checkParse(
				"[\\[-\\]]",
				new CharClassElement(false, TestUtils.makeHashSet('[', '\\', ']')),
				"�G�X�P�[�v���ꂽ�����͈̔� -> �w��͈̔͂� HashSet �ɓ���");
				
		// �����N���X�̍Ō�� ']'
		checkParse(
				"[]",
				new CharClassElement(false, TestUtils.makeHashSet()),
				"�w�肪�� -> HashSet �͋�");
		checkParse(
				"[a]",
				new CharClassElement(false, TestUtils.makeHashSet('a')),
				"1 �������� -> �w��� 1 ������ HashSet �ɓ���");
		checkParse(
				"[a-]",
				new CharClassElement(false, TestUtils.makeHashSet('a', '-')),
				"1 ������ '-' ���� -> �w��� 1 ������ '-' �� HashSet �ɓ���");

		// �����N���X�̍Ō�� ']' ���Ȃ�
		checkParse(
				"[",
				new CharClassElement(false, TestUtils.makeHashSet()),
				"'[' ���� �� ']' �Ȃ� -> HashSet �͋�");
		checkParse(
				"[a",
				new CharClassElement(false, TestUtils.makeHashSet('a')),
				"1 ���������� ']' �Ȃ� -> �w��� 1 ������ HashSet �ɓ���");
		checkParse(
				"[a-",
				new CharClassElement(false, TestUtils.makeHashSet('a', '-')),
				"1 ������ '-' ������ ']' �Ȃ� -> �w��� 1 ������ '-' �� HashSet �ɓ���");

		// �w��ȊO��\�킷 '^'
		checkParse(
				"[^abc]",
				new CharClassElement(true, TestUtils.makeHashSet('a', 'b', 'c')),
				"'[' �̌�� '^' -> �w��̕������܂܂�Ȃ��ɂȂ�");
		checkParse(
				"[\\^]",
				new CharClassElement(false, TestUtils.makeHashSet('^')),
				"'[' �̌�� '^' ���G�X�P�[�v -> '^' �������Ƃ��Ďw�肳���");
		checkParse(
				"[^]",
				new CharClassElement(true, TestUtils.makeHashSet()),
				"'^' �����ŕ����̎w��Ȃ� -> HashSet �͋�");
		checkParse(
				"[^",
				new CharClassElement(true, TestUtils.makeHashSet()),
				"'[' �� '^' ���� �� ']' �Ȃ� -> HashSet �͋�");
		checkParse(
				"[a^]",
				new CharClassElement(false, TestUtils.makeHashSet('a', '^')),
				"'[' �̒���łȂ� '^' -> '^' �͕����Ƃ��Ĉ���� HashSet �ɓ���");

		// ����������������܂܂�Ă���ꍇ�B
		checkParse(
				"[aaa-b]",
				new CharClassElement(false, TestUtils.makeHashSet('a', 'b')),
				"����������������܂܂�Ă��� -> ���镶���� HashSet �� 1 ��������");
	}
	
	private void checkParse(String pattern, CharClassElement expected, String message) {
		StringEnumerator strEnum = new StringEnumerator(pattern, 0);
		CharClassParser target = new CharClassParser();
		CharClassElement actual = target.parse(strEnum);
		CharClassElementTest.check(expected, actual, message);
	}

}
