package com.gmail.tt195361;

import java.util.HashSet;

/*
 * 文字列を文字クラスの指定として解釈する。
 */
class CharClassParser {

	// 文字クラスの文字列を解釈するときに特別な意味を持つ文字の定義。
	private static final char CharClassNotContains = '^';
	private static final char CharClassRange = '-';
	private static final char CharClassEnd = ']';
	
	// メンバー変数: パーサーの状態、範囲開始の文字、文字クラスに属する文字を格納する HashSet。
	private ParserState _state;
	private char _startCh;
	private final HashSet<Character> _charSet;
	
	// メンバー変数: パーサーの状態を表わすクラスのインスタンス。
	private final ParserState _initialState = new InitialState();
	private final ParserState _startCharReadState = new StartCharReadState();
	private final ParserState _rangeReadState = new RangeReadState();
	
	// コンストラクタ
	CharClassParser() {
		_state = _initialState;
		_startCh = '\0';
		_charSet = new HashSet<Character>();
	}
	
	// 文字列を文字クラスの指定として解釈し、解釈した内容をもとに作成した CharClassElement のオブジェクトを返す。
	// strEnum の現在位置は、文字クラスの指定の最初の文字であるものとする。 
	//
	// 文字クラス:
	//
	//  --> '[' --+---------+---+------------+--> ']' -->|
	//            |         A   A            |
	//            |         |   |            |
	//            +--> '^ --+   +-- 文字範囲 --+
	//
	//    - '[' で始まり、
	//    - それに続いて '^' があってもよく、
	//    - 文字範囲が 0　回以上繰返し、
	//    - ']' で終わる。
	//
	// 文字範囲:
	//
	//  --> 開始文字 --+---------------------+-->|
	//               |                     A
	//               |                     |
	//               +--> '-' --> 終了文字 --+
	//
	//    - 1 文字だけの指定、
	//    - あるいは、開始文字に続いて '-' と終了文字がある範囲指定。
	//
	CharClassElement parse(StringEnumerator strEnum) {
		// 最初の文字をまたぎ越し、次が '^' かどうか調べる。
		strEnum.moveNext();
		boolean notContains = parseCharClassNotContains(strEnum);
		
		// 現在の文字があり、それが ']' でなければ、状態に応じて読み込み、状態遷移し、次の文字に移動する。
		while (strEnum.hasCurrent()) {
			char ch = strEnum.getCurrent();
			if (ch == CharClassEnd) {
				break;
			}
			
			_state = _state.read(ch, strEnum);
			strEnum.moveNext();
		}
		
		// 読み込んだが処理されずに残っている文字があれば _charSet に追加し、CharClassElement を生成して返す。
		_state.addRemainingChars();
		return new CharClassElement(notContains, _charSet);
	}
	
	// '含まれていない' の '^' の指定を解釈する。
	private boolean parseCharClassNotContains(StringEnumerator strEnum) {
		// 現在の文字がなければ、notContains ではない。
		if (!strEnum.hasCurrent()) {
			return false;
		}
		
		// 現在の文字があっても、それが '^' でなければ、notContains ではない。
		char ch = strEnum.getCurrent();
		if (ch != CharClassNotContains) {
			return false;
		}
		
		// 現在の文字が '^' ならば、notContains で、次の文字へ移動する。
		strEnum.moveNext();
		return true;
	}
	
	@Override
	public String toString() {
		return String.format(
				"_state=%s, _startCh='%c', _charSet=%s",
				_state.getClass().getSimpleName(), _startCh, _charSet.toString());
	}
	
	// パーサーの状態を表わす抽象クラス。
	// 状態は、(1) 初期状態 (InitialState)、(2) 開始文字を読み込んだ状態 (StartCharReadState)、
	// (3) 開始文字に続いて範囲を指定する '-' を読み込んだ状態 (RangeReadState)、の 3 つ。
	private abstract class ParserState {
		// 状態に応じて文字を読み込み、次に遷移する状態を返す。
		abstract ParserState read(char ch, StringEnumerator strEnum);
		// 解釈終了時に処理されずに残っている文字があれば _charSet に追加する。
		abstract void addRemainingChars();
	}
	
	// 初期状態を表わすクラス。
	private class InitialState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			// 開始文字を読み込み、StartCharRead 状態に遷移する。
			_startCh = RegexParser.parseEscapeChar(ch, strEnum);
			return _startCharReadState;
		}
		
		@Override
		void addRemainingChars() {
			// 追加する文字なし。
		}
	}
	
	// 開始文字を読み込んだ状態を表わすクラス。
	private class StartCharReadState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			if (ch == CharClassRange) {
				// 現在の文字が '-' ならば、RangeRead　状態に遷移する。
				return _rangeReadState;
			}
			else {
				// それ以外ならば、開始文字を 1 文字の指定として _charSet に追加し、
				// あらためて初期状態から範囲開始の文字を読み込む。
				_charSet.add(_startCh);
				return _initialState.read(ch, strEnum);
			}
		}
		
		@Override
		void addRemainingChars() {
			// 開始文字を出力する。
			_charSet.add(_startCh);
		}
	}
	
	// 開始文字と範囲を指定する '-' を読み込んだ状態を表わすクラス。
	private class RangeReadState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			// 終了文字を読み込み、指定範囲の文字を _charSet に追加し、Initial 状態に戻る。
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
			// 開始文字と '-' を出力する。
			_charSet.add(_startCh);
			_charSet.add(CharClassRange);
		}
	}
}
