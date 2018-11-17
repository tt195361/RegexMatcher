package com.gmail.tt195361.Regex;

/**
 * ���l�����Ɋւ��郁�\�b�h���W�߂��N���X�ł��B
 */
class MathUtils {
	
	// �C���X�^���X���쐬����K�v�͂Ȃ��̂ŁA�R���X�g���N�^�� private �ɂ��܂��B
	private MathUtils() {
		//
	}

	/**
	 * 2 �̕����̂����̏������ق���Ԃ��܂��B
	 * 
	 * @param ch1 1 �ڂ̕����ł��B
	 * @param ch2 2 �ڂ̕����ł��B
	 * @return�@2 �̕����̂����̂ǂ��炩�������ق���Ԃ��܂��B
	 */
	static char minCh(char ch1, char ch2) {
		return ch1 <= ch2 ? ch1 : ch2;
	}
	
	/**
	 * 2 �̕����̂����̑傫���ق���Ԃ��܂��B
	 * 
	 * @param ch1 1 �ڂ̕����ł��B
	 * @param ch2 2 �ڂ̕����ł��B
	 * @return�@2 �̕����̂����̂ǂ��炩�傫���ق���Ԃ��܂��B
	 */
	static char maxCh(char ch1, char ch2) {
		return ch1 <= ch2 ? ch2 : ch1;
	}
}
