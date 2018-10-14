package com.gmail.tt195361;

/**
 * �񋓎q�̏�Ԃ��i�[���܂��B
 */
class EnumeratorState {

	// �����o�[�ϐ�: �񋓎q�̌��݈ʒu�ł��B
	private final int _currentIndex;
	
	/**
	 * �p�����[�^�Ŏw��̗񋓎q�̏�Ԃ��i�[���� {@link EnumeratorState} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @param currentIndex ��Ԃ��i�[����񋓎q�̌��݈ʒu�̒l�ł��B
	 */
	EnumeratorState(int currentIndex) {
		_currentIndex = currentIndex;
	}
	
	/**
	 * �ۑ������񋓎q�̌��݈ʒu�̒l���擾���܂��B
	 * 
	 * @return�@�񋓎q�̌��݈ʒu�̒l��Ԃ��܂��B
	 */
	int getCurrentIndex() {
		return _currentIndex;
	}
	
	/**
	 * ���̃I�u�W�F�N�g�Ƒ��̃I�u�W�F�N�g�����������ǂ������r���܂��B
	 * {@link EnumeratorState} �N���X�̃I�u�W�F�N�g�́A�ʂ̃I�u�W�F�N�g�ł�
	 * �ێ������Ԃ������Ȃ�Γ��������̂Ƃ��܂��B
	 * 
	 * @return�@���̃I�u�W�F�N�g���w��̃I�u�W�F�N�g�Ɠ������ꍇ�� {@code true}�A
	 * 		�������Ȃ��ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof EnumeratorState)) {
			return false;
		}
		
		EnumeratorState that = (EnumeratorState)obj;
		return this._currentIndex == that._currentIndex;
	}
	
	/**
	 * �I�u�W�F�N�g�̃n�b�V���R�[�h�̒l���擾���܂��B
	 * {@link equals} ���\�b�h�� {@code true} ��Ԃ��I�u�W�F�N�g�́A�����n�b�V���R�[�h��Ԃ�
	 * �悤�ɂ��܂��B���̂��ߕێ������ԂɊ�Â��ăn�b�V���R�[�h���쐬���܂��B
	 * 
	 * @return ���̃I�u�W�F�N�g�̃n�b�V���R�[�h�̒l��Ԃ��܂��B
	 */
	@Override
	public int hashCode() {
		return _currentIndex;
	}
	
	/**
	 * {@link EnumeratorState} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format("_index=%d", _currentIndex);
	}
}
