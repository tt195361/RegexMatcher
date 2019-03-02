package com.gmail.tt195361.Regex;

import java.util.Stack;

/**
 * ���\�킷���K�\�����^�ł��B��͎w��̐��K�\�����^�� 0 ��ȏ��
 * �J��Ԃ�����舵���܂��B
 *
 * <h1 id="��̈�v">��̈�v</h1>
 * <p>
 * ���v��������́A���̂悤�ɏ����܂��B
 * <ul>
 *   <li>��̑O�ɂ��镶�^�� 0 ��ȏ㕶����ƈ�v���A���A</li>
 *   <li>��̌��̕��^���c��̕�����ƈ�v����B</li>
 * </ul>
 * <p>
 * ���̏����́A���̎菇�Œ��ׂ��܂��B
 * <ol>
 *   <li>���쐬 -- ��̑O�ɂ��镶�^��������̌��݈ʒu����
 *       �ǂ��܂ň�v���邩���ׁA��v���邻�ꂼ��̈ʒu��
 *       ��Ƃ̈�v�̌��ɂ��܂��B</li>
 *   <li>�c��̈�v���� -- ������̂��ꂼ��̌��ʒu����A
 *   	 ��̌��̕��^����v���邩���ׂ܂��B</li>
 * </ol>
 * <p>
 * ���Ƃ��΁A���K�\�� {@code ".*b"} �ƕ����� {@code "ab"} �̈�v��
 * �l���܂��B���̐��K�\���̒��̕�̑O�̕��^�́A
 * �C�ӂ� 1 ���� {@code "."} �ł��B���̂��߁A���
 * �w��̕�����ɑ΂��āA{@code ""} (�󕶎���)�A{@code "a"}�A
 * {@code "ab"} �� 3 �ʂ�̈�v�̌�₪�l�����܂��B
 * ��͌J��Ԃ��� 0 ��ł������̂ŁA
 * �󕶎���͏�Ɍ��ɂȂ�܂��B�����̌��ɂ��āA
 * ��ɑ����w��� 1 ���� {@code "b"} �Ǝc�蕶����̈�v�́A
 * ���̕\�̂悤�ɂȂ�܂��B
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="���ꂼ��̌��ʒu�ł̈�v�̌���">
 *   <tr bgcolor="lightgray" align="center">
 *     <th rowspan="2">�ԍ�</th>
 *     <th            >�� {@code ".*"}</th>
 *     <th colspan="2">�w��� 1 ���� {@code "b"}</th>
 *   </tr>
 *   <tr bgcolor="whitesmoke" align="center">
 *     <th>��v�̌��</th>
 *     <th>�Ή����镶��</th>
 *     <th>��v</th>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">1</td>
 *     <td>{@code ""}</td>
 *     <td>{@code a}</td>
 *     <td bgcolor="pink">{@code �~}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">2</td>
 *     <td>{@code a}</td>
 *     <td>{@code b}</td>
 *     <td bgcolor="lightblue">{@code �Z}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">3</td>
 *     <td>{@code ab}</td>
 *     <td>{@code ""}</td>
 *     <td bgcolor="pink">{@code �~}</td>
 *   </tr>
 * </table>
 * </div>
 * <p>
 * ���̌��ʂ��A���K�\�� {@code ".*b"} ��
 * ������ {@code "ab"} �ɑ΂��āA
 * �� {@code ".*"} �� {@code "a"} �Ɉ�v������΁A
 * �c��̕��^�̎w��� 1 ���� {@code "b"} ��
 * �c��̕����� {@code "b"} �ƈ�v���A
 * ���K�\���S�̂Ƃ��ĕ�����ƈ�v���邱�Ƃ��킩��܂��B
 * 
 * <h1 id="�����̕�">�����̕�</h1>
 * <p>
 * ��̐��K�\���̒��ɕ����̕����ꍇ�ł��A
 * ���ꂼ��̕�ɂ��ď�L�̎菇���J��Ԃ��΁A
 * ��v�𒲂ׂ��܂��B
 * ���Ƃ��΁A���K�\�� {@code "(.*,.*)"} �ƕ�����
 * {@code "(a,b)"} �̏ꍇ�A���̂悤�ɂȂ�܂��B
 * <ol>
 *   <li>�ŏ��̕�̈�v�̌��́A{@code ""} (�󕶎���)�A
 *   	 {@code "a"}�A{@code "a,"}�A{@code "a,b"}�A{@code "a,b)"} ��
 *   	 5 �ɂȂ�܂��B
 *       �����ɂ��āA��ɑ������^ {@code ",.*)"} ��
 *       �c��̕�����̈�v�𒲂ׂ܂��B</li>
 *   <li>�ŏ��̕�ɑ����w��̈ꕶ�� {@code ","} ����v����̂́A
 *       �ŏ��̕�𕶎��� {@code "a"} �ƈ�v�������ꍇ�ł��B</li>
 *   <li>���̏ꍇ�A2 �Ԗڂ̕� {@code ".*"} �ɑΉ����镶�����
 *       {@code "b)"} �ɂȂ�܂��B
 *       ���̂Ƃ��� 2 �Ԗڂ̕�̈�v�̌��́A{@code ""} (�󕶎���)�A
 *       {@code "b"}�A{@code "b)"} �� 3 �ʂ�ɂȂ�܂��B</li>
 *   <li>�����̌��̂����A��̌��� {@code ")"} ����v����̂́A
 *       ��� {@code "b"} �Ɉ�v�������Ƃ��ł��B</li>
 * </ol>
 * <p>
 * ���̓��e��\�ɂ܂Ƃ߂�ƁA���̂悤�ɂȂ�܂��B
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="�����̕�̈�v">
 *   <tr bgcolor="lightgray" align="center">
 *     <th rowspan="2">�ԍ�</th>
 *     <th colspan="2">�w��� 1 ���� {@code "("}</th>
 *     <th            >�� {@code ".*"}</th>
 *     <th colspan="2">�w��� 1 ���� {@code ","}</th>
 *     <th            >�� {@code ".*"}</th>
 *     <th colspan="2">�w��� 1 ���� {@code ")"}</th>
 *   </tr>
 *   <tr bgcolor="whitesmoke" align="center">
 *     <th>�Ή����镶��</th>
 *     <th>��v</th>
 *     <th>��v�̌��</th>
 *     <th>�Ή����镶��</th>
 *     <th>��v</th>
 *     <th>��v�̌��</th>
 *     <th>�Ή����镶��</th>
 *     <th>��v</th>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">1</td>
 *     <td rowspan="7">{@code (}</td>
 *     <td rowspan="7" bgcolor="lightblue">{@code �Z}</td>
 *     <td>{@code ""}</td>
 *     <td>{@code a}</td>
 *     <td bgcolor="pink">{@code �~}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">2-1</td>
 *     <td rowspan="3">{@code a}</td>
 *     <td rowspan="3">{@code ,}</td>
 *     <td rowspan="3" bgcolor="lightblue">{@code �Z}</td>
 *     <td>{@code ""}</td>
 *     <td>{@code b}</td>
 *     <td bgcolor="pink">{@code �~}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">2-2</td>
 *     <td>{@code b}</td>
 *     <td>{@code )}</td>
 *     <td bgcolor="lightblue">{@code �Z}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">2-3</td>
 *     <td>{@code b)}</td>
 *     <td>{@code ""}</td>
 *     <td bgcolor="pink">{@code �~}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">3</td>
 *     <td>{@code a,}</td>
 *     <td>{@code b}</td>
 *     <td bgcolor="pink">{@code �~}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">4</td>
 *     <td>{@code a,b}</td>
 *     <td>{@code )}</td>
 *     <td bgcolor="pink">{@code �~}</td>
 *   </tr>
 *   <tr align="center">
 *     <td bgcolor="whitesmoke">5</td>
 *     <td>{@code a,b)}</td>
 *     <td>{@code ""}</td>
 *     <td bgcolor="pink">{@code �~}</td>
 *   </tr>
 * </table>
 * </div>
 * 
 * <h1 id="��v�̒���">��v�̒���</h1>
 * <p>
 * ��܂܂�鐳�K�\���ƕ�����̈�v�ł́A
 * �����̈قȂ镡���̌�₪�����邱�Ƃ�����܂��B
 * ���Ƃ��΁A���K�\�� {@code ".*"} �ƕ����� {@code "abc"} ���ƁA
 * ��v�̌��� {@code ""} (�󕶎���)�A{@code "a"}�A{@code "ab"}�A
 * {@code "abc"} �� 4 �ɂȂ�܂��B
 * �����̈�v�̌�₪����ꍇ�́A�ǂ�����ʂ�
 * ���邩���߂Ă����Ȃ���΂Ȃ�܂���B
 * <p>
 * �����̈�v���̂ǂ�����ʂƂ��邩�́A���̕��@���l�����܂��B
 * <ul>
 *   <li>�ŒZ��v -- �ł��Z����v�����ʂƂ��܂��B</li>
 *   <li>�Œ���v -- �ł�������v�����ʂƂ��܂��B</li>
 * </ul>
 * <p>
 * ���K�\�� {@code ".*"} �ƕ����� {@code "abc"} �̏ꍇ�A
 * �ŒZ��v�͌J��Ԃ��� 0 ��̋󕶎���ɂȂ�܂��B
 * �܂��A�Œ���v�͑ΏۂƂȂ镶����S�̂� {@code "abc"} �ɂȂ�܂��B
 * <p>
 * ���K�\�� {@code "(.*,.*)"} �ƕ����� {@code "(a,b)(c,d)"} �ł́A
 * ���ꂼ��̈�v�͈ȉ��̂悤�ɂȂ�܂��B
 * <ul>
 *   <li>�ŒZ��v�̏ꍇ�A�ŏ��̕�� {@code "a"} �ƁA
 *       2 �Ԗڂ� {@code "b"} �ƈ�v���܂��B
 *       ���K�\���S�̂Ƃ��ẮA������ {@code "(a,b)"} �ƈ�v���܂��B</li>
 *   <li>�Œ���v�̏ꍇ�A�ŏ��̕�� {@code "a,b)(c"} �ƈ�v����̂�
 *       �ł������Ȃ�܂��B���̏ꍇ�A2 �Ԗڂ� {@code "d"} �ƈ�v���A
 *       �S�̂Ƃ��Ă� {@code "(a,b)(c,d)"} �ƈ�v���܂��B</li>
 * </ul>
 * <p>
 * ���̃v���O�����ł́A<a href="RegexParser.html#��v�̏ڍ�">��v�̏ڍ�</a> ��
 * ���������悤�ɁA�Œ���v�̌��ʂ�Ԃ��܂��B
 * 
 * <h1 id="�����o�[�ϐ�">�����o�[�ϐ�</h1>
 * <p>
 * {@link ClosurePattern} �N���X�́A�ȉ��̃����o�[�ϐ����g���܂��B
 * <div align="center">
 * <table
 * 		border="1" rules="all"
 * 		summary="ClosurePattern �N���X�̃����o�[�ϐ�">
 *   <tr bgcolor="lightgray">
 *     <th align="center">���O</th>
 *     <th align="center">�^</th>
 *     <th align="center">����</th>
 *   </tr>
 *   <tr>
 *     <td>{@code _repeatPattern}</td>
 *     <td>{@code final RegexPattern}</td>
 *     <td>�J��Ԃ����^��ێ����܂��B
 *     	   ���̒l�́A�R���X�g���N�^�ōŏ��ɐݒ肵�����Ƃ͕ύX���Ȃ��̂ŁA
 *     	   {@code final} �ɂ��܂��B</td>
 *   </tr>
 * </table>
 * </div>
 *
 * <h1 id="��̈�v�̎���">��̈�v�̎���</h1>
 * <p>
 * ��̈�v�𒲂ׂ� {@link #oneMatch(PatternEnumerator, StringEnumerator)} ���\�b�h�́A
 * <a href="#��̈�v">��̈�v</a> �Ő��������菇����������
 * �ȉ��̃��\�b�h�����ɌĂяo���܂��B
 * <ul>
 *   <li>���쐬 -- {@code makeStrEnumCandidateStates()}</li>
 *   <li>�c��̈�v���� -- {@code matchTheRest()}</li>
 * </ul>
 * <p>
 * �v���O�����́A���̂悤�ɂȂ�܂��B
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            // �\�ȌJ��Ԃ��񐔂̌����쐬���A���ꂼ��̌��ɂ��āA
            // �c�肪��v���邩�ǂ������ׂ܂��B
            Stack<EnumeratorState> strEnumCandidateStateStack =
                    makeStrEnumCandidateStates(patEnum, strEnum);
            return matchTheRest(patEnum, strEnum, strEnumCandidateStateStack);
        }
 * </pre>
 *
 * <h1 id="���쐬">���쐬</h1>
 * <p>
 * ���쐬�ł́A��̑O�ɂ��镶�^��������̌��݈ʒu����
 * �ǂ��܂ň�v���邩���ׁA��v���邻�ꂼ��̈ʒu��
 * ��v�̌��Ƃ��ĕԂ��܂��B
 * <p>
 * ��v���̒l�� {@link EnumeratorState} �N���X�̃I�u�W�F�N�g
 * ���i�[���܂��B����ɂ��ẮA
 * <a href="EnumeratorState.html#�񋓎q�̏�Ԃ̕ۑ��ƕ���">
 * �񋓎q�̏�Ԃ̕ۑ��ƕ���</a> �Ő������܂��B
 * <p>
 * ��v�̌��́A���̂悤�ɒZ�����̂��珇�ɍ쐬���܂��B
 * <ol>
 *   <li>��͌J��Ԃ��� 0 ��ł������̂ŁA
 *       ���݈ʒu�����ɉ����܂��B</li>
 *   <li>�J��Ԃ����^ {@code _repeatPattern} ��������ƈ�v����΁A
 *       ���̈ʒu�����ɉ����܂��B</li>
 * </ol>
 * <p>
 * ���̃v���O�����͍Œ���v�̌��ʂ�Ԃ��̂ŁA
 * �c��̈�v���ׂ́A�ł�������₩�珇��
 * <p>
 * �قȂ��v�̌��
 * <p>
 * TODO: �c��̐���������
 */
