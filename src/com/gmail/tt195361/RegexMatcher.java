package com.gmail.tt195361;

/**
 * �w��̕��^�������K�\�����w��̕�����ƈ�v���邩�ǂ����𒲂ׂ܂��B
 */
public class RegexMatcher {
	
	// �����o�|�ϐ�: ���K�\����\�킷�����񂩂�쐬�������K�\���v�f�̗񋓎q�ƁA���̏�����Ԃł��B
	private final ElementEnumerator _elemEnum;
	private final EnumeratorState _initialElemEnumState;

	/**
	 * �w��̕��^�������K�\����\�킷 {@link RegexMatcher} �N���X�̃I�u�W�F�N�g�𐶐����܂��B
	 * 
	 * @param pattern ���K�\���̕��^���w�肷�镶����ł��B
	 */
	public RegexMatcher(String pattern) {
		// ���K�\���̕��^��\�킷����������߂��A�����v�f�ɕ������A���ꂼ��̗v�f��񋓂���
		// �񋓎q���쐬���܂��B
		_elemEnum = RegexParser.parse(pattern);
		// ���K�\���v�f�̗񋓎q��������Ԃɖ߂����߁A���̏�Ԃ�ۑ����܂��B
		_initialElemEnumState = _elemEnum.saveState();
	}
	
	/**
	 * ���̐��K�\�����w��̕�����ƈ�v���邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @param str ���K�\���ƈ�v���邩�ǂ����𒲂ׂ镶����ł��B
	 * @return ��v�𒲂ׂ����ʂ��i�[���� {@link MatchResult} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	public MatchResult match(String str) {
		// �w��̕�����̂��ꂼ��̈ʒu�ŁA���K�\������v���邩�ǂ����𒲂ׂ܂��B
		// ������̍Ō�Ɉ�v���邱�Ƃ�����̂ŁAStringEnumerator.getEndIndex() �܂Œ��ׂ܂��B
		int endIndex = StringEnumerator.getEndIndex(str);
		for (int startIndex = 0; startIndex <= endIndex; ++startIndex) {
			// ���K�\���v�f�̗񋓎q���ŏ��ɖ߂��A������� startIndex �̈ʒu�����v���邩���ׂ܂��B
			_elemEnum.restoreState(_initialElemEnumState);
			StringEnumerator strEnum = new StringEnumerator(str, startIndex);
			if (matchFromCurrent(_elemEnum, strEnum)) {
				String matchString = strEnum.getSubstring();
				return MatchResult.makeSuccessResult(startIndex, matchString);
			}
		}
		
		// ������̂��ׂĂ̈ʒu�Ő��K�\������v���Ȃ������̂ŁA���s�̌��ʂ��쐬���Ԃ��܂��B
		return MatchResult.makeFailResult();
	}
	
	/**
	 * ���K�\���v�f�ƕ�����̂��ꂼ��̌��݈ʒu����A���K�\���v�f��������Ɉ�v���邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @param elemEnum ���K�\���v�f�̗񋓎q�ł��B�Ăяo����̌��݈ʒu��
	 * 				�Ō�Ɉ�v�ɐ��������v�f�̎��Ɉړ����܂��B
	 * @param strEnum ������̗񋓎q�ł��B�Ăяo����̌��݈ʒu��
	 * 				���K�\���v�f�ƈ�v���������̎��̕����Ɉړ����܂��B
	 * @return�@��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	static boolean matchFromCurrent(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		// ���K�\���v�f�����݂���ԁA�J��Ԃ��܂��B
		while (elemEnum.hasCurrent()) {
			// ���K�\���v�f���܂����݂���ɂ�������炸�A������̏I�����z���Ă���΁A��v�͎��s�ł��B
			if (!strEnum.hasCurrentOrEnd()) {
				return false;
			}
			
			// ���݂̐��K�\���v�f��������Ɉ�v���邩�ǂ������ׂ܂��B��v���Ȃ���Ύ��s�ł��B
			RegexElement currentElem = elemEnum.getCurrent();
			if (!currentElem.oneMatch(elemEnum, strEnum)) {
				return false;
			}
			
			// ���݂̐��K�\���v�f�̈�v�ɐ��������̂ŁA���̐��K�\���v�f�Ɉړ����܂��B
			elemEnum.moveNext();
		}

		// ���ׂĂ̐��K�\���v�f��������̌��݈ʒu�����v�����̂ŁA������̈�v�ɐ����ł��B
		return true;
	}
}
