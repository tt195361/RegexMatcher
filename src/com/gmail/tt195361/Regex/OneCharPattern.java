package com.gmail.tt195361.Regex;

/**
 * �w��� 1 �����Ɉ�v���鐳�K�\�����^�ł��B
 */
class OneCharPattern extends RegexPattern {
	
	// �����o�[�ϐ�: �w��̕�����ێ����܂��B
	private final char _specifiedCh;
	
	/**
	 * �p�����[�^�Ŏw��̕����Ɉ�v���� {@link OneCharPattern} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @param ch ���̐��K�\�����^����v���镶�����w�肵�܂��B
	 */
	OneCharPattern(char ch) {
		_specifiedCh = ch;
	}
	
	/**
	 * ���̐��K�\�����^����v���镶�����擾���܂��B
	 * 
	 * @return ���̐��K�\�����^����v���镶����Ԃ��܂��B
	 */
	char getSpecifiedChar() {
		return _specifiedCh;
	}
	
	/**
	 * ���̐��K�\�����^��������̌��݈ʒu�ƈ�v���邩�ǂ������ׂ܂��B
	 * ���̐��K�\�����^�́A���݈ʒu�ɕ���������A���̕������w�肳�ꂽ�����Ȃ�΁A��v���܂��B
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu�͎��̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		if (!strEnum.hasCurrent()) {
			return false;
		}
		
		char actualCh = strEnum.getCurrent();
		if (actualCh != _specifiedCh) {
			return false;
		}

		strEnum.moveNext();
		return true;
	}
	
	/**
	 * {@link OneCharPattern} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return "OneChar: " + Character.toString(_specifiedCh);
	}
}
