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
	 * �w��̕������܂܂�Ă��Ȃ����Ƃ��w�肷��l���擾���܂��B
	 * 
	 * @return �w��̕������܂܂�Ă��Ȃ����Ƃ��w�肷��l��Ԃ��܂��B
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
	 * �����N���X�́A���݈ʒu�ɕ���������A���A���݈ʒu�̕����� {@link #getNotContains} ��
	 * {@link #getCharSet} �̕Ԃ��l�ƁA�ȉ��̂����ꂩ�̏����𖞂����ꍇ�Ɉ�v���܂��B
	 * <ul>
	 *   <li>{@link #getNotContains} �� {@code false} ��Ԃ��ꍇ�A
	 *   	���݈ʒu�̕����� {@link #getCharSet} �̕Ԃ� {@link HashSet} �Ɋ܂܂�Ă���B</li>
	 *   <li>{@link #getNotContains} �� {@code true} ��Ԃ��ꍇ�A
	 *   	���݈ʒu�̕����� {@link #getCharSet} �̕Ԃ� {@link HashSet} �Ɋ܂܂�Ă��Ȃ��B</li>
	 * </ul>
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
		
		char ch = strEnum.getCurrent();
		if (!matchConditionSatisfied(ch)) {
			return false;
		}

		strEnum.moveNext();
		return true;
	}
	
	// �w��̕�������v�̏����𖞂����Ă��邩�ǂ����𒲂ׂ܂��B
	private boolean matchConditionSatisfied(char ch) {
		// ^ �͔r���I�_���a�ŁA"false ^ true" ���邢�� "true ^ false" �̂Ƃ��A���ʂ� true �ɂȂ�܂��B
		return _notContains ^ _charSet.contains(ch);
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
