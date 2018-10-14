package com.gmail.tt195361;

/**
 * ������̍Ō�Ɉ�v���鐳�K�\���v�f�ł��B
 */
class EndOfStringElement extends RegexElement {
	
	/**
	 * {@link EndOfStringElement} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 */
	EndOfStringElement() {
		//
	}

	/**
	 * ���̐��K�\���v�f��������̌��݈ʒu�ƈ�v���邩�ǂ������ׂ܂��B
	 * ���̐��K�\���v�f�͌��݈ʒu��������̏I���ł���΁A��v���܂��B
	 * 
	 * @param elemEnum ���K�\���v�f�̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @param strEnum ������̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		return strEnum.isEnd();
	}
	
	/**
	 * {@link EndOfStringElement} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return "EndOfString";
	}
}
