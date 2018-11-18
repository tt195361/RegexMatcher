package com.gmail.tt195361.Regex;

/**
 * 任意の一文字に一致する正規表現文型です。
 */
class AnyCharPattern extends RegexPattern {

	/**
	 * {@link AnyCharPattern} クラスのオブジェクトを作成します。
	 */
	AnyCharPattern() {
		//
	}
	
	/**
	 * この正規表現文型が文字列の現在位置と一致するかどうか調べます。
	 * 任意の一文字は、現在位置に文字があれば、どの文字とも一致します。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		if (!strEnum.hasCurrent()) {
			return false;
		}

		strEnum.moveNext();
		return true;
	}
	
	/**
	 * {@link AnyCharPattern} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "AnyChar";
	}
}
