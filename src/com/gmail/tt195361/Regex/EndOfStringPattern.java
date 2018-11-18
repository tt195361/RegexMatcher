package com.gmail.tt195361.Regex;

/**
 * 文字列の最後に一致する正規表現文型です。
 */
class EndOfStringPattern extends RegexPattern {
	
	/**
	 * {@link EndOfStringPattern} クラスのオブジェクトを作成します。
	 */
	EndOfStringPattern() {
		//
	}

	/**
	 * この正規表現文型が文字列の現在位置と一致するかどうか調べます。
	 * この正規表現文型は、文字列の現在位置が最後であれば、一致します。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は移動しません。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		return strEnum.isEnd();
	}
	
	/**
	 * {@link EndOfStringPattern} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "EndOfString";
	}
}
