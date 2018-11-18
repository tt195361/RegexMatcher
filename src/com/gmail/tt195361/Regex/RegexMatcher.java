package com.gmail.tt195361.Regex;

/**
 * 指定の文型を持つ正規表現が指定の文字列と一致するかどうかを調べます。
 */
public class RegexMatcher {
	
	// メンバ－変数: 正規表現を表わす文字列から作成した正規表現文型の列挙子と、その初期状態です。
	private final PatternEnumerator _patEnum;
	private final EnumeratorState _initialPatEnumState;

	/**
	 * 指定の文型を持つ正規表現を表わす {@link RegexMatcher} クラスのオブジェクトを生成します。
	 * 
	 * @param pattern 正規表現の文型を指定する文字列です。
	 */
	public RegexMatcher(String pattern) {
		// 正規表現の文型を表わす文字列を解釈し、一つ一つの文型に分解し、その文型を順に列挙する
		// 列挙子を作成します。
		_patEnum = RegexParser.parse(pattern);
		// 正規表現文型の列挙子を初期状態に戻すため、その状態を保存します。
		_initialPatEnumState = _patEnum.saveState();
	}
	
	/**
	 * この正規表現が指定の文字列と一致するかどうかを調べます。
	 * 
	 * @param str 正規表現と一致するかどうかを調べる文字列です。
	 * @return 一致を調べた結果を格納する {@link MatchResult} クラスのオブジェクトを返します。
	 */
	public MatchResult match(String str) {
		// 指定の文字列のそれぞれの位置で、正規表現が一致するかどうかを調べます。
		// 文字列の最後に一致することがあるので、StringEnumerator.getEndIndex() まで調べます。
		int endIndex = StringEnumerator.getEndIndex(str);
		for (int startIndex = 0; startIndex <= endIndex; ++startIndex) {
			// 正規表現文型の列挙子の状態を最初に戻し、文字列の startIndex の位置から一致するか調べます。
			_patEnum.restoreState(_initialPatEnumState);
			StringEnumerator strEnum = new StringEnumerator(str, startIndex);
			if (matchFromCurrent(_patEnum, strEnum)) {
				String matchString = strEnum.getSubstring();
				return MatchResult.makeSuccessResult(startIndex, matchString);
			}
		}
		
		// 文字列のすべての位置で正規表現が一致しなかったので、失敗の結果を作成し返します。
		return MatchResult.makeFailResult();
	}
	
	/**
	 * 正規表現文型と文字列のそれぞれの現在位置から、正規表現文型が文字列に一致するかどうかを調べます。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し後の現在位置は
	 * 				最後に一致に成功した文型の次に移動します。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は
	 * 				正規表現文型と一致した部分の次の文字に移動します。
	 * @return　一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	static boolean matchFromCurrent(PatternEnumerator patEnum, StringEnumerator strEnum) {
		// 正規表現文型が存在する間、繰り返します。
		while (patEnum.hasCurrent()) {
			// 正規表現文型がまだ存在するにもかかわらず、文字列の終わりを越えていれば、一致は失敗です。
			if (!strEnum.hasCurrentOrEnd()) {
				return false;
			}
			
			// 現在の正規表現文型が文字列に一致するかどうか調べます。一致しなければ失敗です。
			RegexPattern currentPat = patEnum.getCurrent();
			if (!currentPat.oneMatch(patEnum, strEnum)) {
				return false;
			}
			
			// 現在の正規表現文型の一致に成功したので、次の正規表現文型に移動します。
			patEnum.moveNext();
		}

		// すべての正規表現文型が文字列の現在位置から一致したので、成功です。
		return true;
	}
}
