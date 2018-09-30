package com.gmail.tt195361;

/**
 * �w��̃p�^�[���������K�\�����w��̕�����ƈ�v���邩�ǂ����𒲂ׂ܂��B
 */
public class RegexMatcher {
	
	// �����o�|�ϐ�: ���K�\����\�킷�����񂩂�쐬�������K�\���v�f�̗񋓎q�ƁA���̏�����Ԃł��B
	private final ElementEnumerator _elemEnum;
	private final EnumeratorState _initialElemEnumState;

	/**
	 * �w��̃p�^�[���������K�\����\�킷 {@link RegexMatcher} �N���X�̃I�u�W�F�N�g�𐶐����܂��B
	 * 
	 * <p>
	 * ���̃v���O�����ł́A���K�\���p�^�[�����̂��ꂼ��̕������ȉ��̂悤�ɉ��߂��܂��B
	 * <ul>
	 *   <li>{@code .} -- �C�ӂ� 1 �����ƈ�v���܂��B</li>
	 *   <li>{@code ^} -- ���K�\���p�^�[���̐擪�ɂ���ꍇ�́A������̐擪�ƈ�v���܂��B
	 *   		����ȊO�̏ꍇ�A���̕������g�ƈ�v���܂��B</li>
	 *   <li>{@code $} -- ���K�\���p�^�[���̍Ō�ɂ���ꍇ�́A������̖����ƈ�v���܂��B
	 *   		����ȊO�̏ꍇ�́A���̕������g�ƈ�v���܂��B</li>
	 *   <li>{@code [ ]} -- {@code [ ]} ���Ŏw��̕����̒��� 1 �����ƈ�v���܂��B�����͈ȉ��̂悤�Ɏw�肵�܂��B
	 *     <ul>
	 *       <li>���� -- �w��̕������܂߂܂��B���Ƃ��΁A {@code abc} �́A
	 *       		{@code a}, {@code b}, {@code c} �̂��ꂼ��̕������w�肵�܂��B</li>
	 *       <li>�J�n����{@code -}�I������ -- �J�n�����ƏI�������� {@code -} �ŋ�؂�ƁA
	 *       		���͈̔͂̕������w�肵�܂��B���Ƃ��΁A{@code A-Z} �͉p�啶��
	 *       		({@code A}, {@code B}, ..., {@code Z}) ���A{@code 0-9} �͐���
	 *       		({@code 0}, {@code 1}, ..., {@code 9}) ��\�킵�܂��B</li>
	 *     </ul>
	 *   </li>
	 *   <li>{@code [^ ]} -- {@code [ ]} ���Ŏw��̕����̒��� 1 �����ȊO�ƈ�v���܂��B</li>
	 *   <li>{@code *} -- ���̑O�̗v�f�� 0 ��ȏ�̌J��Ԃ��ƈ�v���܂��B���Ƃ��΁A{@code a*} ��
	 *   			{@code a} �� 0 ��ȏ�̌J��Ԃ��A{@code .*} �͔C�ӂ̕����� 0 ��ȏ��
	 *   			�J�Ԃ��A{@code [0-9]*} �͐����� 0 ��ȏ�̌J��Ԃ��ƈ�v���܂��B</li>
	 *   <li>{@code \} -- ���̌�̕��������̕������g�Ƃ��Ď�舵���܂��B{@code [ ]} ���ł��g���܂��B
	 *   	���Ƃ��� {@code \.} �́A{@code .} �̓��ʂȈӖ���ł������A{@code .} �Ƃ��Ď�舵���܂��B
	 *   	�܂��A{@code \\} �� {@code \} ���Ӗ����܂��B</li>
	 *   <li>��L�ȊO�̕����́A���̕������g�ƈ�v���܂��B</li>
	 * </ul>
	 * 
	 * @param pattern�@���K�\���̃p�^�[�����w�肷�镶����ł��B
	 */
	public RegexMatcher(String pattern) {
		// ���K�\���̃p�^�[����\�킷����������߂��A�����v�f�ɕ������A���ꂼ��̗v�f��񋓂���񋓎q���쐬���܂��B
		_elemEnum = RegexParser.parse(pattern);
		// ���K�\���v�f�̗񋓎q��������Ԃɖ߂����߁A���̏�Ԃ�ۑ����܂��B
		_initialElemEnumState = _elemEnum.saveState();
	}
	
	/**
	 * ���̐��K�\�����w��̕�����ƈ�v���邩�ǂ����𒲂ׂ܂��B
	 * 
	 * @param str�@���K�\���ƈ�v���邩�ǂ����𒲂ׂ镶����ł��B
	 * @return ��v�𒲂ׂ����ʂ��i�[���� {@link MatchResult} �N���X�̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	public MatchResult match(String str) {
		// �w��̕�����̂��ꂼ��̈ʒu�ŁA���K�\������v���邩�ǂ����𒲂ׂ܂��B
		for (int startIndex = 0; startIndex < str.length(); ++startIndex) {
			// ���K�\���v�f�̗񋓎q��������Ԃɖ߂��A���݂̈ʒu����񋓂��J�n���镶����̗񋓎q���쐬���܂��B
			_elemEnum.restoreState(_initialElemEnumState);
			StringEnumerator strEnum = new StringEnumerator(str, startIndex);
			
			// ���K�\����������̌��݈ʒu�����v���邩�ǂ����𒲂ׂ܂��B��v����ΐ����̌��ʂ��쐬���Ԃ��܂��B
			if (matchFromCurrent(_elemEnum, strEnum)) {
				String matchString = strEnum.getMatchString();
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
			// ���K�\���v�f���܂����݂���ɂ�������炸�A�����񂪏I�����Ă���΁A��v�͎��s�ł��B
			if (!strEnum.hasCurrent()) {
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
