package com.gmail.tt195361.Regex;

/**
 * 正規表現文型を表わす文字列を解釈した結果を格納します。
 */
class PatternParseResult {

	// メンバー変数: 正規表現文型と、一つ前の正規表現文型を削除するかどうかを保持します。
	private final RegexPattern _pattern;
	private final boolean _removeLastPat;
	
	/**
	 * パラメータで指定の値を持つ {@link PatternParseResult} クラスのオブジェクトを作成します。
	 * 
	 * @param pattern 解釈した結果をもとに作成した正規表現文型を表わす
	 * 		{@link RegexPattern} クラスのオブジェクトです。
	 * @param removeLastPat 一つ前の正規表現文型を削除するかどうかを指定する
	 * 		{@code boolean} の値です。
	 */
	PatternParseResult(RegexPattern pattern, boolean removeLastPat) {
		_pattern = pattern;
		_removeLastPat = removeLastPat;
	}
	
	/**
	 * 解釈した結果をもとに作成した正規表現文型のオブジェクトを取得します。
	 * 
	 * @return 正規表現文型を表わす {@link RegexPattern} クラスのオブジェクトを返します。
	 */
	RegexPattern getPattern() {
		return _pattern;
	}
	
	/**
	 * 一つ前の正規表現文型を削除するかどうかを指定する値を取得します。
	 * 
	 * @return 一つ前の正規表現文型を削除するかどうかを指定する {@code boolean} の値です。
	 */
	boolean removeLastPat() {
		return _removeLastPat;
	}
	
	/**
	 * {@link PatternParseResult} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return String.format(
				"_pattern=%s, _removeLastPat=%s",
				_pattern.toString(), Boolean.toString(_removeLastPat));
	}
}
