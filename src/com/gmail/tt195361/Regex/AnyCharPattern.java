package com.gmail.tt195361.Regex;

/**
 * �C�ӂ̈ꕶ���Ɉ�v���鐳�K�\�����^�ł��B
 */
class AnyCharPattern extends RegexPattern {

	/**
	 * {@link AnyCharPattern} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 */
	AnyCharPattern() {
		//
	}
	
	/**
	 * ���̐��K�\�����^��������̌��݈ʒu�ƈ�v���邩�ǂ������ׂ܂��B
	 * �C�ӂ̈ꕶ���́A���݈ʒu�ɕ���������΁A�ǂ̕����Ƃ���v���܂��B
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

		strEnum.moveNext();
		return true;
	}
	
	/**
	 * {@link AnyCharPattern} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return "AnyChar";
	}
}
