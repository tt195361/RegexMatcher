package com.gmail.tt195361.Regex;

/**
 * ���K�\�����\�������̕��^��\�킷���ۃN���X�ł��B
 */
abstract class RegexPattern {
	
	/**
	 * {@link RegexPattern} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 */
	protected RegexPattern() {
		//
	}
	
	/**
	 * ���̐��K�\�����^��������̌��݈ʒu�����v���邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B�Ăяo�����̌��݈ʒu�́A���̐��K�\�����^�ł�����̂Ƃ��܂��B
	 * 				�Ăяo����̌��݈ʒu�́A�Ō�Ɉ�v���邩�ǂ������ׂ����K�\�����^�ɂȂ�܂��B
	 * @param strEnum ������̗񋓎q�ł��B�Ăяo����̌��݈ʒu�́A
	 * 				���̐��K�\�����^�ƈ�v���������̎��̈ʒu�Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	abstract boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum);
}
