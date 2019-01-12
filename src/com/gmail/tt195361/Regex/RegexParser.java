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
	 * {@link PatternEnumerator} クラスのオブジェクトを返します。
 	 *  
	 * @param pattern 正規表現の文型を指定する文字列です。
	 * @return 指定の文字列を解釈した結果を表わす {@link PatternEnumerator} クラスのオブジェクトを返します。
	 */
	static PatternEnumerator parse(String pattern) {
		StringEnumerator strEnum = new StringEnumerator(pattern);
		List<RegexPattern> patList = new ArrayList<RegexPattern>();
		RegexPattern lastPat = null;

		// 文字列に文字がある間、繰り返します。
		while (strEnum.hasCurrent()) {
			// 文字列の現在位置から正規表現文型を解釈し、その結果を取得します。
			PatternParseResult patParseResult = parsePattern(strEnum, lastPat);

			// '*' の場合は、一つ前の正規表現文型を削除します。
			if (patParseResult.removeLastPat()) {
				patList.remove(lastPat);
			}
			
			// 解釈した結果から作成した正規表現文型をリストに追加し、lastPat に一つ前の文型として記録します。
			RegexPattern parsedPattern = patParseResult.getPattern();
			patList.add(parsedPattern);
			lastPat = parsedPattern;
			
			// 次の文字に移動します。
			strEnum.moveNext();
		}
		
		// 正規表現文型のリストから列挙子を作成して返します。
		RegexPattern[] patArray = new RegexPattern[patList.size()];
		return new PatternEnumerator(patList.toArray(patArray));
	}
	
	// 文字列の現在位置から正規表現文型を解釈し、解釈結果を返します。
	private static PatternParseResult parsePattern(
			StringEnumerator strEnum, RegexPattern lastPat) {
		RegexPattern pattern;
		boolean removeLastPat = false;
		
		char ch = strEnum.getCurrent();
		if (ch == Any) {
			// '.' は AnyCharPattern です。
			pattern = new AnyCharPattern();
		}
		else if (ch == Start && strEnum.isStart()) {
			// '^' が文型の最初にある場合は StartOfStringPattern です。
			pattern = new StartOfStringPattern();
		}
		else if (ch == End && strEnum.isLast()) {
			// '$' が文型の最後にある場合は EndOfStringPattern です。
			pattern = new EndOfStringPattern();
		}
		else if (ch == Closure && lastPat != null) {
			// '*' でその前に文型がある場合は、その文型を 0 回以上繰り返す ClosurePattern です。
			// 一つ前の文型は ClosurePattern に取り込むので、リストから削除します。
			pattern = new ClosurePattern(lastPat);
			removeLastPat = true;
		}
		else if (ch == CharClassStart && !strEnum.isLast()) {
			// '[' が文型の最後でなければ、以降を文字クラスとして解釈し、CharClassParser を作成します。
			CharClassParser charClassParser = new CharClassParser();
			pattern =  charClassParser.parse(strEnum);
		}
		else {
			// それ以外は、その文字自身を意味する OneCharPattern です。エスケープされている場合を考慮します。
			char escCh = parseEscapeChar(ch, strEnum);
			pattern = new OneCharPattern(escCh);
		}
		
		return new PatternParseResult(pattern, removeLastPat);
	}

	// 文字のエスケープを解釈し、解釈した結果の文字を返します。
	private static char parseEscapeChar(char ch, StringEnumerator strEnum) {
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