class ClosurePattern extends RegexPattern {
	
	// �����o�[�ϐ�: �J��Ԃ����K�\�����^��ێ����܂��B
	private final RegexPattern _repeatPattern;
	
	/**
	 * �w��̌J��Ԃ����^������ {@link ClosurePattern} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @param repeatPattern �J��Ԃ����K�\�����^���w�肵�܂��B
	 */
	ClosurePattern(RegexPattern repeatPattern) {
		_repeatPattern = repeatPattern;
	}
	
	/**
	 * �J��Ԃ����K�\�����^���擾���܂��B
	 * 
	 * @return �J��Ԃ����K�\�����^��Ԃ��܂��B
	 */
	RegexPattern getRepeatPattern() {
		return _repeatPattern;
	}

	/**
	 * ���̐��K�\�����^��������̌��݈ʒu�����v���邩�ǂ����𒲂ׂ܂��B
	 * ��́A�w��̕��^�� 0 ��ȏ�Ȃ�ׂ������J��Ԃ���v���A
	 * ����ɑ������K�\�����^���c��̕�����Ɉ�v���邱�Ƃ𒲂ׂ܂��B
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B�Ăяo����̌��݈ʒu��
	 * 				�Ō�Ɉ�v�𒲂ׂ����K�\�����^�Ɉړ����܂��B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu��
	 * 				���K�\�����^�Ɉ�v���������̎��̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A
	 * 			���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		// �\�ȌJ��Ԃ��񐔂̌����쐬���A���ꂼ��̌��ɂ��āA
		// �c�肪��v���邩�ǂ������ׂ܂��B
		Stack<EnumeratorState> strEnumCandidateStateStack =
				makeStrEnumCandidateStates(patEnum, strEnum);
		return matchTheRest(patEnum, strEnum, strEnumCandidateStateStack);
	}
	
	// �\�ȌJ��Ԃ��񐔂̌����쐬���܂��B
	private Stack<EnumeratorState> makeStrEnumCandidateStates(
			PatternEnumerator patEnum, StringEnumerator strEnum) {
		// �Ȃ�ׂ������J��Ԃ��񐔂ƈ�v�����邽�߁A���̓X�^�b�N�Ɋi�[���A
		// ��ɓ��ꂽ��蒷���J��Ԃ����ɒ��ׂ�悤�ɂ��܂��B
		Stack<EnumeratorState> candidateStateStack = new Stack<EnumeratorState>();
		
		// �J��Ԃ��񐔂� 0 ��ł������̂ŁA���݂̏�Ԃ����̂܂܌��ɂ��܂��B
		candidateStateStack.push(strEnum.saveState());

		// �J��Ԃ����^��������Ƃǂ��܂ň�v���邩���ׁA������̈ʒu��
		// �قȂ�Ȃ�Ό��Ƃ��Ēǉ����܂��B
		while (strEnum.hasCurrentOrEnd()) {
			if (!_repeatPattern.oneMatch(patEnum, strEnum)) {
				break;
			}
			
			// ������̏�Ԃ��ς��Ȃ��̂͋󕶎���ƈ�v����ꍇ�ŁA
			// '^*', '$*', '.**' �Ȃǂł��B
			EnumeratorState savedState = strEnum.saveState();
			if (savedState.equals(candidateStateStack.peek())) {
				break;
			}
			
			candidateStateStack.push(savedState);
		}
		
		return candidateStateStack;
	}
			
	// ���ꂼ��̕�����񋓎q�̌��̈ʒu�ɑ΂��āA�c��̐��K�\�����^��
	// ��v���邩�ǂ������ׂ܂��B
	private boolean matchTheRest(
			PatternEnumerator patEnum, StringEnumerator strEnum,
			Stack<EnumeratorState> strEnumCandidateStateStack) {
		// ���^�����̕�̎��Ɉړ����A���̈ʒu��ۑ����܂��B���ꂼ��̌���
		// �T�������̈ʒu����n�߂���悤�ɂ��܂��B
		patEnum.moveNext();
		EnumeratorState savedPatEnumState = patEnum.saveState();
		
		while (!strEnumCandidateStateStack.empty()) {
			// ���K�\�����^�̗񋓎q�̌��݈ʒu�����̕�̎��ɁA
			// ������̗񋓎q�̌��݈ʒu�����̌��ɐݒ肵�A��v���邩�ǂ����𒲂ׂ܂��B
			patEnum.restoreState(savedPatEnumState);
			strEnum.restoreState(strEnumCandidateStateStack.pop());
			if (RegexMatcher.matchFromCurrent(patEnum, strEnum)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * {@link ClosurePattern} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return "Closure: " + _repeatPattern.toString();
	}
}
