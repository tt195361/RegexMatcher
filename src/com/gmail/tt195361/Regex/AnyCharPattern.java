package com.gmail.tt195361.Regex;

/**
 * �C�ӂ� 1 �����Ɉ�v���鐳�K�\�����^�ł��B
 *
 * <h1 id="�C�ӂ� 1 �����̈�v">�C�ӂ� 1 �����̈�v</h1>
 * <p>
 * �C�ӂ� 1 �����́A������̗񋓎q�̌��݈ʒu�ɕ���������΁A
 * ���ꂪ�ǂ̕������ɂ͂�����炸��v���܂��B
 * ��v����΁A������̌��݈ʒu�����ɐi�߂܂��B
 * �v���O�����͎��̂悤�ɂȂ�܂��B
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            if (!strEnum.hasCurrent()) {
                return false;
            }

            strEnum.moveNext();
            return true;
        }
 * </pre>
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
	 * �C�ӂ� 1 �����́A���݈ʒu�ɕ���������΁A�ǂ̕����Ƃ���v���܂��B
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu��
	 * 				���̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A
	 * 			���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		if (!strEnum.hasChar()) {
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
