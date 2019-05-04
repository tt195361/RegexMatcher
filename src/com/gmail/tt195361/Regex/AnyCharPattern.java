package com.gmail.tt195361.Regex;

/**
 * 任意の 1 文字に一致する正規表現文型です。
 *
 * <h1 id="任意の 1 文字の一致">任意の 1 文字の一致</h1>
 * <p>
 * 任意の 1 文字は、文字列の列挙子の現在位置に文字があれば、
 * それがどの文字かにはかかわらず一致します。
 * 一致すれば、文字列の現在位置を次に進めます。
 * プログラムは次のようになります。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            if (!strEnum.hasCurrent()) {
                return false;
            }

            strEnum.moveNext();
            return true;
        }
 * </pre>
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
	 * 任意の 1 文字は、現在位置に文字があれば、どの文字とも一致します。
	 * 
	 * @param patEnum 正規表現文型の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。一致に成功した場合、呼び出し後の現在位置は
	 * 				次の文字に移動します。
	 * @return 一致に成功した場合は {@code true} を、
	 * 			失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
		if (!strEnum.hasChar()) {
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
