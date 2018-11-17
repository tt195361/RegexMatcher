package com.gmail.tt195361.Regex;

/**
 * �����񒆂̕�����񋓂���񋓎q�ł��B
 * 
 * <p>
 * �񋓎q�̗v�f�̗񋓂Ə�Ԃ̕ۑ��ƕ����ɂ��ẮA{@link ElementEnumerator} ��
 * �������Q�Ƃ��Ă��������B
 * <p>
 * ������̗񋓎q�̌��݈ʒu�́A���ɐ�������悤�ɁA���ꂼ��̕��������ɗ񋓂������ƁA
 * ������̏I���Ɉړ����A���ɏI�����z�����ʒu�Ɉړ����܂��B
 * <ul>
 *   <li>���݈ʒu�͕����񒆂̂��ꂼ��̕��������Ɉړ����܂��B
 *   	���̈ʒu�ł� {@link #hasCurrent} �� {@link #hasCurrentOrEnd} ��
 *   	{@code true} ��Ԃ��܂��B�܂��A�ŏ��̕����ł� {@link #isStart} ���A
 *   	�Ō�̕����ł� {@link #isLast} �� {@code true} ��Ԃ��܂��B</li>
 *   <li>���ׂĂ̕�����񋓂������Ƃ́A������̏I���Ɉړ����܂��B
 *   	���̈ʒu�ł� {@link #isEnd} �� {@link #hasCurrentOrEnd} ��
 *   	{@code true} ��Ԃ��܂��B</li>
 *   <li>����Ɉړ�����ƁA������̏I�����z�����ʒu�Ɉړ����܂��B
 *   	���̈ʒu�ł�  {@link #hasCurrent}, {@link #isEnd}, 
 *   	{@link #hasCurrentOrEnd} �̂��ׂĂ� {@code false} ��Ԃ��܂��B</li>
 * </ul>
 * <p>
 * {@link #getCurrentIndex} �́A���݈ʒu�̒l���擾���܂��B
 * �܂� {@link #getCurrent} �́A���݈ʒu�̕�����Ԃ��܂��B
 * <p>
 * ������ "abc" ���A�J�n�ʒu 0 ����񋓂���ꍇ�A{@link #moveNext} ���\�b�h��
 * ���݈ʒu���ړ����邽�тɁA���ꂼ��̃��\�b�h���Ԃ��l�͈ȉ��̂悤�ɂȂ�܂��B
 * <table
 * 		border="1" rules="all"
 * 		summary="{@link #moveNext} �̌Ăяo���Ɗe���\�b�h�̖߂�l">
 *   <tr�@align="center">
 *     <th>{@link #moveNext}<br>�Ăяo����</th>
 *     <th>{@link #hasCurrent}</th>
 *     <th>{@link #hasCurrentOrEnd}</th>
 *     <th>{@link #isStart}</th>
 *     <th>{@link #isLast}</th>
 *     <th>{@link #isEnd}</th>
 *     <th>{@link #getCurrentIndex}</th>
 *     <th>{@link #getCurrent}</th>
 *   </tr>
 *   <tr align="center">
 *     <td>0 ��</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>0</td>
 *     <td>'a'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>1 ��</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>1</td>
 *     <td>'b'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>2 ��</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>2</td>
 *     <td>'c'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>3 ��</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>3</td>
 *     <td>{@code '\0'}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>4 ��</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>4</td>
 *     <td>{@code '\0'}</td>
 *   </tr>
 * </table>
 */
class StringEnumerator {
	
	// �����o�[�ϐ�: ������A�J�n�ʒu�A������̒����A���݈ʒu��ێ����܂��B
	private final String _str;
	private final int _startIndex;
	private final int _length;
	private int _currentIndex;
	
	/**
	 * �w��̕�����̂��ꂼ��̕������w��̊J�n�ʒu����񋓂���񋓎q���쐬���܂��B
	 * 
	 * @param str ���ꂼ��̕�����񋓂��镶����ł��B
	 * @param startIndex �񋓂̊J�n�ʒu���w�肵�܂��B������̍ŏ��̕����̈ʒu�� 0 �ɂȂ�܂��B
	 */
	StringEnumerator(String str, int startIndex) {
		_str = str;
		_startIndex = startIndex;
		_length = str.length();
		_currentIndex = startIndex;
	}
	
