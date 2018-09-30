package com.gmail.tt195361;

/**
 * 指定のパターンを持つ正規表現が指定の文字列と一致するかどうかを調べます。
 */
public class RegexMatcher {
	
	// メンバ－変数: 正規表現を表わす文字列から作成した正規表現要素の列挙子と、その初期状態です。
	private final ElementEnumerator _elemEnum;
	private final EnumeratorState _initialElemEnumState;

	/**
	 * 指定のパターンを持つ正規表現を表わす {@link RegexMatcher} クラスのオブジェクトを生成します。
	 * 
	 * <p>
	 * このプログラムでは、正規表現パターン中のそれぞれの文字を以下のように解釈します。
	 * <ul>
	 *   <li>{@code .} -- 任意の 1 文字と一致します。</li>
	 *   <li>{@code ^} -- 正規表現パターンの先頭にある場合は、文字列の先頭と一致します。
	 *   		それ以外の場合、その文字自身と一致します。</li>
	 *   <li>{@code $} -- 正規表現パターンの最後にある場合は、文字列の末尾と一致します。
	 *   		それ以外の場合は、その文字自身と一致します。</li>
	 *   <li>{@code [ ]} -- {@code [ ]} 内で指定の文字の中の 1 文字と一致します。文字は以下のように指定します。
	 *     <ul>
	 *       <li>文字 -- 指定の文字を含めます。たとえば、 {@code abc} は、
	 *       		{@code a}, {@code b}, {@code c} のそれぞれの文字を指定します。</li>
	 *       <li>開始文字{@code -}終了文字 -- 開始文字と終了文字を {@code -} で区切ると、
	 *       		その範囲の文字を指定します。たとえば、{@code A-Z} は英大文字
	 *       		({@code A}, {@code B}, ..., {@code Z}) を、{@code 0-9} は数字
	 *       		({@code 0}, {@code 1}, ..., {@code 9}) を表わします。</li>
	 *     </ul>
	 *   </li>
	 *   <li>{@code [^ ]} -- {@code [ ]} 内で指定の文字の中の 1 文字以外と一致します。</li>
	 *   <li>{@code *} -- この前の要素の 0 回以上の繰り返しと一致します。たとえば、{@code a*} は
	 *   			{@code a} の 0 回以上の繰り返し、{@code .*} は任意の文字の 0 回以上の
	 *   			繰返し、{@code [0-9]*} は数字の 0 回以上の繰り返しと一致します。</li>
	 *   <li>{@code \} -- この後の文字をその文字自身として取り扱います。{@code [ ]} 内でも使えます。
	 *   	たとえば {@code \.} は、{@code .} の特別な意味を打ち消し、{@code .} として取り扱います。
	 *   	また、{@code \\} は {@code \} を意味します。</li>
	 *   <li>上記以外の文字は、その文字自身と一致します。</li>
	 * </ul>
	 * 
	 * @param pattern　正規表現のパターンを指定する文字列です。
	 */
	public RegexMatcher(String pattern) {
		// 正規表現のパターンを表わす文字列を解釈し、それを要素に分解し、それぞれの要素を列挙する列挙子を作成します。
		_elemEnum = RegexParser.parse(pattern);
		// 正規表現要素の列挙子を初期状態に戻すため、その状態を保存します。
		_initialElemEnumState = _elemEnum.saveState();
	}
	
	/**
	 * この正規表現が指定の文字列と一致するかどうかを調べます。
	 * 
	 * @param str　正規表現と一致するかどうかを調べる文字列です。
	 * @return 一致を調べた結果を格納する {@link MatchResult} クラスのオブジェクトを返します。
	 */
	public MatchResult match(String str) {
		// 指定の文字列のそれぞれの位置で、正規表現が一致するかどうかを調べます。
		for (int startIndex = 0; startIndex < str.length(); ++startIndex) {
			// 正規表現要素の列挙子を初期状態に戻し、現在の位置から列挙を開始する文字列の列挙子を作成します。
			_elemEnum.restoreState(_initialElemEnumState);
			StringEnumerator strEnum = new StringEnumerator(str, startIndex);
			
			// 正規表現が文字列の現在位置から一致するかどうかを調べます。一致すれば成功の結果を作成し返します。
			if (matchFromCurrent(_elemEnum, strEnum)) {
				String matchString = strEnum.getMatchString();
				return MatchResult.makeSuccessResult(startIndex, matchString);
			}
		}
		
		// 文字列のすべての位置で正規表現が一致しなかったので、失敗の結果を作成し返します。
		return MatchResult.makeFailResult();
	}
	
	/**
	 * 正規表現要素と文字列のそれぞれの現在位置から、正規表現要素が文字列に一致するかどうかを調べます。
	 * 
	 * @param elemEnum 正規表現要素の列挙子です。呼び出し後の現在位置は
	 * 				最後に一致に成功した要素の次に移動します。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は
	 * 				正規表現要素と一致した部分の次の文字に移動します。
	 * @return　一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	static boolean matchFromCurrent(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		// 正規表現要素が存在する間、繰り返します。
		while (elemEnum.hasCurrent()) {
			// 正規表現要素がまだ存在するにもかかわらず、文字列が終了していれば、一致は失敗です。
			if (!strEnum.hasCurrent()) {
				return false;
			}
			
			// 現在の正規表現要素が文字列に一致するかどうか調べます。一致しなければ失敗です。
			RegexElement currentElem = elemEnum.getCurrent();
			if (!currentElem.oneMatch(elemEnum, strEnum)) {
				return false;
			}
			
			// 現在の正規表現要素の一致に成功したので、次の正規表現要素に移動します。
			elemEnum.moveNext();
		}

		// すべての正規表現要素が文字列の現在位置から一致したので、文字列の一致に成功です。
		return true;
	}
}
