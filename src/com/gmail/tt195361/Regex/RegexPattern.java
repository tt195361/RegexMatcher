package com.gmail.tt195361.Regex;

/**
 * 正規表現を構成する一つの文型を表わす抽象クラスです。
 */
abstract class RegexPattern {
	
	/**
	 * {@link RegexPattern} クラスのオブジェクトを作成します。
	 */
	protected RegexPattern() {
		//
	}
	
	/**
	 * この正規表現文型が文字列の現在位置から一致するかどうかを調べます。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し時の現在位置は、この正規表現文型であるものとします。
	 * 				呼び出し後の現在位置は、最後に一致するかどうか調べた正規表現文型になります。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は、
	 * 				この正規表現文型と一致した文字の次の位置に移動します。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	abstract boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum);
}
