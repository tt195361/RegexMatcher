package com.gmail.tt195361.Regex;

/**
 * �����񒆂̕����̗񋓂ɉ����āA�J�n�ʒu�̈ړ��A���݈ʒu�̔��ʂ��s���܂��B
 *
 * <p>
 * ���̃N���X�ɂ��āA�ȉ��̍��ڂ����ɐ������܂��B
 * <ul>
 *   <li><a href="#�����o�[�ϐ�">�����o�[�ϐ�</a></li>
 *   <li><a href="#���݈ʒu�̈ړ�">���݈ʒu�̈ړ�</a></li>
 *   <li><a href="#�����̗�">�����̗�</a></li>
 *   <li><a href="#���݈ʒu�̔���">���݈ʒu�̔���</a></li>
 *   <li><a href="#���\�b�h�̓����">���\�b�h�̓����</a></li>
 *   <li><a href="#�񋓎q���g���v���O����">�񋓎q���g���v���O����</a></li>
 *   <li><a href="#�J�n�ʒu�̈ړ�">�J�n�ʒu�̈ړ�</a></li>
 *   <li><a href="#��Ԃ̕ۑ��ƕ���">��Ԃ̕ۑ��ƕ���</a></li>
 * </ul>
 * 
 * <h1 id="�����o�[�ϐ�">�����o�[�ϐ�</h1>
 * <p>
 * ���̃N���X�́A�ȉ��̃����o�[�ϐ����g���܂��B
 * <table
 * 		border="1" rules="all"
 * 		summary="StringEnumerator �N���X�̃����o�[�ϐ�">
 *   <tr bgcolor="lightgray">
 *     <th align="center">���O</th>
 *     <th align="center">�^</th>
 *     <th align="center">����</th>
 *   </tr>
 *   <tr>
 *     <td>{@code _str}</td>
 *     <td>{@code final String}</td>
 *     <td>�񋓂��镶�����ێ����܂��B
 *     	   ���̒l�́A�R���X�g���N�^�ōŏ��ɐݒ肵�����Ƃ͕ύX���Ȃ��̂ŁA
 *     	   {@code final} �ɂ��܂��B</td>
 *   </tr>
 *   <tr>
 *     <td>{@code _length}</td>
 *     <td>{@code final int}</td>
 *     <td>�񋓂��镶����̒����ł��B
 *   	   ���̒l�͂��̃N���X�̒��ŉ��񂩎g���̂ŁA�ϐ��ɓ���Ă����܂��B
 *   	   ���̒l���ύX���Ȃ��̂ŁA{@code final} �ɂ��܂��B</td>
 *   </tr>
 *   <tr>
 *     <td>{@code _startIndex}</td>
 *     <td>{@code int}</td>
 *     <td>�񋓂̊J�n�ʒu��ێ����܂��B</td>
 *   </tr>
 *   <tr>
 *     <td>{@code _currentIndex}</td>
 *     <td>{@code int}</td>
 *     <td>�񋓂̌��݈ʒu�̒l�ł��B</td>
 *   </tr>
 * </table>
 *
 * <h1 id="���݈ʒu�̈ړ�">���݈ʒu�̈ړ�</h1>
 * <p>
 * �񋓂̌��݈ʒu��ێ�����ϐ� {@code _currentIndex} �̒l�́A
 * {@link #moveNext()} ���\�b�h�Ŏ��̈ʒu�֐i�߂܂��B
 * ���̃��\�b�h���ĂԂ��ƂɁA���݈ʒu�͎��̂悤�Ɉړ����܂��B
 * <ul>
 *   <li>�w��̊J�n�ʒu����A�����񒆂̂��ꂼ��̕��������Ɉړ����܂��B</li>
 *   <li>�����񒆂̕�����񋓂������Ƃ́A������̏I���Ɉړ����܂��B
 *       ������̍Ō��\�킷 {@link EndOfStringPattern ���K�\�����^ '$'} �́A
 *       ���̈ʒu�ƈ�v���܂��B</li>
 *   <li>���ɂ��̃��\�b�h���ĂԂƁA������̏I�����z�����ʒu�Ɉړ����܂��B</li>
 *   <li>����ɂ��̃��\�b�h���Ă�ł��A���݈ʒu�͂���ȏ�ړ����܂���B</li>
 * </ul>
 * <p>
 * {@link #moveNext()} ���\�b�h�̎������A���Ɏ����܂��B
 * {@link #hasCurrentOrEnd()} ���\�b�h���g���āA
 * ���݈ʒu�ɕ��������邩�A���邢�͕�����̏I���ł��邱�Ƃ𒲂ׁA
 * ���̏�������������Ă���Ό��݈ʒu�� 1 �i�߂܂��B
 * <pre>
        void moveNext() {
            if (hasCurrentOrEnd()) {
                setCurrentIndex(_currentIndex + 1);
            }
        }
 * </pre>
 * 
 * <h1 id="�����̗�">�����̗�</h1>
 * <p>
 * �����񒆂̕�����񋓂����ɏ������邽�߁A�ȉ��̃��\�b�h��񋟂��܂��B
 * <table
 * 		border="1" rules="all"
 * 		summary="������񋓂��郁�\�b�h">
 *   <tr bgcolor="lightgray">
 *     <th align="center">���O</th>
 *     <th align="center">����</th>
 *   </tr>
 *   <tr>
 *     <td>{@link #hasCurrent()}</td>
 *     <td>���݈ʒu�ɕ��������邩�ǂ����𒲂ׂ܂��B</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #hasCurrentOrEnd()}</td>
 *     <td>���݈ʒu�ɕ��������邩�A���邢�́A���݈ʒu��
 *         ������̏I��肩�𒲂ׂ܂��B���̃��\�b�h�́A
 *         ���K�\�����^�̈�v�𒲂ׂ�Ƃ��Ɏg���܂��B
 *         {@link EndOfStringPattern ���K�\�����^ '$'} ��
 *         ������̏I���ƈ�v���邽�߂ł��B</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #getCurrent()}</td>
 *     <td>���݈ʒu�̕������擾���܂��B</td>
 *   </tr>
 * </table>
 * <p>
 * {@link #hasCurrent()} ���\�b�h�̎����́A���̂悤�ɂȂ�܂��B
 * ���݈ʒu�����������o�[�ϐ� {@code _currentIndex} ���A
 * {@code 0} �ȏ�A���� {@code _length} �����ł��邱�Ƃ𒲂ׂ܂��B
 * <pre>
        boolean hasCurrent() {
            return 0 <= _currentIndex && _currentIndex < _length;
        }
 * </pre>
 * <p>
 * {@link #hasCurrentOrEnd()} ���\�b�h�́A���Ɏ����悤�ɁA
 * {@link #hasCurrent()} �� {@link #isEnd()} ���g���܂��B
 * <pre>
        boolean hasCurrentOrEnd() {
            return hasCurrent() || isEnd();
        }
 * </pre>
 *
 * <h1 id="���݈ʒu�̔���">���݈ʒu�̔���</h1>
 * <p>
 * ���K�\�����^�Ƃ̈�v�𒲂ׂ�ꍇ��A������𐳋K�\�����^��
 * ���ĉ��߂���ꍇ�ɁA���݈ʒu��������̂ǂ��Ȃ̂��m�肽��
 * �Ƃ�������܂��B���̂��߂ɁA�ȉ��̃��\�b�h��p�ӂ��܂����B
 * <table
 * 		border="1" rules="all"
 * 		summary="���݈ʒu�𔻕ʂ��郁�\�b�h">
 *   <tr bgcolor="lightgray">
 *     <th align="center">���O</th>
 *     <th align="center">����</th>
 *     <th align="center">����</th>
 *   </tr>
 *   <tr>
 *     <td>{@link #isStart()}</td>
 *     <td>���݈ʒu��������̍ŏ����ǂ����𒲂ׂ܂��B</td>
 *     <td>{@code  _currentIndex == 0}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #isLast()}</td>
 *     <td>���݈ʒu��������̏I��肩�ǂ����𒲂ׂ܂��B</td>
 *     <td>{@code _currentIndex == _length - 1}</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #isEnd()}</td>
 *     <td>���݈ʒu��������̏I��肩�ǂ����𒲂ׂ܂��B</td>
 *     <td>{@code _currentIndex == _length}</td>
 *   </tr>
 * </table>
 *
 * <h1 id="���\�b�h�̓����">���\�b�h�̓����</h1>
 * <p>
 * ������ "abc" ���A�J�n�ʒu 0 ����񋓂���ꍇ�A
 * {@link #moveNext} ���\�b�h�Ō��݈ʒu���ړ����邽�тɁA
 * <a href="#�����̗�">�����̗�</a> ��
 * <a href="#���݈ʒu�̔���">���݈ʒu�̔���</a> ��
 * ���\�b�h���Ԃ��l�͈ȉ��̂悤�ɂȂ�܂��B
 * <table
 * 		border="1" rules="all"
 * 		summary="moveNext() �̌Ăяo���Ɗe���\�b�h�̖߂�l">
 *   <tr�@align="center">
 *     <th>{@link #moveNext}<br>�Ăяo����</th>
 *     <th>{@link #hasCurrent}</th>
 *     <th>{@link #hasCurrentOrEnd}</th>
 *     <th>{@link #getCurrent}</th>
 *     <th>{@link #isStart}</th>
 *     <th>{@link #isLast}</th>
 *     <th>{@link #isEnd}</th>
 *   </tr>
 *   <tr align="center">
 *     <td>0</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>'a'</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>1</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>'b'</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>2</td>
 *     <td>{@code true}</td>
 *     <td>{@code true}</td>
 *     <td>'c'</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code false}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>3</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *     <td>{@code '\0'}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code true}</td>
 *   </tr>
 *   <tr align="center">
 *     <td>4</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code '\0'}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *     <td>{@code false}</td>
 *   </tr>
 * </table>
 *
 * <h1 id="�񋓎q���g���v���O����">�񋓎q���g���v���O����</h1>
 * <p>
 * �񋓎q���g���ĕ��������ɏ�������v���O�����́A
 * ���̂悤�Ȍ`�ɂȂ�܂��B
 * <pre>
        StringEnumerator strEnum;
        while (strEnum.hasCurrent()) {          // ���݈ʒu�ɕ���������ԌJ��Ԃ��B
            char c = strEnum.getCurrent();      // ���݈ʒu�̕������擾����B
            if (c == '^' && strEnum.isStart())  // '^' ��������̍ŏ����ǂ������ׂ�B
                . . . . .
            strEnum.moveNext();                 // ���̈ʒu�Ɉړ�����B
        }
 * </pre>
 * <p>
 * �񋓎q���g�킸�ɕ�����Ƃ��̌��݈ʒu�𒼐ڈ����ƁA
 * �v���O�����͈ȉ��̂悤�ɂȂ�܂��B
 * <pre>
        String s;
        for (int i = 0; i < s.length(); ++i) {  // �����񒆂̂��ׂĂ̕����ɂ��āA
            char c = s.charAt(i);               // ���݈ʒu�̕������擾����B
            if (c == '^' && i == 0)             // '^' ��������̍ŏ����ǂ������ׂ�B
                . . . . .
        }
 * </pre>
 * <p>
 * �񋓎q�𓱓����邱�Ƃɂ��A�v�f��񋓂��ď�������v���O�����́A
 * �v�f��񋓂���񋓎q�ƁA�񋓂����v�f���������镔���ɕ�����܂��B
 * ���̂悤�Ɋ֘A������̂��܂Ƃ߂ď����𕪊����邱�ƂŁA
 * �v���O�����̂��ꂼ��̕��������P���ɂȂ�A
 * �v���O���������ǂ݂₷���A��藝�����₷���ł��܂��B
 *
 * <h1 id="�J�n�ʒu�̈ړ�">�J�n�ʒu�̈ړ�</h1>
 * <p>
 * ������̊J�n�ʒu���ړ�����ɂ́A�ȉ��̃��\�b�h���g���܂��B
 * <table
 * 		border="1" rules="all"
 * 		summary="�J�n�ʒu���ړ����郁�\�b�h">
 *   <tr bgcolor="lightgray">
 *     <th align="center">���O</th>
 *     <th align="center">����</th>
 *   </tr>
 *   <tr>
 *     <td>{@link #hasValidStart()}</td>
 *     <td>���݂̊J�n�ʒu���L�����ǂ�����Ԃ��܂��B
 *         ���^ {@code $} ��������̍Ō�Ɉ�v����̂ŁA
 *         ������̍Ō�܂ł��L���ȊJ�n�ʒu�ɂȂ�܂��B</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #moveNextStart()}</td>
 *     <td>�J�n�ʒu�����Ɉړ����܂��B</td>
 *   </tr>
 * </table>
 * <p>
 * ���ׂĂ̊J�n�ʒu�����ɒ��ׂ�v���O�����́A�ȉ��̂悤�ɂȂ�܂��B
 * <pre>
        StringEnumerator strEnum;
        while (strEnum.hasValidStart()) {   // ���݂̊J�n�ʒu���L���ȊԌJ��Ԃ��B
            . . . . .                       // ���݂̊J�n�ʒu���珈������B
            strEnum.moveNextStart();        // ���̊J�n�ʒu�Ɉړ�����B
        }
 * </pre>
 *
 * <h1 id="��Ԃ̕ۑ��ƕ���">��Ԃ̕ۑ��ƕ���</h1>
 * <p>
 * TODO: ������ǉ�����B
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
	 * @return ���݈ʒu�ɕ���������� {@code true} ���A
	 * 		�������Ȃ���� {@code false} ��Ԃ��܂��B
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _length;
	}
	
	/**
	 * ���݈ʒu�ɕ��������邩�A���邢�́A���݈ʒu��������̏I��肩�𒲂ׂ܂��B
	 * 
	 * @return�@���݈ʒu�ɕ��������邩�A���邢�́A
	 * 		������̏I���ł���ꍇ�� {@code true} ���A
	 * 		����ȊO�̏ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	boolean hasCurrentOrEnd() {
		return hasCurrent() || isEnd();
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
	 * @return ���݈ʒu�̕�����Ԃ��܂��B���݈ʒu��������̏I���A
	 * 		���邢�͏I�����z�����ʒu�ɂ���A
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
		return _currentIndex == _length;
	}
	
	/**
	 * �J�n�ʒu���L�����ǂ�����Ԃ��܂��B
	 * 
	 * @return ���݂̊J�n�ʒu���L���Ȃ�� {@code true} ���A
	 * 		�����Ȃ�� {@code false} ��Ԃ��܂��B
	 */
	boolean hasValidStart() {
		// '$' �͕�����̍Ō�Ɉ�v����̂ŁA������̏I���܂Œ��ׂ܂��B
		return _startIndex <= _length;
	}
	
	/**
	 * �J�n�ʒu�����Ɉړ����܂��B���킹�Č��݈ʒu���J�n�ʒu�Ɉړ����܂��B
	 * �J�n�ʒu�������ȏꍇ�́A�J�n�ʒu�ƌ��݈ʒu�͂��̂܂܈ړ����܂���B
	 */
	void moveNextStart() {
		if (hasValidStart()) {
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
	 * �񋓎q�̌��݂̏�Ԃ�ۑ����� {@link EnumeratorState} �N���X��
	 * �I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @return �񋓎q�̌��݂̏�Ԃ�ۑ����� {@link EnumeratorState} �N���X��
	 * 		�I�u�W�F�N�g��Ԃ��܂��B
	 */
	EnumeratorState saveState() {
		return new EnumeratorState(_currentIndex);
	}

	/**
	 * �񋓎q�̏�Ԃ��w��� {@link EnumeratorState} �N���X�̃I�u�W�F�N�g��
	 * �ۑ�������Ԃɖ߂��܂��B
	 * 
	 * @param state �񋓎q����Ԃ�ۑ����� {@link EnumeratorState} �N���X��
	 * 		�I�u�W�F�N�g�ł��B
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
