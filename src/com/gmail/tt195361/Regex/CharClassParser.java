package com.gmail.tt195361.Regex;

import java.util.HashSet;

/**
 * ������𕶎��N���X�̎w��Ƃ��ĉ��߂��܂��B
 */
class CharClassParser {

	// �����N���X�̕���������߂���Ƃ��ɓ��ʂȈӖ����������̒�`�ł��B
	private static final char CharClassNotContains = '^';
	private static final char CharClassRange = '-';
	private static final char CharClassEnd = ']';
	
	// �����o�[�ϐ�: �p�[�T�[�̏�ԁA�͈͊J�n�̕����A�����N���X�ɑ����镶�����i�[���� HashSet�B
	private ParserState _state;
	private char _startCh;
	private final HashSet<Character> _charSet;
	
	// �����o�[�ϐ�: �p�[�T�[�̏�Ԃ�\�킷�N���X�̃C���X�^���X�B
	private final ParserState _initialState = new InitialState();
	private final ParserState _startCharReadState = new StartCharReadState();
	private final ParserState _rangeReadState = new RangeReadState();
	
	/**
	 * {@link CharClassParser} �N���X�̃I�u�W�F�N�g���쐬���܂��B
	 */
	CharClassParser() {
		_state = _initialState;
		_startCh = '\0';
		_charSet = new HashSet<Character>();
	}
	
	/**
	 * �w��̕�����𕶎��N���X�̎w��Ƃ��ĉ��߂��A���߂������e�����Ƃɍ쐬���� {@link CharClassElement} ��
	 * �I�u�W�F�N�g��Ԃ��܂��B
	 *
	 * <p>
	 * �����N���X�̕��@���A�ȉ��̐}�Ɏ����܂��B
	 * <pre>{@code
	 *  --> '[' --+---------+---+--------------+--> ']' -->|
	 *            |         A   A              |
	 *            |         |   |              |
	 *            +--> '^ --+   +-- �����w�� --+
	 * }</pre>
	 * <ul>
	 *   <li>{@code '['} �Ŏn�܂�A</li>
	 *   <li>����ɑ����� {@code '^'} �������Ă��悭�A</li>
	 *   <li>�����w�肪 0�@��ȏ�J�Ԃ��A</li>
	 *   <li>{@code ']'} �ŏI���܂��B</li>
	 * </ul>
	 * <p>
	 * �����w��́A�ȉ��̂悤�ɋL�q���܂��B
	 * <pre>{@code
	 *  --> �J�n���� --+-----------------------+-->|
	 *                 |                       A
	 *                 |                       |
	 *                 +--> '-' --> �I������ --+
	 * }</pre>
	 * <ul>
	 *    <li>1 ���������̎w��A</li>
	 *    <li>���邢�́A�J�n�����ɑ����� {@code '-'} �ƏI���������L�q����͈͎w��B</li>
	 * </ul>
	 * 
	 * @param strEnum ���߂��镶����̗񋓎q�ł��B�Ăяo�����̌��݈ʒu�́A�����N���X�̎w��̍ŏ��̕���
	 * 			�ł�����̂Ƃ��܂��B �Ăяo����̌��݈ʒu�͍Ō�ɉ��߂��������Ɉړ����܂��B
	 * @return ���߂������e�����Ƃɍ쐬���� {@link CharClassElement} �̃I�u�W�F�N�g��Ԃ��܂��B
	 */
	CharClassElement parse(StringEnumerator strEnum) {
		// �ŏ��̕������܂����z���A���� '^' ���ǂ������ׂ܂��B
		strEnum.moveNext();
		boolean notContains = parseCharClassNotContains(strEnum);
		
		// ���݂̕���������A���ꂪ ']' �łȂ���΁A��Ԃɉ����ēǂݍ��݁A��ԑJ�ڂ��A���̕����Ɉړ����܂��B
		while (strEnum.hasCurrent()) {
			char ch = strEnum.getCurrent();
			if (ch == CharClassEnd) {
				break;
			}
			
			_state = _state.read(ch, strEnum);
			strEnum.moveNext();
		}
		
		// �ǂݍ��񂾂��������ꂸ�Ɏc���Ă��镶��������� _charSet �ɒǉ����ACharClassElement ��Ԃ��܂��B
		_state.addRemainingChars();
		return new CharClassElement(notContains, _charSet);
	}
	
