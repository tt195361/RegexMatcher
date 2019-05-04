package com.gmail.tt195361.Regex;

/**
 * �v�f�̕��т�񋓂���񋓎q��\�킷���ۃN���X�ł��B
 *
 * @param <TElement> �񋓂���v�f�̌^���w�肵�܂��B
 */
abstract class Enumerator<TElement> {
	
	// �����o�[�ϐ�: �񋓂���v�f�̐��ƌ��݈ʒu�ł��B
	protected final int _count;
	private int _currentIndex;
	
	/**
	 * �w��̐��̗v�f��񋓂���񋓎q���쐬���܂��B
	 * 
	 * @param count �񋓂���v�f�̐��ł��B
	 */
	protected Enumerator(int count) {
		_count = count;
		setCurrentIndex(0);
	}
	
	/**
	 * �w��̈ʒu�ɂ���v�f���擾���܂��B
	 * ���ꂼ��̔h���N���X�Œ�`���钊�ۃ��\�b�h�ł��B
	 * 
	 * @param index �擾����v�f�̈ʒu���w�肵�܂��B
	 * @return �w��̈ʒu�̗v�f��Ԃ��܂��B
	 */
	protected abstract TElement getElementAt(int index);

	/**
	 * ���݂��Ȃ��v�f��\�킷�l���擾���܂��B
	 * ���ꂼ��̔h���N���X�Œ�`���钊�ۃ��\�b�h�ł��B
	 * 
	 * @return ���݂��Ȃ��v�f��\�킷�l��Ԃ��܂��B
	 */
	protected abstract TElement getNullElement();
	
	/**
	 * ���݈ʒu�̒l��ݒ肵�܂��B
	 * 
	 * @param value �ݒ肷�錻�݈ʒu�̒l�ł��B
	 */
	protected void setCurrentIndex(int value) {
		_currentIndex = value;
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
	 * ���݈ʒu�ɗv�f�����邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @return ���݈ʒu�ɗv�f������ꍇ�� {@code true} ���A
	 * 		�v�f���Ȃ��ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	boolean hasCurrent() {
		return 0 <= _currentIndex && _currentIndex < _count;
	}
	
	/**
	 * ���݈ʒu�̗v�f���擾���܂��B
	 * 
	 * @return ���݈ʒu�̗v�f��Ԃ��܂��B���݈ʒu�ɗv�f���Ȃ��ꍇ��
	 * 		{@link #getNullElement()} �Ŏ擾�����l��Ԃ��܂��B
	 */
	TElement getCurrent() {
		if (!hasCurrent()) {
			return getNullElement();
		}
		else {
			return getElementAt(_currentIndex);
		}
	}
	
	/**
	 * ���݈ʒu�����̗v�f�Ɉړ����܂��B���݈ʒu�ɗv�f���Ȃ��ꍇ�́A
	 * ���݈ʒu������ȏ�ړ����܂���B
	 */
	void moveNext() {
		if (hasCurrent()) {
			setCurrentIndex(_currentIndex + 1);
		}
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
		int current_index = state.getCurrentIndex();
		setCurrentIndex(current_index);
	}
}
