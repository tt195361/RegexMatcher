package com.gmail.tt195361.Regex;

import java.util.*;

/**
 * 文字列を正規表現の文型の指定として解釈します。
 * 
 * <h1 id="取り扱う正規表現">取り扱う正規表現</h1>
 * <p>
 * このプログラムで取り扱う正規表現の文型を、次の表にまとめます。
 * <table
 * 		border="1" rules="all"
 * 		summary="このプログラムで取り扱う正規表現の文型">
 *   <tr align="center" bgcolor="lightgray">
 *     <th>文型</th>
 *     <th>一致する対象</th>
 *     <th>現れる文脈</th>
 *     <th>呼び方</th>
 *     <th>使用例</th>
 *     <th>一致例</th>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code .}</td>
 *     <td>任意の 1 文字</td>
 *     <td>どこでもよい</td>
 *     <td>任意の 1 文字</td>
 *     <td align="center">{@code .}</td>
 *     <td>{@code a, A, 0, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code ^}</td>
 *     <td>文字列の最初</td>
 *     <td>正規表現の最初</td>
 *     <td>文字列の最初</td>
 *     <td align="center">{@code ^a}</td>
 *     <td>{@code a, ab, abc, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code $}</td>
 *     <td>文字列の最後</td>
 *     <td>正規表現の最後</td>
 *     <td>文字列の最後</td>
 *     <td align="center">{@code z$}</td>
 *     <td>{@code z, yz, xyz, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code [ ]}</td>
 *     <td>指定の文字の中の 1 文字</td>
 *     <td rowspan="2">どこでもよい</td>
 *     <td rowspan="2">文字クラス</td>
 *     <td align="center">{@code [a-z]}</td>
 *     <td>{@code a, b, c, ..., z}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code [^ ]}</td>
 *     <td>指定の文字の中の 1 文字以外</td>
 *     <td align="center">{@code [^a-z]}</td>
 *     <td>{@code A, B, ..., 0, 1, ..., !, $, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">{@code *}</td>
 *     <td>この前の文型の 0 回以上の繰り返し</td>
 *     <td>他の文型の後</td>
 *     <td>閉包</td>
 *     <td align="center">{@code a*}</td>
 *     <td>{@code "", a, aa, aaa, ...}</td>
 *   </tr>
 *   <tr>
 *     <td align="center">上記以外の文字</td>
 *     <td>その文字自身</td>
 *     <td>どこでもよい</td>
 *     <td>指定の 1 文字</td>
 *     <td align="center">{@code a}</td>
 *     <td>{@code a}</td>
 *   </tr>
 * </table>
 * 
 * <h1 id="特別な意味の打ち消し">特別な意味の打ち消し</h1>
 * <p>
 * 特別な意味を持つ文字をその文字自身として取り扱うには、その文字の前に {@code \} を置きます。
 * たとえば {@code \.} は、{@code .} の特別な意味を打ち消し、{@code .} 自身
 * として取り扱います。また、{@code \\} は {@code \} を意味します。
 * {@code [ ]} 内、あるいは {@code [^ ]} 内では、{@code \} は取り扱いません。
 * 
 * <h1 id="一致の詳細">一致の詳細</h1>
 * <p>
 * 正規表現と文字列の一致は、以下のように取り扱います。
 * <ul>
 *   <li id="最左一致">最左一致 -- 文字列のなるべく左から一致させます。
 *   	たとえば、正規表現 {@code "aa"} は、文字列
 *   	{@code "aabaac"} と、開始位置 0 と 3 の 2 個所で一致しますが、より左の開始位置 0 から
 *   	一致させます。</li>
 *   <li>最長一致 -- 文字列のなるべく長い部分と一致させます。たとえば、正規表現 {@code "a*"} は
 *   	文字列 {@code "aaa"} に対して、{@code ""}、{@code "a"}、
 *      {@code "aa"}、{@code "aaa"} の 4 通りの一致が考えられます。
 *   	この中で、一致する文字列が一番長い {@code "aaa"} を結果とします。</li>
 * </ul>
 */
class RegexParser {
	
	// 正規表現の文型として特別な意味を持つ文字の定義です。
	private static final char Any = '.';
	private static final char Start = '^';
	private static final char End = '$';
	private static final char Closure = '*';
	private static final char CharClassStart = '[';
	private static final char Escape = '\\';

	// このクラスは static メソッドだけなので、コンストラクタを private にして、
	// インスタンスを作れないようにします。 
	private RegexParser() {
		//
	}
	
	/**
	 * 指定の文字列を正規表現の文型として解釈し、解釈した内容をもとに作成した
	 * {@link PatternEnumerator} クラスのオブジェクトを返します。
 	 *  
	 * @param pattern 正規表現の文型を指定する文字列です。
	 * @return 指定の文字列を解釈した結果として作成した
	 * 		{@link PatternEnumerator} クラスのオブジェクトを返します。
	 */
	static PatternEnumerator parse(String pattern) {
		StringEnumerator strEnum = StringEnumerator.makeForParse(pattern);
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
			
			// 解釈した結果から作成した正規表現文型をリストに追加し、
			// lastPat に一つ前の文型として記録します。
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
			// '*' でその前に文型がある場合は、その文型を 0 回以上繰り返す
			// ClosurePattern です。一つ前の文型は ClosurePattern に取り込むので、
			// リストから削除します。
			pattern = new ClosurePattern(lastPat);
			removeLastPat = true;
		}
		else if (ch == CharClassStart && !strEnum.isLast()) {
			// '[' が文型の最後でなければ、以降を文字クラスとして解釈し、
			// CharClassPattern を作成します。
			CharClassParser charClassParser = new CharClassParser();
			pattern =  charClassParser.parse(strEnum);
		}
		else {
			// それ以外は、その文字自身を意味する OneCharPattern です。
			// エスケープされている場合を考慮します。
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
