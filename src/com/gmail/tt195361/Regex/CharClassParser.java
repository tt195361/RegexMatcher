package com.gmail.tt195361.Regex;

import java.util.HashSet;

/**
 * 文字列を文字クラスの指定として解釈します。
 */
class CharClassParser {

	// 文字クラスの文字列を解釈するときに特別な意味を持つ文字の定義です。
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
	
	/**
	 * {@link CharClassParser} クラスのオブジェクトを作成します。
	 */
	CharClassParser() {
		_state = _initialState;
		_startCh = '\0';
		_charSet = new HashSet<Character>();
	}
	
	/**
	 * 指定の文字列を文字クラスの指定として解釈し、解釈した内容をもとに作成した {@link CharClassElement} の
	 * オブジェクトを返します。
	 *
	 * <p>
	 * 文字クラスの文法を、以下の図に示します。
	 * <pre>{@code
	 *  --> '[' --+---------+---+--------------+--> ']' -->|
	 *            |         A   A              |
	 *            |         |   |              |
	 *            +--> '^ --+   +-- 文字指定 --+
	 * }</pre>
	 * <ul>
	 *   <li>{@code '['} で始まり、</li>
	 *   <li>それに続いて {@code '^'} があってもよく、</li>
	 *   <li>文字指定が 0　回以上繰返し、</li>
	 *   <li>{@code ']'} で終わります。</li>
	 * </ul>
	 * <p>
	 * 文字指定は、以下のように記述します。
	 * <pre>{@code
	 *  --> 開始文字 --+-----------------------+-->|
	 *                 |                       A
	 *                 |                       |
	 *                 +--> '-' --> 終了文字 --+
	 * }</pre>
	 * <ul>
	 *    <li>1 文字だけの指定、</li>
	 *    <li>あるいは、開始文字に続いて {@code '-'} と終了文字を記述する範囲指定。</li>
	 * </ul>
	 * 
	 * @param strEnum 解釈する文字列の列挙子です。呼び出し時の現在位置は、文字クラスの指定の最初の文字
	 * 			であるものとします。 呼び出し後の現在位置は最後に解釈した文字に移動します。
	 * @return 解釈した内容をもとに作成した {@link CharClassElement} のオブジェクトを返します。
	 */
	CharClassElement parse(StringEnumerator strEnum) {
		// 最初の文字をまたぎ越し、次が '^' かどうか調べます。
		strEnum.moveNext();
		boolean notContains = parseCharClassNotContains(strEnum);
		
		// 現在の文字があり、それが ']' でなければ、状態に応じて読み込み、状態遷移し、次の文字に移動します。
		while (strEnum.hasCurrent()) {
			char ch = strEnum.getCurrent();
			if (ch == CharClassEnd) {
				break;
			}
			
			_state = _state.read(ch, strEnum);
			strEnum.moveNext();
		}
		
		// 読み込んだが処理されずに残っている文字があれば _charSet に追加し、CharClassElement を返します。
		_state.addRemainingChars();
		return new CharClassElement(notContains, _charSet);
	}
	
	// '含まれていない' の '^' の指定を解釈します。
	private boolean parseCharClassNotContains(StringEnumerator strEnum) {
		// 現在の文字がなければ、notContains ではありません。
		if (!strEnum.hasCurrent()) {
			return false;
		}
		
		// 現在の文字が '^' でなければ、notContains ではありません。
		char ch = strEnum.getCurrent();
		if (ch != CharClassNotContains) {
			return false;
		}
		
		// 現在の文字が '^' ならば、notContains で、次の文字へ移動します。
		strEnum.moveNext();
		return true;
	}
	
	/**
	 * {@link CharClassParser} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format(
				"_state=%s, _startCh='%c', _charSet=%s",
				_state.getClass().getSimpleName(), _startCh, _charSet.toString());
	}
	
	// パーサーの状態を表わす抽象クラスです。
	// 状態は、(1) 初期状態 (InitialState)、(2) 開始文字を読み込んだ状態 (StartCharReadState)、
	// (3) 開始文字に続いて範囲を指定する '-' を読み込んだ状態 (RangeReadState)、の 3 つです。
	private abstract class ParserState {
		// 状態に応じて文字を読み込み、次に遷移する状態を返します。
		abstract ParserState read(char ch, StringEnumerator strEnum);
		// 解釈終了時に処理されずに残っている文字があれば _charSet に追加します。
		abstract void addRemainingChars();
	}
	
	// 初期状態を表わすクラスです。
	private class InitialState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			// 開始文字を読み込み、StartCharRead 状態に遷移します。
			_startCh = RegexParser.parseEscapeChar(ch, strEnum);
			return _startCharReadState;
		}
		
		@Override
		void addRemainingChars() {
			// 追加する文字はありません。
		}
	}
	
	// 開始文字を読み込んだ状態を表わすクラスです。
	private class StartCharReadState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			if (ch == CharClassRange) {
				// 現在の文字が '-' ならば、RangeRead　状態に遷移します。
				return _rangeReadState;
			}
			else {
				// それ以外ならば、開始文字を 1 文字の指定として _charSet に追加し、
				// あらためて初期状態から範囲開始の文字を読み込みます。
				_charSet.add(_startCh);
				return _initialState.read(ch, strEnum);
			}
		}
		
		@Override
		void addRemainingChars() {
			// 開始文字を出力します。
			_charSet.add(_startCh);
		}
	}
	
	// 開始文字と範囲を指定する '-' を読み込んだ状態を表わすクラスです。
	private class RangeReadState extends ParserState {
		@Override
		ParserState read(char ch, StringEnumerator strEnum) {
			// 終了文字を読み込み、指定範囲の文字を _charSet に追加し、Initial 状態に戻ります。
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
			// 開始文字と '-' を出力します。
			_charSet.add(_startCh);
			_charSet.add(CharClassRange);
		}
	}
}
