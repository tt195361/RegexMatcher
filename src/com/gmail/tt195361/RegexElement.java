package com.gmail.tt195361;

/**
 * ���K�\�����\�������̗v�f��\�킷���ۃN���X�ł��B
 */
abstract class RegexElement {
	
	/**
	 * {@link RegexElement} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 */
	protected RegexElement() {
		//
	}
	
	/**
	 * ���̐��K�\���v�f��������̌��݈ʒu�����v���邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @param elemEnum ���K�\���v�f�̗񋓎q�ł��B�Ăяo�����̌��݈ʒu�́A���̐��K�\���v�f�ł�����̂Ƃ��܂��B
	 * 				�Ăяo����̌��݈ʒu�́A�Ō�ɒT���������K�\���v�f�ɂȂ�܂��B
	 * @param strEnum ������̗񋓎q�ł��B�Ăяo����̌��݈ʒu�́A
	 * 				���̐��K�\���v�f�ƈ�v���������̎��̈ʒu�Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	abstract boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum);
}
