package com.gmail.tt195361.Regex;

/**
 * 文字列の最後に一致する正規表現文型です。
 *
 * <h1 id="文字列の最後の一致">文字列の最後の一致</h1>
 * <p>
 * 文字列の最後は、{@link StringEnumerator#isEnd()} を使って
 * 現在位置が文字列の最後かどうかを調べます。
 * この文型は一致する文字はないので、文字列の列挙子 {@code strEnum} の
 * 現在位置はそのままで移動しません。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            return strEnum.isEnd();
        }
 * </pre>
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
	 * @return 一致に成功した場合は {@code true} を、
	 * 			失敗した場合は {@code false} を返します。
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