	// '�܂܂�Ă��Ȃ�' �� '^' �̎w������߂��܂��B
	private boolean parseCharClassNotContains(StringEnumerator strEnum) {
		// ���݂̕������Ȃ���΁AnotContains �ł͂���܂���B
		if (!strEnum.hasCurrent()) {
			return false;
		}
		
		// ���݂̕����� '^' �łȂ���΁AnotContains �ł͂���܂���B
		char ch = strEnum.getCurrent();
		if (ch != CharClassNotContains) {
			return false;
		}
		
		// ���݂̕����� '^' �Ȃ�΁AnotContains �ŁA���̕����ֈړ����܂��B
		strEnum.moveNext();
		return true;
	}
	
	/**
	 * {@link CharClassParser} �N���X�̃I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 */
	@Override
	public String toString() {
		return String.format(
				"_state=%s, _startCh='%c', _charSet=%s",
				_state.getClass().getSimpleName(), _startCh, _charSet.toString());
	}
	
	// �p�[�T�[�̏�Ԃ�\�킷���ۃN���X�ł��B
	// ��Ԃ́A(1) ������� (InitialState)�A(2) �J�n������ǂݍ��񂾏�� (StartCharReadState)�A
	// (3) �J�n�����ɑ����Ĕ͈͂��w�肷�� '-' ��ǂݍ��񂾏�� (RangeReadState)�A�� 3 �ł��B
	private abstract class ParserState {
		// ��Ԃɉ����ĕ�����ǂݍ��݁A���ɑJ�ڂ����Ԃ�Ԃ��܂��B
		abstract ParserState read(char ch, StringEnumerator strEnum);
		// ���ߏI�����ɏ������ꂸ�Ɏc���Ă��镶��������� _charSet �ɒǉ����܂��B
		abstract void addRemainingChars();
	}
	
	// ������Ԃ�\�킷�N���X�ł��B
	private class InitialState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			// �J�n������ǂݍ��݁AStartCharRead ��ԂɑJ�ڂ��܂��B
			_startCh = RegexParser.parseEscapeChar(ch, strEnum);
			return _startCharReadState;
		}
		
		@Override
		void addRemainingChars() {
			// �ǉ����镶���͂���܂���B
		}
	}
	
	// �J�n������ǂݍ��񂾏�Ԃ�\�킷�N���X�ł��B
	private class StartCharReadState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			if (ch == CharClassRange) {
				// ���݂̕����� '-' �Ȃ�΁ARangeRead�@��ԂɑJ�ڂ��܂��B
				return _rangeReadState;
			}
			else {
				// ����ȊO�Ȃ�΁A�J�n������ 1 �����̎w��Ƃ��� _charSet �ɒǉ����A
				// ���炽�߂ď�����Ԃ���͈͊J�n�̕�����ǂݍ��݂܂��B
				_charSet.add(_startCh);
				return _initialState.read(ch, strEnum);
			}
		}
		
		@Override
		void addRemainingChars() {
			// �J�n�������o�͂��܂��B
			_charSet.add(_startCh);
		}
	}
	
	// �J�n�����Ɣ͈͂��w�肷�� '-' ��ǂݍ��񂾏�Ԃ�\�킷�N���X�ł��B
	private class RangeReadState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			// �I��������ǂݍ��݁A�w��͈͂̕����� _charSet �ɒǉ����AInitial ��Ԃɖ߂�܂��B
			char endCh = RegexParser.parseEscapeChar(ch, strEnum);
			
			char minCh = MathUtils.minCh(_startCh, endCh);
			char maxCh = MathUtils.maxCh(_startCh, endCh);
			for (char c = minCh; c <= maxCh; ++c) {
				_charSet.add(c);
			}

			return _initialState;
		}
		
		@Override
		void addRemainingChars() {
			// �J�n������ '-' ���o�͂��܂��B
			_charSet.add(_startCh);
			_charSet.add(CharClassRange);
		}
	}
}