	/**
	 * ���݈ʒu�����̕����Ɉړ����܂��B���݈ʒu��������̏I�����z���Ă���ꍇ�A
	 * ���݈ʒu�͂���ȏ�ړ����܂���B
	 */
	void moveNext() {
		if (hasCurrentOrEnd()) {
			++_currentIndex;
		}
	}
	
	/**
	 * ���݈ʒu�ɕ��������邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @return ���݈ʒu�ɕ���������� {@code true} ���A�������Ȃ���� {@code false} ��Ԃ��܂��B
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _length;
	}
	
	/**
	 * ���݈ʒu�ɕ��������邩�A���邢�́A���݈ʒu��������̏I��肩�𒲂ׂ܂��B
	 * 
	 * @return�@���݈ʒu�ɕ��������邩�A���邢�́A������̏I���ł���ꍇ�� {@code true} ���A
	 * 		����ȊO�̏ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	boolean hasCurrentOrEnd() {
		return hasCurrent() || isEnd();
	}
	
	/**
	 * ���݈ʒu��������̍ŏ����ǂ����𒲂ׂ܂��B
	 * 
	 * @return ���݈ʒu��������̍ŏ��Ȃ�� {@code true} ���A
	 * 		�ŏ��łȂ���� {@code false} ��Ԃ��܂��B
	 */
	boolean isStart() {
		return _currentIndex == 0;
	}
	
	/**
	 * ���݈ʒu��������̍Ō�̕������ǂ����𒲂ׂ܂��B
	 * 
	 * @return ���݈ʒu��������̍Ō�̕����Ȃ�� {@code true} ���A
	 * 		�Ō�łȂ���� {@code false} ��Ԃ��܂��B
	 */
	boolean isLast() {
		return _currentIndex == _length - 1;
	}
	
	/**
	 * ���݈ʒu��������̏I��肩�ǂ����𒲂ׂ܂��B
	 * 
	 * @return ���݈ʒu��������̏I���Ȃ�� {@code true} ���A
	 * 		�I���łȂ���� {@code false} ��Ԃ��܂��B
	 */
	boolean isEnd() {
		return _length == _currentIndex;
	}
	
	/**
	 * �w��̕�����̏I���ʒu�̒l���擾���܂��B
	 * 
	 * @param str �I���ʒu�̒l���擾���镶����ł��B
	 * @return �w��̕�����̏I���ʒu�̒l��Ԃ��܂��B
	 */
	static int getEndIndex(String str) {
		return str.length();
	}

	/**
	 * ���݈ʒu�̒l���擾���܂��B
	 * 
	 * @return ���݈ʒu�̒l��Ԃ��܂��B
	 */
	int getCurrentIndex() {
		return _currentIndex;
	}

	/**
	 * ���݈ʒu�̕������擾���܂��B
	 * 
	 * @return ���݈ʒu�̕�����Ԃ��܂��B���݈ʒu��������̏I���A���邢�͏I�����z�����ʒu�ɂ���A
	 * 		���̈ʒu�ɕ������Ȃ��ꍇ�� {@code '\0'} ��Ԃ��܂��B
	 */
	char getCurrent() {
		if (!hasCurrent()) {
			return '\0';
		}
		else {
			return _str.charAt(_currentIndex);
		}
	}
	
	/**
	 * �J�n�ʒu���猻�݈ʒu�܂ł̕�����������擾���܂��B
	 * 
	 * @return �J�n�ʒu���猻�݈ʒu�܂ł̕����������Ԃ��܂��B
	 */
	String getSubstring() {
		return _str.substring(_startIndex, getEndIndex());
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
	 * {@link StringEnumerator} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format("_currentIndex=%d, rest=%s", _currentIndex, getRest());
	}
	
	private String getRest() {
		return _str.substring(getEndIndex());
	}
	
	private int getEndIndex() {
		return Math.min(_currentIndex, _length);
	}
}
