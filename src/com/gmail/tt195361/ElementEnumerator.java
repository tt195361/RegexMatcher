package com.gmail.tt195361;

import java.util.List;

/**
 * ���K�\���̈�̗v�f��\�킷 {@link RegexElement} �̃I�u�W�F�N�g�����ɗ񋓂���񋓎q�ł��B
 * 
 * <p>
 * �񋓎q�́A�񋓂���v�f�̃R���N�V������ێ�����ƂƂ��ɁA���̒��̈�̗v�f���w���������݈ʒu���Ǘ����܂��B
 * �ȉ��̃��\�b�h��p���āA�񋓎q�̌��݈ʒu����舵���A�R���N�V�����̗v�f��񋓂��܂��B
 * <ul>
 *   <li>{@link hasCurrent} ���\�b�h�ŁA���݈ʒu�ɗv�f�����邩�ǂ����𒲂ׂ܂��B</li>
 *   <li>{@link getCurrent} ���\�b�h�ŁA���݈ʒu�̗v�f���擾���܂��B</li>
 *   <li>{@link moveNext} ���\�b�h�ŁA���݈ʒu�����̗v�f�Ɉړ����܂��B</li>
 * </ul>
 * <p>
 * �񋓎q�̏�Ԃ�ۑ����A�ۑ�������Ԃɕ�������ɂ́A�ȉ��̃��\�b�h���g���܂��B
 * <ul>
 *   <li>{@link saveState} ���\�b�h�ŁA�񋓎q�̌��݂̏�Ԃ�ۑ����܂��B</li>
 *   <li>{@link restoreState} ���\�b�h�ŁA�񋓎q��ۑ����ꂽ��Ԃɖ߂��܂��B</li>
 * </ul>
 */
class ElementEnumerator {

	// �����o�[�ϐ�: �񋓂��鐳�K�\���v�f�̔z��A���̔z��̒����A���݈ʒu�ł��B
	private final RegexElement[] _elements;
	private final int _length;
	private int _currentIndex;
	
	/**
	 * �w��̃��X�g�̐��K�\���v�f��񋓂���񋓎q���쐬���܂��B
	 * 
	 * @param elemList �񋓂��鐳�K�\���v�f���i�[�������X�g�ł��B
	 */
	ElementEnumerator(List<RegexElement> elemList) {
		this(elemList.toArray(new RegexElement[0]));
	}
	
	private ElementEnumerator(RegexElement[] elements) {
		_elements = elements;
		_length = elements.length;
		_currentIndex = 0;
	}
	
	/**
	 * ���݈ʒu�ɗv�f�����邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @return ���݈ʒu�ɗv�f������ꍇ�� {@code true} ���A�v�f���Ȃ��ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _length;
	}
	
	/**
	 * ���݈ʒu�����̗v�f�Ɉړ����܂��B���݈ʒu�ɗv�f���Ȃ��ꍇ�́A���݈ʒu������ȏ�ړ����܂���B
	 */
	void moveNext() {
		if (hasCurrent()) {
			++_currentIndex;
		}
	}
	
	/**
	 * ���݈ʒu�̗v�f���擾���܂��B
	 * 
	 * @return ���݈ʒu�̗v�f��Ԃ��܂��B���݈ʒu�ɗv�f���Ȃ��ꍇ�� {@code null} ��Ԃ��܂��B
	 */
	RegexElement getCurrent() {
		if (!hasCurrent()) {
			return null;
		}
		else {
			return _elements[_currentIndex];
		}
	}
	
	/**
	 * �񋓎q�̌��݂̏�Ԃ�ۑ����� {@link EnumeratorState} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @return �񋓎q�̌��݂̏�Ԃ�ۑ����� {@link EnumeratorState} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	EnumeratorState saveState() {
		return new EnumeratorState(_currentIndex);
	}
	
	/**
	 * �񋓎q�̏�Ԃ��w��� {@link EnumeratorState} �N���X�̃I�u�W�F�N�g���ۑ�������Ԃɖ߂��܂��B
	 * 
	 * @param state �񋓎q����Ԃ�ۑ����� {@link EnumeratorState} �N���X�̃I�u�W�F�N�g�ł��B
	 */
	void restoreState(EnumeratorState state) {
		_currentIndex = state.getCurrentIndex();
	}
	
	/**
	 * {@link ElementEnumerator} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		RegexElement current = getCurrent();
		String currentStr = (current == null) ? "NULL" : current.toString();
		return String.format("_index=%d, current=%s", _currentIndex, currentStr);
	}
	
	/**
	 * �w��̐��K�\���v�f��񋓂���񋓎q���쐬���܂��A�P�̃e�X�g�Ŏg�����߂̃��\�b�h�ł��B
	 * 
	 * @param elements �񋓂��鐳�K�\���v�f���w�肵�܂��B
	 * @return �쐬�����񋓎q��Ԃ��܂��B
	 */
	static ElementEnumerator makeForUnitTest(RegexElement...elements) {
		return new ElementEnumerator(elements);
	}
}
