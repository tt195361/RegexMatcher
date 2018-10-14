package com.gmail.tt195361;

/**
 * ���K�\���v�f��\�킷����������߂������ʂ��i�[���܂��B
 */
class ElementParseResult {

	// �����o�[�ϐ�: ���K�\���v�f�ƁA�Ō�̐��K�\���v�f���폜���邩�ǂ�����ێ����܂��B
	private final RegexElement _element;
	private final boolean _removeLastElem;
	
	/**
	 * �p�����[�^�Ŏw��̒l������ {@link ElementParseResult} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @param element ���߂������ʂ����Ƃɍ쐬�������K�\���v�f��\�킷
	 * 		{@link RegexElement} �N���X�̃I�u�W�F�N�g�ł��B
	 * @param removeLastElem �Ō�̐��K�\���v�f���폜���邩�ǂ������w�肷��
	 * 		{@code boolean} �̒l�ł��B
	 */
	ElementParseResult(RegexElement element, boolean removeLastElem) {
		_element = element;
		_removeLastElem = removeLastElem;
	}
	
	/**
	 * ���߂������ʂ����Ƃɍ쐬�������K�\���v�f�̃I�u�W�F�N�g���擾���܂��B
	 * 
	 * @return ���K�\���v�f��\�킷 {@link RegexElement} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	RegexElement getElement() {
		return _element;
	}
	
	/**
	 * �Ō�̐��K�\���v�f���폜���邩�ǂ������w�肷��l���擾���܂��B
	 * 
	 * @return �Ō�̐��K�\���v�f���폜���邩�ǂ������w�肷�� {@code boolean} �̒l�ł��B
	 */
	boolean removeLastElem() {
		return _removeLastElem;
	}
	
	/**
	 * {@link ElementParseResult} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format(
				"_element=%s, _removeLastElem=%s",
				_element.toString(), Boolean.toString(_removeLastElem));
	}
}
