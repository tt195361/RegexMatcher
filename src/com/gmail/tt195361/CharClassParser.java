package com.gmail.tt195361;

import java.util.HashSet;

class CharClassParser {

	private static final char CharClassNot = '^';
	private static final char CharClassRange = '-';
	private static final char CharClassEnd = ']';
	
	private enum State {
		Initial,
		StartCharRead,
		RangeRead,
	}
	
	private State _state;
	private char _startCh;
	private final HashSet<Character> _charSet;
	
	CharClassParser() {
		_state = State.Initial;
		_startCh = '\0';
		_charSet = new HashSet<Character>();
	}
	
	CharClassElement parse(StringEnumerator strEnum) {
		// '[' ���܂����z���A���� '^' ���ǂ������ׂ�B
		strEnum.moveNext();
		boolean notContains = parseCharClassNot(strEnum);
		
		// ���݂̕���������A���ꂪ ']' �łȂ���΁A���̕�������Ԃɉ����ēǂݍ��݁A���̕����Ɉړ�����B
		while (strEnum.hasCurrent()) {
			char ch = strEnum.getCurrent();
			if (ch == CharClassEnd) {
				break;
			}
			
			readDependingOnState(ch, strEnum);
			strEnum.moveNext();
		}
		
		// �ǂݍ��񂾂��������ꂸ�Ɏc���Ă��镶���� _charSet �ɒǉ����ACharClassElement �𐶐����ĕԂ��B
		addRemainingCharsToCharSet();
		return new CharClassElement(notContains, _charSet);
	}
	
	private boolean parseCharClassNot(StringEnumerator strEnum) {
		if (!strEnum.hasCurrent()) {
			return false;
		}
		
		char ch = strEnum.getCurrent();
		if (ch != CharClassNot) {
			return false;
		}
		else {
			strEnum.moveNext();
			return true;
		}
	}
	
	private void readDependingOnState(char ch, StringEnumerator strEnum) {
		if (_state == State.Initial) {
			readStartChar(ch, strEnum);
		}
		else if (_state == State.StartCharRead) {
			readRange(ch, strEnum);
		}
		else {
			readEndChar(ch, strEnum);
		}
	}
	
	private void readStartChar(char ch, StringEnumerator strEnum) {
		// �͈͊J�n�̕�����ǂݍ��݁AStartCharRead ��ԂɑJ�ڂ���B
		_startCh = RegexParser.parseEscapeChar(ch, strEnum);
		_state = State.StartCharRead;
	}
	
	private void readRange(char ch, StringEnumerator strEnum) {
		if (ch == CharClassRange) {
			// ���݂̕����� '-' �Ȃ�΁ARangeRead�@��ԂɑJ�ڂ���B
			_state = State.RangeRead;
		}
		else {
			// ����ȊO�Ȃ�΁A�͈͊J�n�̕����� 1 �����̎w��Ƃ��� _charSet �ɒǉ����A
			// ���炽�߂Ĕ͈͊J�n�̕�����ǂݍ��ށB
			_charSet.add(_startCh);
			readStartChar(ch, strEnum);
		}
	}
	
	private void readEndChar(char ch, StringEnumerator strEnum) {
		// �͈͏I���̕�����ǂݍ��݁A�w��͈͂̕����� _charSet �ɒǉ����AInitial ��Ԃɖ߂�B
		char endCh = RegexParser.parseEscapeChar(ch, strEnum);
		
		char minCh = MathUtils.minCh(_startCh, endCh);
		char maxCh = MathUtils.maxCh(_startCh, endCh);
		for (char c = minCh; c <= maxCh; ++c) {
			_charSet.add(c);
		}

		_state = State.Initial;
	}

	private void addRemainingCharsToCharSet() {
		if (_state == State.StartCharRead ) {
			// StartCharRead ��ԂȂ�΁A_startCh �� _charSet �ɒǉ��B
			_charSet.add(_startCh);
		}
		else if (_state == State.RangeRead) {
			// RangeRead ��ԂȂ�΁A_startCh �� '-' �� _charSet �ɒǉ��B
			_charSet.add(_startCh);
			_charSet.add(CharClassRange);
		}
	}
	
	@Override
	public String toString() {
		return String.format(
				"_state=%s, _startCh=%c, _charSet=%s",
				_state, _startCh, _charSet.toString());
	}
}
