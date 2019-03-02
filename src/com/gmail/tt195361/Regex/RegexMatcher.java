package com.gmail.tt195361.Regex;

/**
 * �w��̐��K�\�����w��̕�����ƈ�v���邩�ǂ����𒲂ׂ܂��B
 * 
 * <h1 id="��v�𒲂ׂ�菇">��v�𒲂ׂ�菇</h1>
 * <p>
 * ���鐳�K�\����������ƈ�v���邩�ǂ������ׂ�菇�ɂ��čl���܂��B
 * ���K�\����������Ɉ�v����Ƃ́A������̂���ʒu����A
 * ���K�\�����\�����镶�^�����ɂ��ׂĈ�v���邱�Ƃł��B
 * ����𒲂ׂ�ɂ́A������̍ŏ��̕�������A
 * 2 �Ԗڂ̕�������A3 �Ԗڂ̕�������A...�A��
 * �J�n�ʒu�����ɂ��炵�āA���^�̈�v���������܂��B
 * ������̂ǂ����̈ʒu�ŕ��^�����ׂĈ�v����΁A
 * ���K�\���͂��̕�����Ɉ�v���邱�Ƃ��킩��܂��B
 * ������̂��ׂĂ̈ʒu�ŕ��^����v���Ȃ���΁A
 * ���̐��K�\���͕�����Ɉ�v���܂���B
 * <p>
 * ��Ƃ��āA���K�\�� {@code "abc"} ��
 * ������ {@code "aabcd"} �ň�v�𒲂ׂĂ݂܂��B
 * �͂��߂ɁA������̐擪�̈ʒu 0 ���琳�K�\������v���邩���ׂ܂��B
 * ���̏ꍇ�A������̈ʒu 0 �̕��� {@code a} �Ɛ��K�\���̈ʒu 0 ��
 * ���^ {@code OneChar: 'a'} �́A��v���܂��B
 * �������A���̎��̕��� {@code a} �ƕ��^ {@code OneChar: 'b'} �́A
 * ��v���܂���B
 * ���������āA�ʒu 0 ����͈�v���܂���ł����B
 * ���̏󋵂����̐}�Ɏ����܂��B
 * <br>
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/matching-procedure-1.png"
 *   	alt="��v�𒲂ׂ�菇-1">
 * </div>
 * <p>
 * ���ɁA������̊J�n�ʒu�� 1 ���炵�āA�ʒu 1 ���璲�ׂĂ݂܂��B
 * ���̂Ƃ��́A���� {@code a}, {@code b}, {@code c} ���A
 * ���^ {@code OneChar: 'a'}, {@code OneChar: 'b'}, {@code OneChar: 'c'} �ɁA
 * ���Ɉ�v���܂��B���������āA���̕��^�͕�����̈ʒu 1 ����
 * ��v���邱�Ƃ��킩��܂��B
 * <br>
 * <div align="center">
 *   <img
 *   	src="{@docRoot}/figures/matching-procedure-2.png"
 *   	alt="��v�𒲂ׂ�菇-2">
 * </div>
 * <p>
 * ������̊J�n�ʒu���ŏ����珇�ɂ��炵�Ē��ׂ�ƁA
 * <a href="RegexParser.html#�ō���v">�ō���v</a> �Ő��������A
 * ������̒��̍ł�������n�܂��v��������܂��B
 * ���Ƃ��΁A���K�\�� "aa" �́A������ "aabaac" �ƁA
 * �J�n�ʒu 0 �� 3 �� 2 ���ň�v���܂��B
 * ���̕��@�ł́A��荶�̊J�n�ʒu 0 ����̈�v��
 * �����ɂȂ�܂��B
 * 
 * <h1 id="��v�𒲂ׂ�v���O����">��v�𒲂ׂ�v���O����</h1>
 * <p>
 * ��L�̎菇���A�ȉ��� 2 �ɕ����Ă��P���ɂ��܂��B
 * <ol>
 *   <li>������̊J�n�ʒu�����Ɉړ�����B</li>
 *   <li>���݂̊J�n�ʒu���當�^�ƕ�����̈�v�𒲂ׂ�B</li>
 * </ol>
 * <p>
 * 
 * <h1 id="������̊J�n�ʒu�̈ړ�">������̊J�n�ʒu�̈ړ�</h1>
 * <p>
 * ������̗񋓎q {@link StringEnumerator} �N���X��
 * <a href="StringEnumerator.html#�J�n�ʒu�̈ړ�">�J�n�ʒu�̈ړ�</a> ��
 * �������郁�\�b�h���g���ƁA������̊J�n�ʒu���ړ�����菇����������
 * {@link #match(String)} ���\�b�h�́A�ȉ��̂悤�ɂȂ�܂��B
 * <pre>
        PatternEnumerator _patEnum;                         // ���^�̗񋓎q _patEnum �̓����o�[�ϐ�

        public MatchResult match(String str) {              // �����͑Ώۂ̕�����A�߂�l�͈�v�̌���
            StringEnumerator strEnum = new StringEnumerator(str);
            while (strEnum.hasValidStart()) {               // �L���ȊJ�n�ʒu�����݂���ԌJ��Ԃ�
                _patEnum �̌��݈ʒu���ŏ��ɖ߂�;            // (1)
                if (matchFromCurrent(_patEnum, strEnum)) {  // ���݈ʒu���當�^�ƕ����񂪈�v���邩�H
                    return ��v�ɐ���;                      // (2)
                }
                strEnum.moveNextStart();                    // ��v���Ȃ���΁A���̊J�n�ʒu�Ɉړ�
            }

            // ���ׂĂ̊J�n�ʒu�ň�v���Ȃ������B
            return ��v�Ɏ��s;                              // (2)
        }
 * </pre>
 * <ol>
 *   <li>_patEnum �̌��݈ʒu���ŏ��ɖ߂��ɂ́A
 *       �񋓎q�̏�Ԃ̕ۑ��ƕ������g���܂��B</li>
 *   <li>��v�𒲂ׂ����ʂ� {@link MatchResult} �N���X��
 *       �\�킵�܂��B</li>
 * </ol>
 * 
 * <h1 id="���݈ʒu�����v�𒲂ׂ�">���݈ʒu�����v�𒲂ׂ�</h1>
 * <p>
 * ���K�\�����^�ƕ�����̂��ꂼ��̌��݈ʒu����A���K�\�����^��
 * ������Ɉ�v���邩�ǂ����𒲂ׂ郁�\�b�h��
 * {@link #matchFromCurrent(PatternEnumerator, StringEnumerator)} �ł��B
 * ���̃��\�b�h�́A���K�\�����^�̗񋓎q {@link PatternEnumerator} ��
 * �g���āA���K�\�����^ {@link RegexPattern} �� 1 �����ɗ񋓂��A
 * ����炪������ƈ�v���邩�𒲂ׂ܂��B
 * ���^�ƕ�����̈�v�𒲂ׂ�ɂ́A
 * {@link RegexPattern#oneMatch(PatternEnumerator, StringEnumerator)} 
 * ���\�b�h���g���܂��B�v���O�����͎��̂悤�ɂȂ�܂��B
 * <pre>
    static boolean matchFromCurrent(PatternEnumerator patEnum, StringEnumerator strEnum) {
        // ���K�\�����^�����݂���ԁA�J��Ԃ��܂��B
        while (patEnum.hasCurrent()) {

            // ���K�\�����^���܂����݂���ɂ�������炸�A������̏I�����z���Ă���΁A
            // ��v�͎��s�ł��B
            if (!strEnum.hasCurrentOrEnd()) {
                return false;
            }
            
            // ���݂̐��K�\�����^��������Ɉ�v���邩�ǂ������ׂ܂��B��v���Ȃ���Ύ��s�ł��B
            RegexPattern currentPat = patEnum.getCurrent();
            if (!currentPat.oneMatch(patEnum, strEnum)) {
                return false;
            }
            
            // ���݂̐��K�\�����^�̈�v�ɐ������܂����BoneMatch() ���ĂԂƁA
            // patEnum �͍Ō�Ɉ�v�������^�Ɉړ�����̂ŁA���̐��K�\�����^��
            // �ړ����܂��BstrEnum �͎��Ɉ�v�𒲂ׂ�ʒu�Ɉړ����Ă��܂��B
            patEnum.moveNext();
        }

        // ���ׂĂ̐��K�\�����^��������̌��݈ʒu�����v�����̂ŁA�����ł��B
        return true;
    }
 * </pre>
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

		// �w��̕�����ɗL���ȊJ�n�ʒu�����݂���ԁA��v�𒲂ׂ܂��B
		while (strEnum.hasValidStart()) {

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
			
			// ���݂̐��K�\�����^��������Ɉ�v���邩�ǂ������ׂ܂��B
			// ��v���Ȃ���Ύ��s�ł��B
			RegexPattern currentPat = patEnum.getCurrent();
			if (!currentPat.oneMatch(patEnum, strEnum)) {
				return false;
			}
			
			// ���݂̐��K�\�����^�̈�v�ɐ������܂����BoneMatch() ���ĂԂƁA
			// patEnum �͍Ō�Ɉ�v�������^�Ɉړ�����̂ŁA���̐��K�\�����^��
			// �ړ����܂��BstrEnum �͎��Ɉ�v�𒲂ׂ�ʒu�Ɉړ����Ă��܂��B
			patEnum.moveNext();
		}

		// ���ׂĂ̐��K�\�����^��������̌��݈ʒu�����v�����̂ŁA�����ł��B
		return true;
	}
}
