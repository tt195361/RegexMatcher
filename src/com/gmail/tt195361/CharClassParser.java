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
	
	CharClassElement parse(StringBuffer strBuffer) {
		// '[' ���܂����z���A���� '^' ���ǂ������ׂ�B
		strBuffer.moveNext();
		boolean notContained = parseCharClassNot(strBuffer);
		
		// ���݂̕���������A���ꂪ ']' �łȂ���΁A���̕�������Ԃɉ����ēǂݍ��݁A���̕����Ɉړ�����B
		while (strBuffer.hasCurrent()) {
			char ch = strBuffer.getCurrent();
			if (ch == CharClassEnd) {
				break;
			}
			
			readDependingOnState(ch, strBuffer);
			strBuffer.moveNext();
		}
		
		// �ǂݍ��񂾂��������ꂸ�Ɏc���Ă��镶���� _charSet �ɒǉ����ACharClassElement �𐶐����ĕԂ��B
		addRemainingCharsToCharSet();
		return new CharClassElement(notContained, _charSet);
	}
	
	private boolean parseCharClassNot(StringBuffer strBuffer) {
		if (!strBuffer.hasCurrent()) {
			return false;
		}
		
		char ch = strBuffer.getCurrent();
		if (ch != CharClassNot) {
			return false;
		}
		else {
			strBuffer.moveNext();
			return true;
		}
	}
	
	private void readDependingOnState(char ch, StringBuffer strBuffer) {
		if (_state == State.Initial) {
			readStartChar(ch, strBuffer);
		}
		else if (_state == State.StartCharRead) {
			readRange(ch, strBuffer);
		}
		else {
			readEndChar(ch, strBuffer);
		}
	}
	
	private void readStartChar(char ch, StringBuffer strBuffer) {
		// �͈͊J�n�̕�����ǂݍ��݁AStartCharRead ��ԂɑJ�ڂ���B
		_startCh = RegexParser.parseEscapeChar(ch, strBuffer);
		_state = State.StartCharRead;
	}
	
	private void readRange(char ch, StringBuffer strBuffer) {
		if (ch == CharClassRange) {
			// ���݂̕����� '-' �Ȃ�΁ARangeRead�@��ԂɑJ�ڂ���B
			_state = State.RangeRead;
		}
		else {
			// ����ȊO�Ȃ�΁A�͈͊J�n�̕����� 1 �����̎w��Ƃ��� _charSet �ɒǉ����A
			// ���炽�߂Ĕ͈͊J�n�̕�����ǂݍ��ށB
			_charSet.add(_startCh);
			readStartChar(ch, strBuffer);
		}
	}
	
	private void readEndChar(char ch, StringBuffer strBuffer) {
		// �͈͏I���̕�����ǂݍ��݁A�w��͈͂̕����� _charSet �ɒǉ����AInitial ��Ԃɖ߂�B
		char endCh = RegexParser.parseEscapeChar(ch, strBuffer);
		
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
