package com.gmail.tt195361.Regex;

/**
 * �w��� 1 �����Ɉ�v���鐳�K�\���v�f�ł��B
 */
class OneCharElement extends RegexElement {
	
	// �����o�[�ϐ�: �w��̕�����ێ����܂��B
	private final char _specifiedCh;
	
	/**
	 * �p�����[�^�Ŏw��̕����Ɉ�v���� {@link OneCharElement} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @param ch ���̐��K�\���v�f����v���镶�����w�肵�܂��B
	 */
	OneCharElement(char ch) {
		_specifiedCh = ch;
	}
	
	/**
	 * ���̐��K�\���v�f����v���镶�����擾���܂��B
	 * 
	 * @return ���̐��K�\���v�f����v���镶����Ԃ��܂��B
	 */
	char getSpecifiedChar() {
		return _specifiedCh;
	}
	
	/**
	 * ���̐��K�\���v�f��������̌��݈ʒu�ƈ�v���邩�ǂ������ׂ܂��B
	 * ���̐��K�\���v�f�́A���݈ʒu�ɕ���������A���̕������w�肳�ꂽ�����Ȃ�΁A��v���܂��B
	 * 
	 * @param elemEnum ���K�\���v�f�̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu�͎��̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
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
	 * {@link OneCharElement} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return "OneChar: " + Character.toString(_specifiedCh);
	}
}
