package com.gmail.tt195361.Regex;

/**
 * �����񒆂̕��������ɗ񋓂���񋓎q�ł��B
 * 
 * <p>
 * �񋓎q�̍��ڂ̗񋓂Ə�Ԃ̕ۑ��ƕ����ɂ��ẮA{@link PatternEnumerator} ��
 * �������Q�Ƃ��Ă��������B
 * <p>
 * ������̗񋓎q�̌��݈ʒu�́A���ɐ�������悤�ɁA���ꂼ��̕��������ɗ񋓂������ƁA
 * ������̏I���Ɉړ����A���ɏI�����z�����ʒu�Ɉړ����܂��B
 * ������̏I���ɂ́A���K�\���̕��^ {@code '$'} ����v���܂��B
 * ���̂��߁A��v�̌����͕�����̏I���܂Ŏ��{���A�I�����z�����ʒu�ŏI�����܂��B
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
 * 		summary="moveNext() �̌Ăяo���Ɗe���\�b�h�̖߂�l">
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
 *     <td>0</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>0</td>
 *     <td>'a'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>1</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>1</td>
 *     <td>'b'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>2</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>2</td>
 *     <td>'c'</td>
 *   </tr>
 *   <tr align="center">
 *     <td>3</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>3</td>
 *     <td>{@code '\0'}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>4</td>
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
	
	// �����o�[�ϐ�: ������A������̒����A�J�n�ʒu�A���݈ʒu��ێ����܂��B
	private final String _str;
	private final int _length;
	private int _startIndex;
	private int _currentIndex;
	
	/**
	 * �w��̕�����̂��ꂼ��̕�����񋓂���񋓎q���쐬���܂��B
	 * 
	 * @param str ���ꂼ��̕�����񋓂��镶����ł��B
	 */
	StringEnumerator(String str) {
		_str = str;
		_length = str.length();
		setStartAndCurrentIndex(0);
	}
	
	private void setStartAndCurrentIndex(int value) {
		_startIndex = _currentIndex = value;
	}
	
	private void setCurrentIndex(int value) {
		_currentIndex = value;
	}
	
	/**
	 * ���݈ʒu�����̕����Ɉړ����܂��B���݈ʒu��������̏I�����z���Ă���ꍇ�A
	 * ���݈ʒu�͂���ȏ�ړ����܂���B
	 */
	void moveNext() {
		if (hasCurrentOrEnd()) {
			setCurrentIndex(_currentIndex + 1);
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
	 * ���݂̊J�n�ʒu�����v�𒲂ׂ��邩�ǂ�����Ԃ��܂��B
	 * 
	 * @return ���݂̊J�n�ʒu�����v�𒲂ׂ���Ȃ�� {@code true} ���A
	 * 		���ׂ��Ȃ���� {@code false} ��Ԃ��܂��B
	 */
	boolean hasCurrentStart() {
		// '$' �͕�����̍Ō�Ɉ�v����̂ŁA������̏I���܂Œ��ׂ܂��B
		return _startIndex <= _length;
	}
	
	/**
	 * �J�n�ʒu�����̕����Ɉړ����܂��B�J�n�ʒu����v�𒲂ׂ���͈͂��z���Ă���ꍇ�A
	 * �J�n�ʒu�͂���ȏ�ړ����܂���B
	 */
	void moveNextStart() {
		if (hasCurrentStart()) {
			setStartAndCurrentIndex(_startIndex + 1);
		}
	}
	
	/**
	 * �J�n�ʒu�̒l���擾���܂��B
	 * 
	 * @return �J�n�ʒu�̒l��Ԃ��܂��B
	 */
	int getStartIndex() {
		return _startIndex;
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
		setCurrentIndex(state.getCurrentIndex());
	}
	
	/**
	 * {@link StringEnumerator} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format(
				"_startIndex=%d, _currentIndex=%d, rest=%s",
				_startIndex, _currentIndex, getRest());
	}
	
	private String getRest() {
		return _str.substring(getEndIndex());
	}
	
	private int getEndIndex() {
		return Math.min(_currentIndex, _length);
	}
	
	/**
	 * �J�n�ʒu��ݒ肵�܂��B�P�̃e�X�g�Ŏg�����߂̃��\�b�h�ł��B
	 * ���݈ʒu���J�n�ʒu�Ɠ����l�ɐݒ肳��܂��B
	 * 
	 * @param startIndex �ݒ肷��J�n�ʒu�̒l���w�肵�܂��B
	 */
	void setStartIndexForUnitTest(int startIndex) {
		setStartAndCurrentIndex(startIndex);
	}
	
	/**
	 * ���݈ʒu��ݒ肵�܂��B�P�̃e�X�g�Ŏg�����߂̃��\�b�h�ł��B
	 * 
	 * @param currentIndex �ݒ肷�錻�݈ʒu�̒l���w�肵�܂��B
	 */
	void setCurrentIndexForUnitTest(int currentIndex) {
		setCurrentIndex(currentIndex);
	}
}
