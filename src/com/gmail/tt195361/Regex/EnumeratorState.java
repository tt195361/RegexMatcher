package com.gmail.tt195361.Regex;

/**
 * �񋓎q�̏�Ԃ�ۑ��������ł���悤�ɂ��܂��B
 * 
 * <h1 id="�񋓎q�̏�Ԃ̕ۑ��ƕ���">�񋓎q�̏�Ԃ̕ۑ��ƕ���</h1>
 * <p>
 * �񋓎q�̌��݈ʒu��ۑ����A���Ƃŕۑ������Ƃ��̏�Ԃ�
 * �������������Ƃ�����܂��B
 * ���Ƃ��΁A<a href="ClosurePattern.html#��̈�v�̎���">
 * ��̈�v�̎���</a> �ł́A{@link StringEnumerator} �̌��ʒu��
 * �쐬���A���̒����珇�Ɏc��̕��^�̈�v�𒲂ׂ܂��B
 * <p>
 * �I�u�W�F�N�g�̏�ԂƂ��ĉ���ۑ��E�������邩�ŁA
 * �ȉ��̕��@���l�����܂��B
 * <ul>
 *   <li>��� -- �I�u�W�F�N�g�̒��̏�Ԃ������l��
 *       �ۑ��E�������܂��B�ۑ�����ꍇ�́A�I�u�W�F�N�g����
 *   	 ���̒l���擾���i�[���܂��B�����́A�i�[�����l��
 *       �I�u�W�F�N�g�ɐݒ肵�A���̏�Ԃɖ߂��܂��B
 *       �񋓎q�̏ꍇ�́A���݈ʒu�̒l��ۑ��E�������܂��B</li>
 *   <li>��Ԃ��i�[����I�u�W�F�N�g -- ��Ԃ̒l���i�[����
 *       �V�����N���X���쐬���܂��B��Ԃ̕ۑ��ɂ́A
 *       ��Ԃ̒l���i�[�������̃N���X�̃I�u�W�F�N�g���쐬���܂��B
 *       �����́A���̃I�u�W�F�N�g�̒l�����̃I�u�W�F�N�g�ɖ߂��܂��B</li>
 *   <li>�ۑ��E��������I�u�W�F�N�g -- �ۑ��E�����������I�u�W�F�N�g��
 *       ���̂܂܂̌`�Ŏg���܂��B��Ԃ�ۑ����邽�߂ɁA
 *       �I�u�W�F�N�g�̕���������悤�ɂ��A�������ۑ����܂��B
 *       �����́A�����ɕۑ����ꂽ���e�𕜌�����I�u�W�F�N�g�ɐݒ肵�܂��B</li>
 * </ul>
 * <p>
 * ���ꂼ��̕��@�̕]�����A���̕\�ɂ܂Ƃ߂܂��B
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="�ۑ����@�̕]��">
 *   <tr bgcolor="lightgray" align="center">
 *     <th rowspan="2">�ϓ_</th>
 *     <th colspan="2">���</th>
 *     <th colspan="2">��Ԃ��i�[����I�u�W�F�N�g</th>
 *     <th colspan="2">�ۑ��E��������I�u�W�F�N�g</th>
 *   </tr>
 *   <tr bgcolor="lightgray" align="center">
 *     <th>�]��</th>
 *     <th>����</th>
 *     <th>�]��</th>
 *     <th>����</th>
 *     <th>�]��</th>
 *     <th>����</th>
 *   </tr>
 *   <tr valign="top">
 *     <td bgcolor="lightgray">�쐬�̎��</td>
 *     <td bgcolor="lightgreen">�Z</td>
 *     <td>��Ԃ����̂܂܎g���̂ŁA
 *         �V�����N���X�Ȃǂ͕K�v����܂���B
 *         �ۑ��E��������I�u�W�F�N�g�̃N���X��
 *         ��Ԃ̎擾�Ɛݒ�̃��\�b�h��ǉ����܂��B</td>
 *     <td bgcolor="lightblue">��</td>
 *     <td>��Ԃ��i�[����N���X���쐬���܂��B
 *         �܂��A�ۑ��E�������s���N���X�ɁA
 *         �������s�����\�b�h���K�v�ɂȂ�܂��B</td>
 *     <td bgcolor="lightgreen">�Z</td>
 *     <td>�ۑ��E��������I�u�W�F�N�g�����̂܂܎g���̂ŁA
 *         �V�����N���X�͕K�v����܂���B
 *         �������쐬���郁�\�b�h�ƁA��������
 *         ��Ԃ��R�s�[���郁�\�b�h���K�v�ɂȂ�܂��B</td>
 *   </tr>
 *   <tr valign="top">
 *     <td bgcolor="lightgray">���̌��J�͈�</td>
 *     <td bgcolor="pink">�~</td>
 *     <td>��Ԃ�ݒ肷�郁�\�b�h��ǉ�����̂ŁA
 *         �N���X�̏�Ԃ��O�����瑀��ł���悤�ɂȂ�܂��B</td>
 *     <td bgcolor="lightblue">��</td>
 *     <td>��Ԃɂ��Ă̏��̌��J�͈͂́A���̃N���X��
 *         ��Ԃ��i�[����N���X�����Ɍ��肳��܂��B
 *         �v���O�����̂��̑��̕����́A��ԂɊ֗^���܂���B</td>
 *     <td bgcolor="lightgreen">�Z</td>
 *     <td>��Ԃ͂��̃N���X�̒������Ŏ�舵���A
 *         �O���ɂ͌��J���܂���B</td>
 *   </tr>
 *   <tr valign="top">
 *     <td bgcolor="lightgray">�I�u�W�F�N�g�̎g����</td>
 *     <td bgcolor="lightgreen">�Z</td>
 *     <td>���̃N���X�{���̖����݂̂��s���܂��B
 *         ���̂��߁A�g�����͒P���ɂȂ�܂��B</td>
 *     <td bgcolor="lightgreen">�Z</td>
 *     <td>���̃N���X�̃I�u�W�F�N�g�͖{���̖����Ɏg���A
 *         �V�����쐬����N���X�̃I�u�W�F�N�g�͏�Ԃ̕ۑ��Ɏg���܂��B
 *         ���ꂼ��̃N���X�̃I�u�W�F�N�g�̖�����
 *         �P���ł͂����肵�܂��B</td>
 *     <td bgcolor="pink">�~</td>
 *     <td>���̃N���X�{���̖����Ŏg���邱�Ɖ����āA
 *         ��Ԃ�ۑ����邽�߂ɂ��g���܂��B
 *         �g�p�ړI��������̂ŁA��蕡�G�ɂȂ�܂��B</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * TODO: ������ǉ��E�ύX����B
 * <p>
 * �񋓎q�̏�Ԃ�ۑ����A�ۑ�������Ԃɕ�������ɂ́A�ȉ��̃��\�b�h���g���܂��B
 * <ul>
 *   <li>{@link PatternEnumerator#saveState} ���\�b�h�ŁA
 *       �񋓎q�̌��݂̏�Ԃ�ۑ����܂��B</li>
 *   <li>{@link PatternEnumerator#restoreState} ���\�b�h�ŁA
 *       �񋓎q��ۑ����ꂽ��Ԃɖ߂��܂��B</li>
 * </ul>
 */
class EnumeratorState {

	// �����o�[�ϐ�: �񋓎q�̌��݈ʒu�ł��B
	private final int _currentIndex;
	
	/**
	 * �p�����[�^�Ŏw��̗񋓎q�̏�Ԃ��i�[���� {@link EnumeratorState} �N���X��
	 * �I�u�W�F�N�g���쐬���܂��B
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
	 * {@link Object#equals} ���\�b�h�� {@code true} ��Ԃ��I�u�W�F�N�g�́A
	 * �����n�b�V���R�[�h��Ԃ��悤�ɂ��܂��B���̂��ߕێ������ԂɊ�Â���
	 * �n�b�V���R�[�h���쐬���܂��B
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
