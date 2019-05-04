/**
 * ���K�\����������ƈ�v���邩�ǂ����𒲂ׂ�v���O�����ł��B
 * ���ۂɓ��삷�邠����x�̑傫���̃v���O�������g���āA���̓��e��������܂��B
 * <p>
 * ���̃v���O�����Ɛ������쐬����ɂ�����A�ȉ��̏��Ђ� Web �T�C�g��
 * �Q�l�ɂ����Ă��������܂����B
 * �����ɋL���āA���ӂ������܂��B
 * <ul>
 *   <li><a
 *   		href="https://www.amazon.co.jp/%E3%82%BD%E3%83%95%E3%83%88%E3%82%A6%E3%82%A7%E3%82%A2%E4%BD%9C%E6%B3%95-Brian-W-Kernighan/dp/4320021428">
 *   		�\�t�g�E�F�A��@, Brian W.Kernighan, P.J.Plauger ��, �ؑ� �� ��
 * 		 </a></li>
 *   <li><a
 *   		href="https://ja.wikipedia.org/wiki/%E6%AD%A3%E8%A6%8F%E8%A1%A8%E7%8F%BE">
 *   		���K�\�� - Wikipedia
 *   	  </a></li>
 * </ul>
 * <p>
 * ���̃v���O�����ɂ��āA�ȉ��̓��e�����ɐ������܂��B
 * <ul>
 *   <li><a href="RegexParser.html#��舵�����K�\��">��舵�����K�\��</a></li>
 *   <li><a href="RegexPattern.html#���^�̕\�킵��">���^�̕\�킵��</a></li>
 *   <li><a href="PatternEnumerator.html#���K�\���̕\�킵��">���K�\���̕\�킵��</a></li>
 *   <li><a href="RegexMatcher.html#��v�𒲂ׂ�菇">��v�𒲂ׂ�菇</a></li>
 *   <li><a href="RegexMatcher.html#��v�𒲂ׂ�v���O����">��v�𒲂ׂ�v���O����</a></li>
 *   <li><a href="PatternEnumerator.html#�񋓎q">�񋓎q</a></li>
 *   <li><a href="RegexMatcher.html#������̊J�n�ʒu�̈ړ�">������̊J�n�ʒu�̈ړ�</a></li>
 *   <li><a href="RegexMatcher.html#���݈ʒu�����v�𒲂ׂ�">���݈ʒu�����v�𒲂ׂ�</a></li>
 *   <li><a href="RegexPattern.html#���^�̈�v">���^�̈�v</a></li>
 * </ul>
 * 
 * <p>
 * TODO: �������牺�́A�ȑO�̓��e�����̂܂܎c���Ă�����́B�������ĕύX����B
 * 
 * <h2>��v�𒲂ׂ� (1)</h2>
 * <p>
 * ��L�̎菇�͂��̂܂܂ł͕��G�Ȃ̂ŁA�ȉ��� 2 �ɕ����Ă݂܂��B
 * <ol>
 *   <li>������̍ŏ��̕�������A2 �Ԗڂ̕�������A3 �Ԗڂ̕�������A...�A�ƁA
 *       �J�n�ʒu�����Ɉړ�����B</li>
 *   <li>������̎w��̊J�n�ʒu����A���^����v���邩�ǂ����𒲂ׂ�B</li>
 * </ol>
 * <p>
 * �J�n�ʒu�̈ړ��ɂ́A������̗񋓎q {@link com.gmail.tt195361.Regex.StringEnumerator} ��
 * �񋟂���A�ȉ��̃��\�b�h���g���܂��B
 * <ul>
 *   <li>{@link com.gmail.tt195361.Regex.StringEnumerator#hasValidStart() StringEnumerator.hasCurrentStart()} -- 
 *   	���݂̊J�n�ʒu�����v�𒲂ׂ��邩�ǂ�����Ԃ��܂��B</li>
 *   <li>{@link com.gmail.tt195361.Regex.StringEnumerator#moveNextStart() StringEnumerator.moveNextStart()} -- 
 *   	�J�n�ʒu�����̕����Ɉړ����܂��B</li>
 * </ul>
 * <p>
 * {@link com.gmail.tt195361.Regex.StringEnumerator#hasValidStart() StringEnumerator.hasCurrentStart()} ��
 * ������̂ǂ��܂Œ��ׂ�΂悢���l���܂��B���^ {@code $} �͕�����̍Ō�Ɉ�v���܂��B
 * ������̍Ō�́A������̍Ō�̕����̎��̈ʒu�ɂȂ�܂��B
 * ���̂��߁A��v�� {@code _startIndex} �� {@code _length} �ɓ������Ȃ�܂Œ��ׂ�K�v������܂��B
 * <pre>
        boolean hasCurrentStart() {
            // '$' �͕�����̍Ō�Ɉ�v����̂ŁA������̍Ō�̕����̎��̈ʒu�܂Œ��ׂ܂��B
            return _startIndex <= _length;
        }
 * </pre>
 * <p>
 * {@link com.gmail.tt195361.Regex.StringEnumerator#moveNextStart() StringEnumerator.moveNextStart()} �́A
 * {@link com.gmail.tt195361.Regex.StringEnumerator#hasValidStart() StringEnumerator.hasCurrentStart()} ��
 * ���݂̊J�n�ʒu���L���Ȃ�Ύ��ɐi�߂܂��B
 * ���̒��Ŏg���Ă��� {@code setStartAndCurrentIndex()} �́A
 * {@code _startIndex} �� {@code _currentIndex} ���A�w��̒l�ɐݒ肵�܂��B
 * <pre>
        void moveNextStart() {
            if (hasCurrentStart()) {
                setStartAndCurrentIndex(_startIndex + 1);
            }
        }

        private void setStartAndCurrentIndex(int value) {
            _startIndex = _currentIndex = value;
        }
 * </pre>
 * <p>
 * �������g���ƁA��v�𒲂ׂ�� 1 �̎菇����������
 * {@link com.gmail.tt195361.Regex.RegexMatcher#match(String)} ���\�b�h�́A�ȉ��̂悤�ɂȂ�܂��B
 * {@code _patEnum} �́A{@link com.gmail.tt195361.Regex.PatternEnumerator} �^�̃����o�[�ϐ��ł��B
 * {@link com.gmail.tt195361.Regex.PatternEnumerator#restoreState(EnumeratorState)} ��
 * ���ẮA<a href="#�񋓎q�̏�Ԃ̕ۑ��ƕ���">�񋓎q�̏�Ԃ̕ۑ��ƕ���</a> �Ő������܂��B
 * {@link com.gmail.tt195361.Regex.RegexMatcher#matchFromCurrent(PatternEnumerator, StringEnumerator)} �́A
 * �� 2 �̎菇���������郁�\�b�h�ł��B
 * {@link com.gmail.tt195361.Regex.MatchResult} �́A��v�𒲂ׂ����ʂ�ێ�����N���X�ŁA
 * <a href="#��v�𒲂ׂ�����">��v�𒲂ׂ�����</a> �Ő������܂��B
 * <pre>
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
 * </pre>
 * 
 * <h2>��v�𒲂ׂ� (2)</h2>
 * <p>
 * ���K�\���ƕ�����̈�v�𒲂ׂ�� 2 �̎菇�ł́A������̌��݂̊J�n�ʒu����A
 * ���K�\�����\�����镶�^�����Ɉ�v���邩�ǂ����𒲂ׂ܂��B���̂��߂�
 * ���^�̗񋓎q {@link com.gmail.tt195361.Regex.PatternEnumerator} ��
 * �ȉ��̃��\�b�h���g���܂��B
 * <ul>
 *   <li>{@link com.gmail.tt195361.Regex.PatternEnumerator#hasCurrent()} -- 
 *   	���݈ʒu�ɕ��^�����邩�ǂ����𒲂ׂ܂��B</li>
 *   <li>{@link com.gmail.tt195361.Regex.PatternEnumerator#getCurrent()} -- 
 *   	���݈ʒu�̕��^���擾���܂��B</li>
 *   <li>{@link com.gmail.tt195361.Regex.PatternEnumerator#moveNext()} -- 
 *   	���݈ʒu�����̕��^�Ɉړ����܂��B</li>
 * </ul>
 * <p>
 * {@link com.gmail.tt195361.Regex.PatternEnumerator#hasCurrent()} �́A
 * ���݈ʒu {@code _currentIndex} ���A���^�̔z�� {@code _patterns} �̓Y�����͈̔͂ł���
 * {@code 0} �ȏォ�� {@code _patterns.length} �����ł��邱�Ƃ𒲂ׂ܂��B
 * <pre>
        boolean hasCurrent() {
            return 0 <= _currentIndex && _currentIndex < _patterns.length;
        }
 * </pre>
 * <p>
 * {@link com.gmail.tt195361.Regex.PatternEnumerator#getCurrent()} �́A
 * ���݈ʒu�ɕ��^������΂��̕��^���A�Ȃ���� {@code null} ��Ԃ��܂��B
 * <pre>
        RegexPattern getCurrent() {
            if (!hasCurrent()) {
                return null;
            }
            else {
                return _patterns[_currentIndex];
            }
        }
 * </pre>
 * {@link com.gmail.tt195361.Regex.PatternEnumerator#moveNext()} �́A
 * ���݈ʒu {@code _currentIndex} ����i�߂܂��B����������͌��݈ʒu�ɕ��^������ꍇ�����A
 * �܂� {@link com.gmail.tt195361.Regex.PatternEnumerator#hasCurrent()} ���A
 * {@code true} ��Ԃ��ꍇ�����ɂ��܂��B���݈ʒu�ɕ��^���Ȃ��ꍇ�́A����ȏ��ɐi�߂܂���B
 * <pre>
        void moveNext() {
            if (hasCurrent()) {
                ++_currentIndex;
            }
        }
 * </pre>
 * <p>
 * ��v�𒲂ׂ�� 2 �̎菇 {@link com.gmail.tt195361.Regex.RegexMatcher#matchFromCurrent(PatternEnumerator, StringEnumerator)} ���\�b�h
 * �ɂ��Đ������܂��B���̃��\�b�h�� {@code static} �ɂȂ��Ă���̂́A��̈�v�𒲂ׂ邽�߂ɂ��g�����߂ł��B
 * {@link com.gmail.tt195361.Regex.StringEnumerator#hasCurrent() StringEnumerator.hasCurrentOrEnd()} �́A
 * ������̌��݈ʒu�ɕ��������邩������̏I���Ȃ�΁A{@code true} ��Ԃ��܂��B
 * {@link com.gmail.tt195361.Regex.RegexPattern#oneMatch(PatternEnumerator, StringEnumerator)} �́A
 * <a href="#���^�̕\�킵��">���^�̕\�킵��</a> �Ő��������A{@link com.gmail.tt195361.Regex.RegexPattern} �N���X��
 * ���ۃ��\�b�h�ŁA���̕��^��������Ɉ�v���邩�ǂ����𒲂ׂ܂��B
 * <pre>
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
 * </pre>
 * 
 * <h2 id="���^�̈�v">���^�̈�v</h2>
 * <p>
 * ���ꂼ��̕��^��������̌��݈ʒu����̈�v�́A
 * ���ۃ��\�b�h {@link com.gmail.tt195361.Regex.RegexPattern#oneMatch(PatternEnumerator, StringEnumerator)} ���A
 * ���ꂼ��̕��^�ɉ����ċ�̓I�Ɏ������܂��B
 * ���̃��\�b�h�́A��v�������ǂ�����Ԃ��ƂƂ��ɁA���ꂼ��̗񋓎q�̌��݈ʒu�����̂悤�ɐi�߂܂��B
 * <ul>
 *   <li>{@link com.gmail.tt195361.Regex.PatternEnumerator} -- 
 *   	��ȊO�ł��̕��^���g�ƈ�v�����ꍇ�A���݈ʒu�͂��̂܂܈ړ����܂���B
 *   	��̏ꍇ�A�Ō�Ɉ�v�������K�\�����^�Ɉړ����܂��B</li>
 *   <li>{@link com.gmail.tt195361.Regex.StringEnumerator} -- 
 *   	���̐��K�\�����^�Ɉ�v�����������܂����z���A���̎��̕����Ɉړ����܂��B</li>
 * </ul>
 * <p>
 * ���ꂼ��̕��^�̈�v�ɂ��āA���ꂩ��������܂��B
 * 
 * <h3>�C�ӂ� 1 ���� ({@link com.gmail.tt195361.Regex.AnyCharPattern})</h3>
 * <p>
 * �C�ӂ� 1 �����́A������̗񋓎q�̌��݈ʒu�ɕ���������΁A��v���܂��B
 * ��v����΁A������̌��݈ʒu�����ɐi�߂܂��B
 * �v���O�����͎��̂悤�ɂȂ�܂��B
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            if (!strEnum.hasCurrent()) {
                return false;
            }

            strEnum.moveNext();
            return true;
        }
 * </pre>
 * 
 * <h3>������̍ŏ� ({@link com.gmail.tt195361.Regex.StartOfStringPattern})</h3>
 * <p>
 * ������̍ŏ��Ɉ�v���鐳�K�\�����^��\�킷
 * {@link com.gmail.tt195361.Regex.StartOfStringPattern} �N���X�̏ꍇ�A
 * {@link com.gmail.tt195361.Regex.StringEnumerator#isStart() StringEnumerator.isStart()} ���ĂсA
 * ���݈ʒu��������̍ŏ����ǂ����𒲂ׂ܂��B
 * ���̕��^�͈�v���镶���͂Ȃ��̂ŁA{@code strEnum} �̈ʒu�͂��̂܂܂ɂ��܂��B
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            return strEnum.isStart();
        }
 * </pre>
 * 
 * <h3>������̍Ō� ({@link com.gmail.tt195361.Regex.EndOfStringPattern})</h3>
 * <p>
 * ������̍Ō�Ɉ�v���� {@link com.gmail.tt195361.Regex.EndOfStringPattern} �́A
 * {@link com.gmail.tt195361.Regex.StringEnumerator#isEnd() StringEnumerator.isEnd()} ���g����
 * ���݈ʒu��������̍Ōォ�ǂ����𒲂ׂ܂��B
 * ���̕��^����v���镶���͂Ȃ��̂ŁA{@code strEnum} �̈ʒu�͂��̂܂܈ړ����܂���B
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            return strEnum.isEnd();
        }
 * </pre>
 * 
 * <h3>�����N���X ({@link com.gmail.tt195361.Regex.CharClassPattern})</h3>
 * <p>
 * �����N���X��\�킷 {@link com.gmail.tt195361.Regex.CharClassPattern} �N���X�́A
 * �ȉ��̃����o�[�ϐ����g���܂��B
 * <ul>
 *   <li>{@code _notContains} -- 
 *   	�w��̕������܂܂�Ă��Ȃ����Ƃ����� {@code boolean} �̒l�ł��B</li>
 *   <li>{@code _charSet} -- 
 *   	�w��̕������i�[���� {@link java.util.HashSet} �ł��B</li>
 * </ul>
 * <p>
 * �����N���X����v����̂́A���݈ʒu�ɕ���������A���A{@code _notContains} ��
 * {@code _charSet} ���ȉ��̏����𖞂����Ƃ��ł��B
 * <ul>
 *   <li>{@code _notContains} �� {@code false} �Ȃ�΁A
 * 		���݈ʒu�̕����� {@code _charSet} �Ɋ܂܂�Ă���A���邢�́A</li>
 *   <li>{@code _notContains} �� {@code true} �Ȃ�΁A
 * 		���݈ʒu�̕����� {@code _charSet} �Ɋ܂܂�Ă��Ȃ��B</li>
 * </ul>
 * <p>
 * {@link com.gmail.tt195361.Regex.CharClassPattern#oneMatch(PatternEnumerator, StringEnumerator)} ���\�b�h�́A
 * ���̂悤�ɂȂ�܂��B
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            if (!strEnum.hasCurrent()) {
                return false;
            }

            char ch = strEnum.getCurrent();
            if (!matchConditionSatisfied(ch)) {
                return false;
            }

            strEnum.moveNext();
            return true;
        }
 * </pre>
 * <p>
 * ��̃��\�b�h�̒��� {@code matchConditionSatisfied()} �́A�w��̕����������𖞂������ǂ�����Ԃ��܂��B
 * ���̏����́A�r���I�_���a (XOR) ���g���āA�ȉ��̂悤�ɏ����܂��B
 * <pre>
        private boolean matchConditionSatisfied(char ch) {
            // ^ �͔r���I�_���a�ŁA"false ^ true" ���邢�� "true ^ false" �̂Ƃ��A���ʂ� true �ɂȂ�܂��B
            return _notContains ^ _charSet.contains(ch);
        }
 * </pre>
 * 
 * <h3>�� ({@link com.gmail.tt195361.Regex.ClosurePattern})</h3>
 * <p>
 * ��̈�v�́A���̎菇�Œ��ׂ܂��B
 * <ol>
 *   <li>��̑O�̕��^�� 0 ��ȏ�Ȃ�ׂ������J��Ԃ���v�����܂��B</li>
 *   <li>��̌��̐��K�\�����^���A�c��̕�����Ɉ�v���邩�m�F���܂��B</li>
 * </ol>
 * 
 * <h3>�w��� 1 ���� ({@link com.gmail.tt195361.Regex.OneCharPattern})</h3>
 * <p>
 * 
 * 
 * <h2 id="��v�𒲂ׂ�����">��v�𒲂ׂ�����</h2>
 * <p>
 * ���K�\����������ƈ�v���邩�ǂ����𒲂ׂ����ʂł����A��v�ɐ������������s�������ɉ����āA
 * ������̂ǂ̈ʒu�����v�������A���ۂɈ�v����������Ȃǂ��擾�ł���ƕ֗��ł��A�A�A
 * 
 * <h2 id="�񋓎q�̏�Ԃ̕ۑ��ƕ���">�񋓎q�̏�Ԃ̕ۑ��ƕ���</h2>
 * <p>
 * 
 * <!--- ===== --->
 * <h2>���K�\�����^�̈�v</h2>
 * <p>
 * <a href="#���^�̕\�킵��">���^�̕\�킵��</a> �Ő��������悤�ɁA���ꂼ��̐��K�\�����^��\�킷
 * �N���X�́A
 * 
 * <h2>��v�𒲂ׂ�v���O����</h2>
 * <p>
 * ���̃v���O�������g���āA�w��̐��K�\�����^��������ƈ�v���邩�ǂ����𒲂ׂ�菇��������܂��B
 * <ol>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃R���X�g���N�^
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} ���A
 *   	���K�\���̕��^��\�킷������������Ƃ��ČĂяo���A
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃I�u�W�F�N�g���쐬���܂��B
 *   	���K�\���� {@code "^[a-z]*$"} �̏ꍇ�́A���̂悤�ɂȂ�܂��B
 *      <pre>
 *�@  RegexMatcher matcher = new RegexMatcher("^[a-z]$");
 * 		</pre>
 *   </li>
 *   <li>�쐬���� {@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃I�u�W�F�N�g�ɑ΂��āA
 *   	��v���邩�ǂ����𒲂ׂ镶����������Ƃ���
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#match} ���\�b�h���Ăяo���܂��B
 *      ������ {@code "abcxyz"} �ƈ�v���邩�ǂ����𒲂ׂ��������܂��B
 *      <pre>
 *�@  MatchResult result = matcher.match("abcxyz");
 * 		</pre>
 *   </li>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher#match} ���\�b�h�́A
 *   	{@link com.gmail.tt195361.Regex.MatchResult} �N���X�̃I�u�W�F�N�g��
 *   	�Ԃ��܂��B���̃I�u�W�F�N�g�͈�v�ɐ����������ǂ�����Ԃ�
 *   	{@link com.gmail.tt195361.Regex.MatchResult#isSucceess} �Ȃǂ̃��\�b�h������A
 *   	��v�𒲂ׂ����ʂ�񋟂��܂��B
 *      <pre>
 *�@  if (result.isSuccess()) {                            // ��v�ɐ����������ǂ������ׂ�
 *�@�@  �@�@int startIndex = result.getStartIndex();       // ��v�̊J�n�ʒu���擾
 *�@�@�@  �@String matchString = result.getMatchString();  // ��v���������̕�������擾
 *�@�@�@�@  . . . . .
 *�@  }
 * 		</pre>
 *   </li>
 * </ol>
 * 
 * <h2>����̗���</h2>
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher} �v���O�����́A�ȉ��̗���œ��삵�܂��B
 * <ol>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃R���X�g���N�^
 *   	{@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} �Ŏ󂯎�������K�\����
 *   	���^��\�킷����������߂��A�v���O�������Ŏg�p��������\�����쐬���܂��B</li>
 *   <li>{@link com.gmail.tt195361.Regex.RegexMatcher#match} ���\�b�h�Ŏw��̕�����ɑ΂��āA
 *   	�쐬���������\������v���邩�ǂ����𒲂ׂ܂��B���̂��߂ɂ́A�w��̕�����̍ŏ����珇�ɂ��ꂼ��̈ʒu�ŁA
 *      ���K�\���̂��ꂼ��̓����\����������ɏ��Ɉ�v���邩���ׂ܂��B</li>
 * </ol>
 * 
 * <h2>���K�\���̕�����̉���</h2>
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher} �N���X�̃R���X�g���N�^
 * {@link com.gmail.tt195361.Regex.RegexMatcher#RegexMatcher(String)} �Ŏ󂯎�������K�\����
 * ���^��\�킷����������߂��A�v���O�������Ŏg�p��������\�����쐬���܂��B
 * <p>
 * �����\���̍쐬�ɂ́A{@link com.gmail.tt195361.Regex.RegexParser#parse(String)}
 * ���\�b�h���g���܂��B�֘A����N���X�́A�ȉ��̒ʂ�ł��B
 * <ul>
 *   <li>{@link com.gmail.tt195361.Regex.RegexParser}</li>
 *   <li>{@link com.gmail.tt195361.Regex.CharClassParser}</li>
 *   <li>{@link com.gmail.tt195361.Regex.PatternParseResult}</li>
 * </ul>
 * 
 * <h2>��v�𒲂ׂ�菇</h2>
 * <p>
 * {@link com.gmail.tt195361.Regex.RegexMatcher#match} ���\�b�h�Ŏw��̕�����ɑ΂��āA
 * �쐬���������\������v���邩�ǂ����𒲂ׂ܂��B���̂��߂ɂ́A�w��̕�����̍ŏ����珇�ɂ��ꂼ��̈ʒu�ŁA
 * ���K�\���̂��ꂼ��̓����\����������ɏ��Ɉ�v���邩���ׂ܂��B
 * <p>
 * ��Ƃ��āA���K�\�� {@code "abc"} �������� {@code "aabcd"} �ƈ�v���邩�ǂ������ׂ�ꍇ��
 * �l���܂��B���̐}�́A������̈ʒu 0 ���璲�ׂ�ꍇ�ł��B���̏ꍇ�A������̈ʒu 1 �̕��� {@code a} ��
 * 2 �Ԗڂ̓����\��  {@code OneChar: 'b'} ����v���܂���B
 * <pre>
 *    ������
 *   �ʒu ����                  �����\��
 *        +---+   ��v����  +--------------+
 *     0  | a |�@&lt;--------&gt; | OneChar: 'a' |
 *        +---+   ��v����  +--------------+
 *     1  | a |�@&lt;--------&gt; | OneChar: 'b' |
 *        +---+             +--------------+
 *     2  | b |             | OneChar: 'c' |
 *        +---+             +--------------+
 *     3  | c |
 *        +---+
 *     4  | d |
 *        +---+
 * </pre>
 * <p>
 * ���ɁA������̈ʒu 1 ���璲�ׂ�ꍇ�̐}�������܂��B���̂Ƃ��́A���ׂĂ̐��K�\�����^����v���܂��B
 * <pre>
 *    ������
 *   �ʒu ����                  �����\��
 *        +---+
 *     0  | a |
 *        +---+   ��v����  +--------------+
 *     1  | a |�@&lt;--------&gt; | OneChar: 'a' |
 *        +---+   ��v����  +--------------+
 *     2  | b |�@&lt;--------&gt; | OneChar: 'b' |
 *        +---+   ��v����  +--------------+
 *     3  | c |�@&lt;--------&gt; | OneChar: 'c' |
 *        +---+             +--------------+
 *     4  | d |
 *        +---+
 * </pre>
 * 
 * <h2>���K�\���̓����\��</h2>
 * <p>
 * ���K�\���̕����񂩂�쐬����v���O�����̓����\���́A�ȉ��̒ʂ�ł��B
 * <ul>
 *   <li>��舵�����ꂼ��̐��K�\���̕��^�ɂ��āA���̕��^��\�킷�N���X���쐬���܂��B</li>
 *   <li>���ꂼ��̐��K�\���̕��^��\�킷�N���X�́A���ۃN���X {@link com.gmail.tt195361.Regex.RegexPattern} ����h�������܂��B</li>
 *   <li>. . . . .</li>
 * </ul>
 * <table
 * 		border="1" rules="all"
 * 		summary="�N���X�̕���">
 *   <tr>
 *     <th>����</th>
 *     <th>�N���X</th>
 *   </tr>
 *   <tr>
 *     <td rowspan="2" valign="top">�S�̂̐���</td>
 *     <td>RegexMatcher</td>
 *   </tr>
 *   <tr>
 *     <td>MatchResult</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="3" valign="top">���K�\���̕��^�̉���</td>
 *     <td>RegexParser</td>
 *   </tr>
 *   <tr>
 *     <td>CharClassParser</td>
 *   </tr>
 *   <tr>
 *     <td>PatternParseResult</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="7" valign="top">���K�\���̈�̕��^</td>
 *     <td>RegexPattern</td>
 *   </tr>
 *   <tr>
 *     <td>AnyCharPattern</td>
 *   </tr>
 *   <tr>
 *     <td>CharClassPattern</td>
 *   </tr>
 *   <tr>
 *     <td>ClosurePattern</td>
 *   </tr>
 *   <tr>
 *     <td>EndOfStringPattern</td>
 *   </tr>
 *   <tr>
 *     <td>OneCharPattern</td>
 *   </tr>
 *   <tr>
 *     <td>StartOfStringPattern</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="3" valign="top">�R���N�V�����̍��ڂ̗�</td>
 *     <td>PatternEnumerator</td>
 *   </tr>
 *   <tr>
 *     <td>StringEnumerator</td>
 *   </tr>
 *   <tr>
 *     <td>EnumeratorState</td>
 *   </tr>
 *   <tr>
 *     <td rowspan="1" valign="top">���[�e�B���e�B</td>
 *     <td>MathUtils</td>
 *   </tr>
 * </table>
 */
package com.gmail.tt195361.Regex;
