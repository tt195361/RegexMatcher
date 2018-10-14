package com.gmail.tt195361;

/**
 * 文字列の最後に一致する正規表現要素です。
 */
class EndOfStringElement extends RegexElement {
	
	/**
	 * {@link EndOfStringElement} クラスのオブジェクトを作成します。
	 */
	EndOfStringElement() {
		//
	}

	/**
	 * この正規表現要素が文字列の現在位置と一致するかどうか調べます。
	 * この正規表現要素は現在位置が文字列の終わりであれば、一致します。
	 * 
	 * @param elemEnum 正規表現要素の列挙子です。呼び出し後の現在位置は移動しません。
	 * @param strEnum 文字列の列挙子です。呼び出し後の現在位置は移動しません。
	 * @return 一致に成功した場合は {@code true} を、失敗した場合は {@code false} を返します。
	 */
	@Override
	boolean oneMatch(ElementEnumerator elemEnum, StringEnumerator strEnum) {
		return strEnum.isEnd();
	}
	
	/**
	 * {@link EndOfStringElement} クラスのオブジェクトの文字列表現を返します。
	 */
	@Override
	public String toString() {
		return "EndOfString";
	}
}
