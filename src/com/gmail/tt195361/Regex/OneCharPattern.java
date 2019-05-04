package com.gmail.tt195361.Regex;

/**
 * 指定の 1 文字に一致する正規表現文型です。
 *
 * <h1 id="指定の 1 文字の一致">指定の 1 文字の一致</h1>
 * <p>
 * 指定の 1 文字は、文字列の列挙子の現在位置に文字があり、
 * その文字が指定の文字ならば一致します。
 * 一致すれば、文字列の現在位置を次に進めます。
 * プログラムは次のようになります。
 * <pre>
        &#64;Override
        boolean oneMatch(PatternEnumerator patEnum, StringEnumerator strEnum) {
            if (!strEnum.hasCurrent()) {
                return false;
            }

            char actualCh = strEnum.getCurrent();
            if (actualCh != _specifiedCh) {
                return false;
            }

            strEnum.moveNext();
            return true;
        }
 * </pre>
 */
class OneCharPattern extends RegexPattern {
	
	// メンバー変数: 指定の文字を保持します。
	private final char _specifiedCh;
	
	/**
	 * パラメータで指定の文字に一致する {@link OneCharPattern} クラスの
	 * オブジェクトを作成します。
	 * 
	 * @param ch この正規表現文型が一致する文字を指定します。
	 */
	OneCharPattern(char ch) {
		_specifiedCh = ch;
	}
	
	/**
	 * この正規表現文型が一致する文字を取得します。
	 * 
	 * @return この正規表現文型が一致する文字を返します。
	 */
	char getSpecifiedChar() {
		return _specifiedCh;
	}
	
	/**
	 * この正規表現文型が文字列の現在位置と一致するかどうか調べます。
	 * この正規表現文型は、現在位置に文字があり、
	 * その文字が指定された文字ならば、一致します。
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
		
		char actualCh = strEnum.getCurrent();
		if (actualCh != _specifiedCh) {
			return false;
		}

		strEnum.moveNext();
		return true;
	}
	
	/**
	 * {@link OneCharPattern} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "OneChar: " + Character.toString(_specifiedCh);
	}
}
