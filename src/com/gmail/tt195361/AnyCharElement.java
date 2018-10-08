package com.gmail.tt195361;

/**
 * �C�ӂ̈ꕶ����\�킷���K�\���v�f�ł��B
 */
class AnyCharElement extends RegexElement {

	/**
	 * {@link AnyCharElement} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 */
	AnyCharElement() {
		//
	}
	
	/**
	 * ���̐��K�\���v�f��������̌��݈ʒu�ƈ�v���邩�ǂ������ׂ܂��B
	 * �C�ӂ̈ꕶ���́A���݈ʒu��������̏I���łȂ���΁A�ǂ̕����Ƃ���v���܂��B
	 * 
	 * @param elemEnum ���K�\���v�f�̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu�͎��̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		if (strEnum.isEnd()) {
			return false;
		}
		else {
			strEnum.moveNext();
			return true;
		}
	}
	
	/**
	 * {@link AnyCharElement} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return "AnyChar";
	}
}
