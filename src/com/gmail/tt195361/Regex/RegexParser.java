package com.gmail.tt195361.Regex;

import java.util.*;

/**
 * 文字列を正規表現の文型の指定として解釈します。
 */
class RegexParser {
	
	// 正規表現の文型として特別な意味を持つ文字の定義です。
	private static final char Any = '.';
	private static final char Start = '^';
	private static final char End = '$';
	private static final char Closure = '*';
	private static final char CharClassStart = '[';
	private static final char Escape = '\\';

	// このクラスは static メソッドだけなので、コンストラクタを private にして、インスタンスを作れないようにします。 
	private RegexParser() {
		//
	}
	
	/**
	 * 指定の文字列を正規表現の文型として解釈し、解釈した内容をもとに作成した
	 * {@link ElementEnumerator} クラスのオブジェクトを返します。
 	 *  
	 * @param pattern 正規表現の文型を指定する文字列です。
	 * @return 指定の文字列を解釈した結果を表わす {@link ElementEnumerator} クラスのオブジェクトを返します。
	 */
	static ElementEnumerator parse(String pattern) {
		StringEnumerator strEnum = new StringEnumerator(pattern, 0);
		List<RegexElement> elemList = new ArrayList<RegexElement>();
		RegexElement lastElem = null;

		// 文字列に文字がある間、繰り返します。
		while (strEnum.hasCurrent()) {
			// 文字列の現在位置から正規表現要素を解釈し、その結果を取得します。
			ElementParseResult elemParseResult = parseElement(strEnum, lastElem);

			// '*' の場合は、最後の正規表現要素を削除します。
			if (elemParseResult.removeLastElem()) {
				elemList.remove(lastElem);
			}
			
			// 解釈した結果から作成した正規表現要素をリストに追加し、lastElem に最後の要素として記録します。
			RegexElement parsedElement = elemParseResult.getElement();
			elemList.add(parsedElement);
			lastElem = parsedElement;
			
			// 次の文字に移動します。
			strEnum.moveNext();
		}
		
		// 正規表現要素のリストから列挙子を作成して返します。
		return new ElementEnumerator(elemList);
	}
	
	// 文字列の現在位置から正規表現要素を解釈し、解釈結果を返します。
	private static ElementParseResult parseElement(
			StringEnumerator strEnum, RegexElement lastElem) {
		RegexElement element;
		boolean removeLastElem = false;
		
		char ch = strEnum.getCurrent();
		if (ch == Any) {
			// '.' は AnyCharElement です。
			element = new AnyCharElement();
		}
		else if (ch == Start && strEnum.isStart()) {
			// '^' が文型の最初にある場合は StartOfStringElement です。
			element = new StartOfStringElement();
		}
		else if (ch == End && strEnum.isLast()) {
			// '$' が文型の最後にある場合は EndOfStringElement です。
			element = new EndOfStringElement();
		}
		else if (ch == Closure && lastElem != null) {
			// '*' でその前に要素がある場合は、その要素を 0 回以上繰り返す ClosureElement です。
			// 前の要素は ClosureElement に取り込むので、リストから削除します。
			element = new ClosureElement(lastElem);
			removeLastElem = true;
		}
		else if (ch == CharClassStart && !strEnum.isLast()) {
			// '[' が文型の最後でなければ、以降を文字クラスとして解釈し、CharClassElement を作成します。
			CharClassParser charClassParser = new CharClassParser();
			element =  charClassParser.parse(strEnum);
		}
		else {
			// それ以外は、その文字自身を意味する OneCharElement です。エスケープされている場合を考慮します。
			char escCh = parseEscapeChar(ch, strEnum);
			element = new OneCharElement(escCh);
		}
		
		return new ElementParseResult(element, removeLastElem);
	}

	/**
	 * 文字のエスケープを解釈し、解釈した結果の文字を返します。
	 * 
	 * @param ch 解釈する文字です。
	 * @param strEnum 文字列の列挙子です。呼び出し時の現在位置は、解釈する文字の位置であるものとします。
	 * 			呼び出し後の現在位置は最後に解釈した文字に移動します。
	 * @return 解釈した結果の文字を返します。
	 */
	static char parseEscapeChar(char ch, StringEnumerator strEnum) {
		if	(ch != Escape) {
			// Escape でなければ、その文字です。
			return ch;
		}
		
		if (strEnum.isLast()) {
			// Escape が文字列の最後ならば Escape を返します。
			return Escape;
		}
		
		// Escape が文字列の最後でなければ、その次の文字を返します。
		strEnum.moveNext();
		return strEnum.getCurrent();
	}
}
