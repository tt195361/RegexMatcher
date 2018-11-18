package com.gmail.tt195361.Regex;

/**
 * ������̍Ō�Ɉ�v���鐳�K�\�����^�ł��B
 */
class EndOfStringPattern extends RegexPattern {
	
	/**
	 * {@link EndOfStringPattern} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 */
	EndOfStringPattern() {
		//
	}

	/**
	 * ���̐��K�\�����^��������̌��݈ʒu�ƈ�v���邩�ǂ������ׂ܂��B
	 * ���̐��K�\�����^�́A������̌��݈ʒu���Ō�ł���΁A��v���܂��B
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @param strEnum ������̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		return strEnum.isEnd();
	}
	
	/**
	 * {@link EndOfStringPattern} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return "EndOfString";
	}
}
