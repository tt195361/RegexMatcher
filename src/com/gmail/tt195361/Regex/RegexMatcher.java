package com.gmail.tt195361.Regex;

/**
 * �w��̕��^�������K�\�����w��̕�����ƈ�v���邩�ǂ����𒲂ׂ܂��B
 */
public class RegexMatcher {
	
	// �����o�|�ϐ�: ���K�\����\�킷�����񂩂�쐬�������K�\�����^�̗񋓎q�ƁA���̏�����Ԃł��B
	private final PatternEnumerator _patEnum;
	private final EnumeratorState _initialPatEnumState;

	/**
	 * �w��̕��^�������K�\����\�킷 {@link RegexMatcher} �N���X�̃I�u�W�F�N�g�𐶐����܂��B
	 * 
	 * @param pattern ���K�\���̕��^���w�肷�镶����ł��B
	 */
	public RegexMatcher(String pattern) {
		// ���K�\���̕��^��\�킷����������߂��A���̕��^�ɕ������A
		// ���̕��^�����ɗ񋓂���񋓎q���쐬���܂��B
		_patEnum = RegexParser.parse(pattern);
		// ���K�\�����^�̗񋓎q��������Ԃɖ߂����߁A���̏�Ԃ�ۑ����܂��B
		_initialPatEnumState = _patEnum.saveState();
	}
	
	/**
	 * ���̐��K�\�����w��̕�����ƈ�v���邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @param str ���K�\���ƈ�v���邩�ǂ����𒲂ׂ镶����ł��B
	 * @return ��v�𒲂ׂ����ʂ��i�[���� {@link MatchResult} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	public MatchResult match(String str) {
		StringEnumerator strEnum = new StringEnumerator(str);

		// �w��̕�����ɒ��ׂ���J�n�ʒu�����݂���ԁA��v�𒲂ׂ܂��B
		while (strEnum.hasCurrentStart()) {

			// ������͌��݂̊J�n�ʒu����A���^�͍ŏ�����A���Ɉ�v�𒲂ׂ܂��B
			_patEnum.restoreState(_initialPatEnumState);
			if (matchFromCurrent(_patEnum, strEnum)) {
				// ��v�ɐ�������΁A���̌��ʂ�Ԃ��܂��B
				return MatchResult.makeSuccessResult(strEnum);
			}

			// ��v���Ȃ������ꍇ�́A������̊J�n�ʒu�����ֈړ����܂��B
			strEnum.moveNextStart();
		}
		
		// ������̂��ׂĂ̊J�n�ʒu�Ő��K�\������v���Ȃ������̂ŁA���s�̌��ʂ�Ԃ��܂��B
		return MatchResult.makeFailResult();
	}
	
	/**
	 * ���K�\�����^�ƕ�����̂��ꂼ��̌��݈ʒu����A���K�\�����^��������Ɉ�v���邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B�Ăяo����̌��݈ʒu��
	 * 				�Ō�Ɉ�v�ɐ����������^�̎��Ɉړ����܂��B
	 * @param strEnum ������̗񋓎q�ł��B�Ăяo����̌��݈ʒu��
	 * 				���K�\�����^�ƈ�v���������̎��̕����Ɉړ����܂��B
	 * @return�@��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	static boolean matchFromCurrent(PatternEnumerator patEnum, StringEnumerator strEnum) {
		// ���K�\�����^�����݂���ԁA�J��Ԃ��܂��B
		while (patEnum.hasCurrent()) {
			// ���K�\�����^���܂����݂���ɂ�������炸�A������̏I�����z���Ă���΁A��v�͎��s�ł��B
			if (!strEnum.hasCurrentOrEnd()) {
				return false;
			}
			
			// ���݂̐��K�\�����^��������Ɉ�v���邩�ǂ������ׂ܂��B��v���Ȃ���Ύ��s�ł��B
			RegexPattern currentPat = patEnum.getCurrent();
			if (!currentPat.oneMatch(patEnum, strEnum)) {
				return false;
			}
			
			// ���݂̐��K�\�����^�̈�v�ɐ��������̂ŁA���̐��K�\�����^�Ɉړ����܂��B
			patEnum.moveNext();
		}

		// ���ׂĂ̐��K�\�����^��������̌��݈ʒu�����v�����̂ŁA�����ł��B
		return true;
	}
}
