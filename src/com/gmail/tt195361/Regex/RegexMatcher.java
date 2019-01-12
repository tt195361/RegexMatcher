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
		// 正規表現の文型を表わす文字列を解釈し、一つ一つの文型に分解し、
		// その文型を順に列挙する列挙子を作成します。
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
		StringEnumerator strEnum = new StringEnumerator(str);

		// 指定の文字列に調べられる開始位置が存在する間、一致を調べます。
		while (strEnum.hasCurrentStart()) {

			// 文字列は現在の開始位置から、文型は最初から、順に一致を調べます。
			_patEnum.restoreState(_initialPatEnumState);
			if (matchFromCurrent(_patEnum, strEnum)) {
				// 一致に成功すれば、その結果を返します。
				return MatchResult.makeSuccessResult(strEnum);
			}

			// 一致しなかった場合は、文字列の開始位置を次へ移動します。
			strEnum.moveNextStart();
		}
		
		// 文字列のすべての開始位置で正規表現が一致しなかったので、失敗の結果を返します。
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
