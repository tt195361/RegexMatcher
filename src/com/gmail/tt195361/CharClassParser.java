package com.gmail.tt195361;

import java.util.HashSet;

/*
 * ������𕶎��N���X�̎w��Ƃ��ĉ��߂���B
 */
class CharClassParser {

	// �����N���X�̕���������߂���Ƃ��ɓ��ʂȈӖ����������̒�`�B
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
	
	// �R���X�g���N�^
	CharClassParser() {
		_state = _initialState;
		_startCh = '\0';
		_charSet = new HashSet<Character>();
	}
	
	// ������𕶎��N���X�̎w��Ƃ��ĉ��߂��A���߂������e�����Ƃɍ쐬���� CharClassElement �̃I�u�W�F�N�g��Ԃ��B
	// strEnum �̌��݈ʒu�́A�����N���X�̎w��̍ŏ��̕����ł�����̂Ƃ���B 
	//
	// �����N���X:
	//
	//  --> '[' --+---------+---+------------+--> ']' -->|
	//            |         A   A            |
	//            |         |   |            |
	//            +--> '^ --+   +-- �����͈� --+
	//
	//    - '[' �Ŏn�܂�A
	//    - ����ɑ����� '^' �������Ă��悭�A
	//    - �����͈͂� 0�@��ȏ�J�Ԃ��A
	//    - ']' �ŏI���B
	//
	// �����͈�:
	//
	//  --> �J�n���� --+---------------------+-->|
	//               |                     A
	//               |                     |
	//               +--> '-' --> �I������ --+
	//
	//    - 1 ���������̎w��A
	//    - ���邢�́A�J�n�����ɑ����� '-' �ƏI������������͈͎w��B
	//
	CharClassElement parse(StringEnumerator strEnum) {
		// �ŏ��̕������܂����z���A���� '^' ���ǂ������ׂ�B
		strEnum.moveNext();
		boolean notContains = parseCharClassNotContains(strEnum);
		
		// ���݂̕���������A���ꂪ ']' �łȂ���΁A��Ԃɉ����ēǂݍ��݁A��ԑJ�ڂ��A���̕����Ɉړ�����B
		while (strEnum.hasCurrent()) {
			char ch = strEnum.getCurrent();
			if (ch == CharClassEnd) {
				break;
			}
			
			_state = _state.read(ch, strEnum);
			strEnum.moveNext();
		}
		
		// �ǂݍ��񂾂��������ꂸ�Ɏc���Ă��镶��������� _charSet �ɒǉ����ACharClassElement �𐶐����ĕԂ��B
		_state.addRemainingChars();
		return new CharClassElement(notContains, _charSet);
	}
	
	// '�܂܂�Ă��Ȃ�' �� '^' �̎w������߂���B
	private boolean parseCharClassNotContains(StringEnumerator strEnum) {
		// ���݂̕������Ȃ���΁AnotContains �ł͂Ȃ��B
		if (!strEnum.hasCurrent()) {
			return false;
		}
		
		// ���݂̕����������Ă��A���ꂪ '^' �łȂ���΁AnotContains �ł͂Ȃ��B
		char ch = strEnum.getCurrent();
		if (ch != CharClassNotContains) {
			return false;
		}
		
		// ���݂̕����� '^' �Ȃ�΁AnotContains �ŁA���̕����ֈړ�����B
		strEnum.moveNext();
		return true;
	}
	
	@Override
	public String toString() {
		return String.format(
				"_state=%s, _startCh='%c', _charSet=%s",
				_state.getClass().getSimpleName(), _startCh, _charSet.toString());
	}
	
	// �p�[�T�[�̏�Ԃ�\�킷���ۃN���X�B
	// ��Ԃ́A(1) ������� (InitialState)�A(2) �J�n������ǂݍ��񂾏�� (StartCharReadState)�A
	// (3) �J�n�����ɑ����Ĕ͈͂��w�肷�� '-' ��ǂݍ��񂾏�� (RangeReadState)�A�� 3 �B
	private abstract class ParserState {
		// ��Ԃɉ����ĕ�����ǂݍ��݁A���ɑJ�ڂ����Ԃ�Ԃ��B
		abstract ParserState read(char ch, StringEnumerator strEnum);
		// ���ߏI�����ɏ������ꂸ�Ɏc���Ă��镶��������� _charSet �ɒǉ�����B
		abstract void addRemainingChars();
	}
	
	// ������Ԃ�\�킷�N���X�B
	private class InitialState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			// �J�n������ǂݍ��݁AStartCharRead ��ԂɑJ�ڂ���B
			_startCh = RegexParser.parseEscapeChar(ch, strEnum);
			return _startCharReadState;
		}
		
		@Override
		void addRemainingChars() {
			// �ǉ����镶���Ȃ��B
		}
	}
	
	// �J�n������ǂݍ��񂾏�Ԃ�\�킷�N���X�B
	private class StartCharReadState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			if (ch == CharClassRange) {
				// ���݂̕����� '-' �Ȃ�΁ARangeRead�@��ԂɑJ�ڂ���B
				return _rangeReadState;
			}
			else {
				// ����ȊO�Ȃ�΁A�J�n������ 1 �����̎w��Ƃ��� _charSet �ɒǉ����A
				// ���炽�߂ď�����Ԃ���͈͊J�n�̕�����ǂݍ��ށB
				_charSet.add(_startCh);
				return _initialState.read(ch, strEnum);
			}
		}
		
		@Override
		void addRemainingChars() {
			// �J�n�������o�͂���B
			_charSet.add(_startCh);
		}
	}
	
	// �J�n�����Ɣ͈͂��w�肷�� '-' ��ǂݍ��񂾏�Ԃ�\�킷�N���X�B
	private class RangeReadState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			// �I��������ǂݍ��݁A�w��͈͂̕����� _charSet �ɒǉ����AInitial ��Ԃɖ߂�B
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
			// �J�n������ '-' ���o�͂���B
			_charSet.add(_startCh);
			_charSet.add(CharClassRange);
		}
	}
}
