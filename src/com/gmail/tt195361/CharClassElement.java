package com.gmail.tt195361;

import java.util.HashSet;

/**
 * �w��̕������܂܂�Ă��邩�ǂ�������舵�������N���X��\�킷���K�\���v�f�ł��B
 */
class CharClassElement extends RegexElement {

	// �����o�[�ϐ�: �܂܂�Ă��Ȃ��̎w��Ǝw��̕������i�[���܂��B
	private final boolean _notContains;
	private final HashSet<Character> _charSet;
	
	/**
	 * �p�����[�^�Ŏw��̓��e��\�킷 {@link CharClassElement} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @param notContains �w��̕������܂܂�Ă��Ȃ����Ƃ��w�肵�܂��B
	 * @param charSet �w��̕������i�[���� {@link HashSet} �ł��B
	 */
	CharClassElement(boolean notContains, HashSet<Character> charSet) {
		_notContains = notContains;
		_charSet = charSet;
	}
	
	/**
	 * �w��̕������܂܂�Ă��Ȃ����Ƃ������l���擾���܂��B
	 * 
	 * @return �w��̕������܂܂�Ă��Ȃ����Ƃ������l��Ԃ��܂��B
	 */
	boolean getNotContains() {
		return _notContains;
	}
	
	/**
	 * �w��̕������i�[���� {@link HashSet} ���擾���܂��B
	 * 
	 * @return �w��̕������i�[���� {@link HashSet} ��Ԃ��܂��B
	 */
	HashSet<Character> getCharSet() {
		return _charSet;
	}
	
	/**
	 * ���̐��K�\���v�f��������̌��݈ʒu�����v���邩�ǂ����𒲂ׂ܂��B
	 * �����N���X�́A���݈ʒu�̕������w��̕����Ɋ܂܂�Ă��邩�ǂ����ň�v�𔻒f���܂��B
	 * 
	 * @param elemEnum ���K�\���v�f�̗񋓎q�ł��B�Ăяo����̌��݈ʒu�͈ړ����܂���B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu�͎��̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		char ch = strEnum.getCurrent();
		
		// ^ �͔r���I�_���a�ŁA"true ^ false" ���邢�� "false ^ true" �̂Ƃ��A���ʂ� true �ɂȂ�܂��B
		if (_charSet.contains(ch) ^ _notContains) {
			strEnum.moveNext();
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * {@link CharClassElement} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format(
				"CharClassElement: _notContains=%s, _charSet=%s",
				Boolean.toString(_notContains), _charSet.toString());
	}
}
