package com.gmail.tt195361.Regex;

/**
 * ���K�\�����^��\�킷����������߂������ʂ��i�[���܂��B
 */
class PatternParseResult {

	// �����o�[�ϐ�: ���K�\�����^�ƁA��O�̐��K�\�����^���폜���邩�ǂ�����ێ����܂��B
	private final RegexPattern _pattern;
	private final boolean _removeLastPat;
	
	/**
	 * �p�����[�^�Ŏw��̒l������ {@link PatternParseResult} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @param pattern ���߂������ʂ����Ƃɍ쐬�������K�\�����^��\�킷
	 * 		{@link RegexPattern} �N���X�̃I�u�W�F�N�g�ł��B
	 * @param removeLastPat ��O�̐��K�\�����^���폜���邩�ǂ������w�肷��
	 * 		{@code boolean} �̒l�ł��B
	 */
	PatternParseResult(RegexPattern pattern, boolean removeLastPat) {
		_pattern = pattern;
		_removeLastPat = removeLastPat;
	}
	
	/**
	 * ���߂������ʂ����Ƃɍ쐬�������K�\�����^�̃I�u�W�F�N�g���擾���܂��B
	 * 
	 * @return ���K�\�����^��\�킷 {@link RegexPattern} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	RegexPattern getPattern() {
		return _pattern;
	}
	
	/**
	 * ��O�̐��K�\�����^���폜���邩�ǂ������w�肷��l���擾���܂��B
	 * 
	 * @return ��O�̐��K�\�����^���폜���邩�ǂ������w�肷�� {@code boolean} �̒l�ł��B
	 */
	boolean removeLastPat() {
		return _removeLastPat;
	}
	
	/**
	 * {@link PatternParseResult} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format(
				"_pattern=%s, _removeLastPat=%s",
				_pattern.toString(), Boolean.toString(_removeLastPat));
	}
}
