package com.gmail.tt195361.Regex;

/**
 * 文字列の最初に一致する正規表現文型です。
 *
 * <h1 id="文字列の最初の一致">文字列の最初の一致</h1>
 * <p>
 * 文字列の最初は、{@link StringEnumerator#isStart()} を呼び、
 * 文字列の現在位置が最初かどうかを調べます。
 * この文型は一致する文字はないので、文字列の列挙子 {@code strEnum} の
 * 現在位置はそのままで移動しません。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            return strEnum.isStart();
        }
 * </pre>
 */
class StartOfStringPattern extends RegexPattern {
	
	/**
	 * {@link StartOfStringPattern} クラスのオブジェクトを作成します。
	 */
	StartOfStringPattern() {
		//
	}

	/**
	 * この正規表現文型が文字列の現在位置と一致するかどうか調べます。
	 * この正規表現文型は文字列の現在位置が最初であれば、一致します。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は移動しません。
	 * @return 一致に成功した場合は {@code true} を、
	 * 		失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		return strEnum.isStart();
	}
	
	/**
	 * {@link StartOfStringPattern} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "StartOfString";
	}
}
