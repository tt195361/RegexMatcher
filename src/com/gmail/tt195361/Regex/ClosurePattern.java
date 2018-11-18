package com.gmail.tt195361.Regex;

import java.util.Stack;

/**
 * ���\�킷���K�\�����^�ł��B��͎w��̐��K�\�����^�� 0 ��ȏ�̌J��Ԃ�����舵���܂��B
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
	 * ��́A�w��̕��^�� 0 ��ȏ�Ȃ�ׂ������J��Ԃ���v���A���̌��̐��K�\�����^���c��̕������
	 * ��v���邱�Ƃ𒲂ׂ܂��B
	 * 
	 * @param patEnum ���K�\�����^�̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu��
	 * 				�Ō�Ɉ�v�������K�\�����^�Ɉړ����܂��B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu��
	 * 				��v�����Ō�̐��K�\�����^�ɑΉ����镔���̎��̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		// �\�ȌJ��Ԃ��񐔂̌����쐬���A���ꂼ��̌��ɂ��āA�c�肪��v���邩�ǂ������ׂ܂��B
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

		// �J��Ԃ����^��������Ƃǂ��܂ň�v���邩���ׁA�قȂ��ԂȂ�Ό��Ƃ��Ēǉ����܂��B
		while (strEnum.hasCurrentOrEnd()) {
			if (!_repeatPattern.oneMatch(patEnum, strEnum)) {
				break;
			}
			
			// ��Ԃ��ς��Ȃ��̂͋󕶎���ƈ�v����ꍇ�ŁA'^*', '$*', '.**' �Ȃǂł��B
			EnumeratorState savedState = strEnum.saveState();
			if (savedState.equals(candidateStateStack.peek())) {
				break;
			}
			
			candidateStateStack.push(savedState);
		}
		
		return candidateStateStack;
	}
			
	// ���ꂼ��̕�����񋓎q�̌��̈ʒu�ɑ΂��āA�c��̐��K�\�����^����v���邩�ǂ������ׂ܂��B
	private boolean matchTheRest(
			PatternEnumerator patEnum, StringEnumerator strEnum,
			Stack<EnumeratorState> strEnumCandidateStateStack) {
		// ���̕�̎��Ɉړ����A���̈ʒu��ۑ����܂��B���ꂼ��̌��̒T�������̈ʒu����n�߂���悤�ɂ��܂��B
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
