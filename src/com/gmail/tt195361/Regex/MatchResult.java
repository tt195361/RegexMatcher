package com.gmail.tt195361.Regex;

/**
 * ���K�\����������ƈ�v���邩�ǂ����𒲂ׂ����ʂ��i�[���܂��B
 */
public class MatchResult {

	/**
	 * ��v�ɐ��������ꍇ�̌��ʂ��쐬���܂��B
	 * 
	 * @param startIndex ��v���J�n����������̃C���f�b�N�X�ł��B
	 * @param matchString ���K�\������v���������̕�����ł��B
	 * @return �p�����[�^�Ŏw�肵�����e������v�ɐ����������ʂ��i�[���� {@link MatchResult} �N���X��
	 * 		�I�u�W�F�N�g���쐬���ĕԂ��܂��B
	 */
	static MatchResult makeSuccessResult(int startIndex, String matchString) {
		return new MatchResult(true, startIndex, matchString);
	}

	/**
	 * ��v�Ɏ��s�����ꍇ�̌��ʂ��쐬���܂��B
	 * 
	 * @return ��v�Ɏ��s�������ʂ��i�[���� {@link MatchResult} �N���X�̃I�u�W�F�N�g���쐬���ĕԂ��܂��B
	 */
	static MatchResult makeFailResult() {
		return new MatchResult(false, -1, null);
	}
	
	private final boolean _isSuccess;
	private final int _startIndex;
	private final String _matchString;
	
	// �X�R�[�v�� private �ɂ��� ���ڍ쐬�����Ȃ��悤�ɂ��܂��B
	// makeSuccessResult() ���邢�� makeFailResult() �ō쐬���܂��B
	private MatchResult(boolean isSuccess, int startIndex, String matchString) {
		_isSuccess = isSuccess;
		_startIndex = startIndex;
		_matchString = matchString;
	}
	
	/**
	 * ���K�\����������Ƃ̈�v�ɐ����������ǂ������擾���܂��B
	 * 
	 * @return�@��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	public boolean isSucceess() {
		return _isSuccess;
	}
	
	/**
	 * ��v���J�n����������̃C���f�b�N�X���擾���܂��B
	 * 
	 * @return ��v���J�n����������̃C���f�b�N�X��Ԃ��܂��B��v�Ɏ��s�����ꍇ�� {@code -1} ��Ԃ��܂��B
	 */
	public int getStartIndex() {
		return _startIndex;
	}
	
	/**
	 * ���K�\������v���������̕�������擾���܂��B
	 * 
	 * @return ���K�\������v���������̕������Ԃ��܂��B��v�Ɏ��s�����ꍇ�� {@code null} ��Ԃ��܂��B
	 */
	public String getMatchString() {
		return _matchString;
	}
	
	/**
	 * {@link MatchResult} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format(
				"_isSuccess=%s, _startIndex=%d, _matchString=%s",
				Boolean.toString(_isSuccess), _startIndex, _matchString);
	}
}
