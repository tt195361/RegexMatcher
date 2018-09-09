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
		// '[' をまたぎ越し、次が '^' かどうか調べる。
		strBuffer.moveNext();
		boolean notContained = parseCharClassNot(strBuffer);
		
		// 現在の文字があり、それが ']' でなければ、その文字を状態に応じて読み込み、次の文字に移動する。
		while (strBuffer.hasCurrent()) {
			char ch = strBuffer.getCurrent();
			if (ch == CharClassEnd) {
				break;
			}
			
			readDependingOnState(ch, strBuffer);
			strBuffer.moveNext();
		}
		
		// 読み込んだが処理されずに残っている文字を _charSet に追加し、CharClassElement を生成して返す。
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
		// 範囲開始の文字を読み込み、StartCharRead 状態に遷移する。
		_startCh = RegexParser.parseEscapeChar(ch, strBuffer);
		_state = State.StartCharRead;
	}
	
	private void readRange(char ch, StringBuffer strBuffer) {
		if (ch == CharClassRange) {
			// 現在の文字が '-' ならば、RangeRead　状態に遷移する。
			_state = State.RangeRead;
		}
		else {
			// それ以外ならば、範囲開始の文字を 1 文字の指定として _charSet に追加し、
			// あらためて範囲開始の文字を読み込む。
			_charSet.add(_startCh);
			readStartChar(ch, strBuffer);
		}
	}
	
	private void readEndChar(char ch, StringBuffer strBuffer) {
		// 範囲終了の文字を読み込み、指定範囲の文字を _charSet に追加し、Initial 状態に戻る。
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
			// StartCharRead 状態ならば、_startCh を _charSet に追加。
			_charSet.add(_startCh);
		}
		else if (_state == State.RangeRead) {
			// RangeRead 状態ならば、_startCh と '-' を _charSet に追加。
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
