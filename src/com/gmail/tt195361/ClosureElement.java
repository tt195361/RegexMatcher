package com.gmail.tt195361;

import java.util.Stack;

/**
 * �w��̐��K�\���v�f�� 0 ��ȏ�J��Ԃ����\�킷���K�\���v�f�ł��B
 */
class ClosureElement extends RegexElement {
	
	// �����o�[�ϐ�: �J��Ԃ����K�\���v�f��ێ����܂��B
	private final RegexElement _repeatElement;
	
	/**
	 * �w��̌J��Ԃ��v�f������ {@link ClosureElement} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 * 
	 * @param repeatElement�@�J��Ԃ����K�\���v�f���w�肵�܂��B
	 */
	ClosureElement(RegexElement repeatElement) {
		_repeatElement = repeatElement;
	}
	
	/**
	 * �J��Ԃ����K�\���v�f���擾���܂��B
	 * 
	 * @return �J��Ԃ����K�\���v�f��Ԃ��܂��B
	 */
	RegexElement getRepeatElement() {
		return _repeatElement;
	}

	/**
	 * ���̐��K�\���v�f��������̌��݈ʒu�����v���邩�ǂ����𒲂ׂ܂��B
	 * ��́A�w��̗v�f�� 0 ��ȏ�Ȃ�ׂ������J��Ԃ���v���A���̌��̐��K�\���v�f���c��̕������
	 * ��v���邱�Ƃ𒲂ׂ܂��B
	 * 
	 * @param elemEnum ���K�\���v�f�̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu��
	 * 				�Ō�Ɉ�v�������K�\���v�f�Ɉړ����܂��B
	 * @param strEnum ������̗񋓎q�ł��B��v�ɐ��������ꍇ�A�Ăяo����̌��݈ʒu��
	 * 				��v�����Ō�̐��K�\���v�f�ɑΉ����镔���̎��̕����Ɉړ����܂��B
	 * @return ��v�ɐ��������ꍇ�� {@code true} ���A���s�����ꍇ�� {@code false} ��Ԃ��܂��B
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		// �\�ȌJ��Ԃ��񐔂̌����쐬���A���ꂼ��̌��ɂ��āA�c�肪��v���邩�ǂ������ׂ܂��B
		Stack<EnumeratorState> strEnumCandidateStateStack =
				makeStrEnumCandidateStates(elemEnum, strEnum);
		return matchTheRest(elemEnum, strEnum, strEnumCandidateStateStack);
	}
	
	// �\�ȌJ��Ԃ��񐔂̌����쐬���܂��B
	private Stack<EnumeratorState> makeStrEnumCandidateStates(
			ElementEnumerator elemEnum, StringEnumerator strEnum) {
		// �Ȃ�ׂ������J��Ԃ��񐔂ƈ�v�����邽�߁A���̓X�^�b�N�Ɋi�[���A
		// ��ɓ��ꂽ��蒷���J��Ԃ����ɒ��ׂ�悤�ɂ��܂��B
		Stack<EnumeratorState> candidateStateStack = new Stack<EnumeratorState>();
		
		// �J��Ԃ��񐔂� 0 ��ł������̂ŁA���݂̏�Ԃ����̂܂܌��ɂ��܂��B
		candidateStateStack.push(strEnum.saveState());

		// �J��Ԃ��v�f��������Ƃǂ��܂ň�v���邩���ׁA�قȂ��ԂȂ�Ό��Ƃ��Ēǉ����܂��B
		while (strEnum.hasCurrentOrEnd()) {
			if (!_repeatElement.oneMatch(elemEnum, strEnum)) {
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
			
	// ���ꂼ��̕�����񋓎q�̌��̈ʒu�ɑ΂��āA�c��̐��K�\���v�f����v���邩�ǂ������ׂ܂��B
	private boolean matchTheRest(
			ElementEnumerator elemEnum, StringEnumerator strEnum,
			Stack<EnumeratorState> strEnumCandidateStateStack) {
		// ���̕�̎��Ɉړ����A���̈ʒu��ۑ����܂��B���ꂼ��̌��̒T�������̈ʒu����n�߂���悤�ɂ��܂��B
		elemEnum.moveNext();
		EnumeratorState savedElemEnumState = elemEnum.saveState();
		
		while (!strEnumCandidateStateStack.empty()) {
			// ���K�\���v�f�̗񋓎q�̌��݈ʒu�����̕�̎��ɁA
			// ������̗񋓎q�̌��݈ʒu�����̌��ɐݒ肵�A��v���邩�ǂ����𒲂ׂ܂��B
			elemEnum.restoreState(savedElemEnumState);
			strEnum.restoreState(strEnumCandidateStateStack.pop());
			if (RegexMatcher.matchFromCurrent(elemEnum, strEnum)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * {@link ClosureElement} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return "Closure: " + _repeatElement.toString();
	}
}
