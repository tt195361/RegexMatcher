package com.gmail.tt195361.Regex;

import java.util.List;

/**
 * ���K�\���̈�̕��^��\�킷 {@link RegexPattern} �̃I�u�W�F�N�g�����ɗ񋓂���񋓎q�ł��B
 * 
 * <p>
 * �񋓎q�́A�񋓂��鍀�ڂ̃R���N�V������ێ�����ƂƂ��ɁA���̒��̈�̍��ڂ��w���������݈ʒu���Ǘ����܂��B
 * �ȉ��̃��\�b�h��p���āA�񋓎q�̌��݈ʒu����舵���A�R���N�V�����̍��ڂ�񋓂��܂��B
 * <ul>
 *   <li>{@link #hasCurrent} ���\�b�h�ŁA���݈ʒu�ɍ��ڂ����邩�ǂ����𒲂ׂ܂��B</li>
 *   <li>{@link #getCurrent} ���\�b�h�ŁA���݈ʒu�̍��ڂ��擾���܂��B</li>
 *   <li>{@link #moveNext} ���\�b�h�ŁA���݈ʒu�����̍��ڂɈړ����܂��B</li>
 * </ul>
 * <p>
 * �񋓎q�̏�Ԃ�ۑ����A�ۑ�������Ԃɕ�������ɂ́A�ȉ��̃��\�b�h���g���܂��B
 * <ul>
 *   <li>{@link #saveState} ���\�b�h�ŁA�񋓎q�̌��݂̏�Ԃ�ۑ����܂��B</li>
 *   <li>{@link #restoreState} ���\�b�h�ŁA�񋓎q��ۑ����ꂽ��Ԃɖ߂��܂��B</li>
 * </ul>
 */
class PatternEnumerator {

	// �����o�[�ϐ�: �񋓂��鐳�K�\�����^�̔z��A���̔z��̒����A���݈ʒu�ł��B
	private final RegexPattern[] _patterns;
	private final int _length;
	private int _currentIndex;
	
	/**
	 * �w��̃��X�g�̐��K�\�����^��񋓂���񋓎q���쐬���܂��B
	 * 
	 * @param patList �񋓂��鐳�K�\�����^���i�[�������X�g�ł��B
	 */
	PatternEnumerator(List<RegexPattern> patList) {
		this(patList.toArray(new RegexPattern[0]));
	}
	
	private PatternEnumerator(RegexPattern[] patterns) {
		_patterns = patterns;
		_length = patterns.length;
		_currentIndex = 0;
	}
	
	/**
	 * ���݈ʒu�ɕ��^�����邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @return ���݈ʒu�ɕ��^������ꍇ�� {@code true} ���A���^���Ȃ��ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _length;
	}
	
	/**
	 * ���݈ʒu�����̕��^�Ɉړ����܂��B���݈ʒu�ɕ��^���Ȃ��ꍇ�́A���݈ʒu������ȏ�ړ����܂���B
	 */
	void moveNext() {
		if (hasCurrent()) {
			++_currentIndex;
		}
	}
	
	/**
	 * ���݈ʒu�̕��^���擾���܂��B
	 * 
	 * @return ���݈ʒu�̕��^��Ԃ��܂��B���݈ʒu�ɕ��^���Ȃ��ꍇ�� {@code null} ��Ԃ��܂��B
	 */
	RegexPattern getCurrent() {
		if (!hasCurrent()) {
			return null;
		}
		else {
			return _patterns[_currentIndex];
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
	 * {@link PatternEnumerator} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		RegexPattern current = getCurrent();
		String currentStr = (current == null) ? "NULL" : current.toString();
		return String.format("_index=%d, current=%s", _currentIndex, currentStr);
	}
	
	/**
	 * �w��̐��K�\�����^��񋓂���񋓎q���쐬���܂��A�P�̃e�X�g�Ŏg�����߂̃��\�b�h�ł��B
	 * 
	 * @param patterns �񋓂��鐳�K�\�����^���w�肵�܂��B
	 * @return �쐬�����񋓎q��Ԃ��܂��B
	 */
	static PatternEnumerator makeForUnitTest(RegexPattern...patterns) {
		return new PatternEnumerator(patterns);
	}
}